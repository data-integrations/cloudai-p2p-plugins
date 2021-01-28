package io.cdap.plugin.cloudai.p2p;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.documentai.v1beta2.BatchProcessDocumentsRequest;
import com.google.cloud.documentai.v1beta2.BatchProcessDocumentsResponse;
import com.google.cloud.documentai.v1beta2.Document;
import com.google.cloud.documentai.v1beta2.Document.Entity;
import com.google.cloud.documentai.v1beta2.DocumentUnderstandingServiceClient;
import com.google.cloud.documentai.v1beta2.DocumentUnderstandingServiceSettings;
import com.google.cloud.documentai.v1beta2.GcsDestination;
import com.google.cloud.documentai.v1beta2.GcsSource;
import com.google.cloud.documentai.v1beta2.InputConfig;
import com.google.cloud.documentai.v1beta2.OperationMetadata;
import com.google.cloud.documentai.v1beta2.OutputConfig;
import com.google.cloud.documentai.v1beta2.ProcessDocumentRequest;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.cdap.api.data.batch.Input;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.dataset.lib.KeyValue;
import io.cdap.cdap.etl.api.Emitter;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.cdap.etl.api.PipelineConfigurer;
import io.cdap.cdap.etl.api.StageConfigurer;
import io.cdap.cdap.etl.api.batch.BatchSource;
import io.cdap.cdap.etl.api.batch.BatchSourceContext;
import io.cdap.plugin.common.SourceInputFormatProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.threeten.bp.Duration;

import java.util.concurrent.TimeUnit;

/** Transform plugin to parse invoices. */
@Plugin(type = BatchSource.PLUGIN_TYPE)
@Name("InvoiceParser")
@Description("Receives unstructured invoice file paths from the user, parses them using DocAI "
  + "Invoice parser APIs, parses JSON output received to generated StructuredRecord objects "
  + "which can be then be written to a storage and made available to the user.")
public class InvoiceParserPlugin extends BatchSource<Object, StructuredRecord, StructuredRecord> {
  private static final Logger LOG = LoggerFactory.getLogger(InvoiceParserPlugin.class);
  private static final String RESPONSE_SUFFIX = "-output-page-1-to-5.json";

  private final InvoiceParserConfig config;

  public InvoiceParserPlugin(InvoiceParserConfig config) {
    this.config = config;
  }

  @Override
  public void configurePipeline(PipelineConfigurer pipelineConfigurer) {
    super.configurePipeline(pipelineConfigurer);

    LOG.debug("validateConfig during configurePipeline stage: {}", config);
    StageConfigurer stageConfigurer = pipelineConfigurer.getStageConfigurer();
    FailureCollector collector = stageConfigurer.getFailureCollector();

    config.validate(collector);
    // Throw exception if there were any errors in the config.
    collector.getOrThrowException();
  }

  @Override
  public void prepareRun(BatchSourceContext batchSourceContext) throws Exception {
    LOG.debug("Prepare debug stage...");
    FailureCollector collector = batchSourceContext.getFailureCollector();
    config.validate(collector);
    collector.getOrThrowException();

    batchSourceContext.setInput(
      Input.of(config.getReferenceName(), new SourceInputFormatProvider(
        InvoiceParserInputFormat.class, config.getConfiguration())));
  }

  @Override
  public void transform(KeyValue<Object, StructuredRecord> input, Emitter<StructuredRecord> emitter) {
    StructuredRecord inputRecord = input.getValue();
    String gcsFilePath = inputRecord.get("gcsPath");

    DocumentUnderstandingServiceSettings.Builder settings = DocumentUnderstandingServiceSettings.newBuilder();
    settings.batchProcessDocumentsSettings()
      .retrySettings()
      .setInitialRpcTimeout(Duration.ofMinutes(10))
      .setMaxAttempts(1)
      .setTotalTimeout(Duration.ofMinutes(10))
      .setMaxRpcTimeout(Duration.ofMinutes(10))
      .build();

    try (DocumentUnderstandingServiceClient client = DocumentUnderstandingServiceClient.create(settings.build())) {
      LOG.info("Sending DocAI Invoice parser request for {}, settings - {}",
        gcsFilePath, settings.build());
      String name = "projects/" + config.getProject() + "/locations/us";
      BatchProcessDocumentsRequest request = BatchProcessDocumentsRequest.newBuilder()
        .setParent(name)
        .addRequests(
          0,
          ProcessDocumentRequest.newBuilder()
            .setDocumentType("invoice")
            .setInputConfig(
              InputConfig.newBuilder()
                .setMimeType("application/pdf")
                .setGcsSource(GcsSource.newBuilder().setUri(gcsFilePath).build())
                .build())
            .setOutputConfig(
              OutputConfig.newBuilder()
                .setGcsDestination(GcsDestination.newBuilder().setUri(config.getOutputGcsBucket()).build())
                .build())
            .build())
        .build();
      LOG.info("Sending DocAI Invoice parser request - {}", request);

      OperationFuture<BatchProcessDocumentsResponse, OperationMetadata> future
        = client.batchProcessDocumentsAsync(request);
      LOG.info("Request sent...");
      BatchProcessDocumentsResponse response = future.get(120, TimeUnit.MINUTES);
      LOG.info("Response received - {}.", response);
      emitter.emit(processResponse(gcsFilePath, response));
    } catch (Exception e) {
      LOG.error("Error while parsing invoice {}", e.getMessage(), e);
    }
  }

  StructuredRecord processResponse(String gcsFilePath, BatchProcessDocumentsResponse response) {
    JsonParser parser = new JsonParser();
    Document doc = parser.getDocument(
        response.getResponses(0).getOutputConfig().getGcsDestination().getUri() + RESPONSE_SUFFIX);
    LOG.info("JSON received = " + doc);
    StructuredRecord record = new InvoiceModel().extractInvoiceRecord(doc);
    LOG.info("Invoice record received = " + record);

    return StructuredRecord.builder(InvoiceParserConfig.getOutputSchema())
      .set("gcsPath", gcsFilePath)
      .set("json", response.getResponses(0).getOutputConfig().toString())
      .build();
  }
}
