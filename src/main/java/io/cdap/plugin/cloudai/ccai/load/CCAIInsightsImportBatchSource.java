package io.cdap.plugin.cloudai.ccai.load;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.contactcenterinsights.v1alpha1.ContactCenterInsightsGrpc;
import com.google.cloud.contactcenterinsights.v1alpha1.ContactCenterInsightsGrpc.ContactCenterInsightsFutureStub;
import com.google.cloud.contactcenterinsights.v1alpha1.Conversation;
import com.google.cloud.contactcenterinsights.v1alpha1.ConversationDataSource;
import com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest;
import com.google.cloud.contactcenterinsights.v1alpha1.GcsSource;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
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
import io.cdap.plugin.cloudai.ccai.CCAIInsightsBatchSourceConfig;
import io.cdap.plugin.common.SourceInputFormatProvider;
import io.grpc.ManagedChannelBuilder;
import io.grpc.auth.MoreCallCredentials;
import org.apache.hadoop.io.LongWritable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** CCAI Insights Batch Source. */
@Plugin(type = BatchSource.PLUGIN_TYPE)
@Name("CCAIInsightsImport")
@Description("Load conversations from GCS so that they can be processed by CCAI Insights API.")
public class CCAIInsightsImportBatchSource extends BatchSource<LongWritable, StructuredRecord, StructuredRecord> {
  private static final Logger LOG = LoggerFactory.getLogger(CCAIInsightsImportBatchSource.class);

  private final CCAIInsightsBatchImportSourceConfig config;

  public CCAIInsightsImportBatchSource(CCAIInsightsBatchImportSourceConfig config) {
    this.config = config;
  }

  @Override
  public void configurePipeline(PipelineConfigurer pipelineConfigurer) {
    super.configurePipeline(pipelineConfigurer);

    LOG.debug("validateConfig during configurePipeline stage: {}", config);
    StageConfigurer stageConfigurer = pipelineConfigurer.getStageConfigurer();
    FailureCollector collector = stageConfigurer.getFailureCollector();
    stageConfigurer.setOutputSchema(config.getSchema());

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
            CCAIInsightsImportInputFormat.class, config.getConfiguration())));
  }

  @Override
  public void transform(KeyValue<LongWritable, StructuredRecord> input, Emitter<StructuredRecord> emitter) {
    String transcript = config.getGcsTranscriptFilePath();
    try {
      ContactCenterInsightsFutureStub client =
          ContactCenterInsightsGrpc.newFutureStub(
              ManagedChannelBuilder.forTarget("contactcenterinsights.googleapis.com:443").build())
              .withCallCredentials(
                  MoreCallCredentials.from(GoogleCredentials.getApplicationDefault()));
      String projectId = config.getProject();

      CreateConversationRequest request = CreateConversationRequest.newBuilder()
          .setParent("projects/" + projectId + "/locations/us-central1")
          .setConversation(
              Conversation.newBuilder()
                  .setDataSource(
                      ConversationDataSource.newBuilder()
                          .setGcsSource(
                              GcsSource.newBuilder()
                                  .setTranscriptUri(transcript)
                                  .build())
                          .build())
                  .build())
          .build();
      LOG.info("Sending request - {} ", request);
      ListenableFuture<Conversation> result = client.createConversation(request);
      Conversation conversation = Futures.getUnchecked(result);
      LOG.info("Received response - {}", conversation);
      emitter.emit(InsightsImportModel.generateImportRecord(transcript, conversation));
    } catch (Exception e) {
      LOG.error("Error while creating conversation {}", e);
      emitter.emit(InsightsImportModel.generateImportErrorRecord(transcript, e));
    }
  }
}
