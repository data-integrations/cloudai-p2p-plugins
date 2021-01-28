package io.cdap.plugin.cloudai.p2p;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputSplit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Invoice Parser input split.
 */
public final class InvoiceParserInputSplit extends InputSplit implements Writable {
  private long start = 0L;
  private long end = 0L;

  public InvoiceParserInputSplit() {
  }

  public InvoiceParserInputSplit(long start, long end) {
    this.start = start;
    this.end = end;
  }

  public long getStart() {
    return start;
  }

  public long getEnd() {
    return end;
  }

  @Override
  public void write(DataOutput dataOutput) throws IOException {
    dataOutput.writeLong(this.start);
    dataOutput.writeLong(this.end);
  }

  @Override
  public void readFields(DataInput dataInput) throws IOException {
    this.start = dataInput.readLong();
    this.end = dataInput.readLong();
  }

  @Override
  public long getLength() throws IOException, InterruptedException {
    return this.end - this.start;
  }

  @Override
  public String[] getLocations() throws IOException, InterruptedException {
    return new String[0];
  }
}

