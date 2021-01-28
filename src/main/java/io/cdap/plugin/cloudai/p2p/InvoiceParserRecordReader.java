package io.cdap.plugin.cloudai.p2p;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.api.data.schema.Schema.Field;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** Invoice Parser record reader. */
public final class InvoiceParserRecordReader extends RecordReader<Object, StructuredRecord> {
  private Iterator<StructuredRecord> iter;
  StructuredRecord value = null;

  @Override
  public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext)
    throws IOException, InterruptedException {
    Configuration conf = taskAttemptContext.getConfiguration();
    String gcsFilePath = conf.get(InvoiceParserConfig.NAME_GCS_FILE_PATH);
    Schema inputSchema = Schema.parseJson(conf.get(InvoiceParserConfig.NAME_INPUT_SCHEMA));
    StructuredRecord record =
      StructuredRecord.builder(inputSchema)
        .set("gcsPath", gcsFilePath)
        .build();
    Map<Object, StructuredRecord> map = Maps.newConcurrentMap();
    map.put(new LongWritable(0), record);
    iter = Lists.newArrayList(record).iterator();
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
