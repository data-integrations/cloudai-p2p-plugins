package io.cdap.plugin.cloudai.ccai;

import com.google.common.collect.Lists;
import io.cdap.cdap.api.data.format.StructuredRecord;
import java.io.IOException;
import java.util.List;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/** CCAI Insights Batch Source input format. */
public final class CCAIInsightsInputFormat extends InputFormat<Object, StructuredRecord> {
  @Override
  public List<InputSplit> getSplits(JobContext jobContext) throws IOException, InterruptedException {
    return Lists.newArrayList(new io.cdap.plugin.cloudai.ccai.CCAIInsightsInputSplit());
  }

  @Override
  public RecordReader<Object, StructuredRecord> createRecordReader(InputSplit inputSplit,
      TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
    return new io.cdap.plugin.cloudai.ccai.CCAIInsightsRecordReader();
  }
}
