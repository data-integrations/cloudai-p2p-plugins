package io.cdap.plugin.cloudai.ccai;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.contactcenterinsights.v1alpha1.Analysis;
import com.google.cloud.contactcenterinsights.v1alpha1.ContactCenterInsightsGrpc;
import com.google.cloud.contactcenterinsights.v1alpha1.ContactCenterInsightsGrpc.ContactCenterInsightsFutureStub;
import com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest;
import com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse;
import com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest;
import com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannelBuilder;
import io.grpc.auth.MoreCallCredentials;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** CCAI Insights Batch Source record reader. */
public final class CCAIInsightsRecordReader extends RecordReader<Object, StructuredRecord> {
  private static final Logger LOG = LoggerFactory.getLogger(CCAIInsightsRecordReader.class);

  private Iterator<StructuredRecord> iter;
  StructuredRecord value = null;

  @Override
  public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext)
      throws IOException, InterruptedException {
    Configuration conf = taskAttemptContext.getConfiguration();
    String projectId = conf.get(CCAIInsightsBatchSouceConfig.NAME_PROJECT);

    ContactCenterInsightsFutureStub client =
        ContactCenterInsightsGrpc.newFutureStub(
            ManagedChannelBuilder.forTarget("contactcenterinsights.googleapis.com:443").build())
        .withCallCredentials(MoreCallCredentials.from(GoogleCredentials.getApplicationDefault()));
    ListConversationsRequest conversationsRequest = ListConversationsRequest.newBuilder()
        .setParent("projects/" + projectId + "/locations/us-central1")
        .setPageSize(100)
        .build();
    LOG.info("Sending conversation request {} ", conversationsRequest);
    ListenableFuture<ListConversationsResponse> conversationFuture = client.listConversations(
       conversationsRequest);
    /*ListAnalysesRequest analysesRequest = ListAnalysesRequest.newBuilder()
        .setParent("projects/" + projectId + "/locations/us-central1")
        .setPageSize(100)
        .build();
    LOG.info("Sending analysis request " + analysesRequest);
    ListenableFuture<ListAnalysesResponse> analysesFuture = client.listAnalyses(analysesRequest);
    ConcurrentHashMap<String, Analysis> analyses = new ConcurrentHashMap<>();
    Futures.getUnchecked(analysesFuture).getAnalysesList().parallelStream().forEach(
        analysis -> {
          String name = analysis.getName();
          String[] tokens = name.split("/"); //projects/{project}/locations/{location}/conversations/{conversation}/analyses/{analysis}
          LOG.info("Analysis NAME = {}, TOKENS = {}", name, tokens);
          if (tokens.length > 5) {
            String conversationId = tokens[5];
            analyses.put(conversationId, analysis);
          } else {
            LOG.error("Invalid analysis name {}", name);
          }
        }
    );*/

    iter = Futures.getUnchecked(conversationFuture).getConversationsList().parallelStream().map(
        conversation -> {
          String name = conversation.getName();
          String[] tokens = name.split("/");
          LOG.info("Analysis NAME = {}, TOKENS = {}", name, tokens);
          if (tokens.length > 5) {
            String conversationId = tokens[5];
            return InsightsModel.createExportRecord(conversationId, conversation, conversation.getLatestAnalysis());
          } else {
            LOG.error("Invalid analysis name {}", name);
          }
          return null;
        }).filter(record -> record != null).collect(Collectors.toList()).iterator();
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