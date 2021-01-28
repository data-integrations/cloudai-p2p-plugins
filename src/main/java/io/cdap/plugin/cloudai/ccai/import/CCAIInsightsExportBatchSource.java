package io.cdap.plugin.cloudai.ccai;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.cdap.api.data.batch.Input;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.cdap.etl.api.PipelineConfigurer;
import io.cdap.cdap.etl.api.StageConfigurer;
import io.cdap.cdap.etl.api.batch.BatchSource;
import io.cdap.cdap.etl.api.batch.BatchSourceContext;
import io.cdap.plugin.cloudai.p2p.InvoiceParserInputFormat;
import io.cdap.plugin.cloudai.p2p.InvoiceParserPlugin;
import io.cdap.plugin.common.SourceInputFormatProvider;
import org.apache.hadoop.io.LongWritable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** CCAI Insights Batch Source. */
@Plugin(type = BatchSource.PLUGIN_TYPE)
@Name("CCAIInsightsExport")
@Description("Loads CCAI insights conversation analysis in CDF so that it can be exported to BigQuery.")
public class CCAIInsightsBatchSource extends BatchSource<LongWritable, StructuredRecord, StructuredRecord> {
  private static final Logger LOG = LoggerFactory.getLogger(InvoiceParserPlugin.class);

  private final CCAIInsightsBatchSouceConfig config;

  public CCAIInsightsBatchSource(CCAIInsightsBatchSouceConfig config) {
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
            CCAIInsightsInputFormat.class, config.getConfiguration())));
  }
}
