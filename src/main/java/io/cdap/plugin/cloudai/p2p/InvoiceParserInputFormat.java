package io.cdap.plugin.cloudai.p2p;

import com.google.common.collect.Lists;
import io.cdap.cdap.api.data.format.StructuredRecord;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;
import java.util.List;

/** Invoice Parser plugin input format. */
public final class InvoiceParserInputFormat extends InputFormat<Object, StructuredRecord> {
  @Override
  public List<InputSplit> getSplits(JobContext jobContext) throws IOException, InterruptedException {
    return Lists.newArrayList(new InvoiceParserInputSplit());
  }

  @Override
  public RecordReader<Object, StructuredRecord> createRecordReader(InputSplit inputSplit,
    TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
    return new InvoiceParserRecordReader();
  }
}
