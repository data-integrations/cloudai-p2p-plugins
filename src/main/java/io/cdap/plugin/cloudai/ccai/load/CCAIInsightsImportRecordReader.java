package io.cdap.plugin.cloudai.ccai.load;

import com.google.common.collect.Lists;
import io.cdap.cdap.api.data.format.StructuredRecord;
import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** CCAI Insights Batch Source record reader. */
public final class CCAIInsightsImportRecordReader extends RecordReader<Object, StructuredRecord> {
  private static final Logger LOG = LoggerFactory.getLogger(CCAIInsightsImportRecordReader.class);

  private Iterator<StructuredRecord> iter;
  StructuredRecord value = null;

  @Override
  public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext)
      throws IOException, InterruptedException {
    iter = Lists.newArrayList(InsightsImportModel.getTestRecord()).iterator();
  }

  @Override
  public boolean nextKeyValue() throws IOException, InterruptedException {
    if (iter.hasNext()) {
      value = iter.next();
      return true;
    }
    return false;
  }

  @Override
  public Object getCurrentKey() throws IOException, InterruptedException {
    return new LongWritable(0);
  }

  @Override
  public StructuredRecord getCurrentValue() throws IOException, InterruptedException {
    return value;
  }

  @Override
  public float getProgress() throws IOException, InterruptedException {
    return 0;
  }

  @Override
  public void close() throws IOException {
  }
}