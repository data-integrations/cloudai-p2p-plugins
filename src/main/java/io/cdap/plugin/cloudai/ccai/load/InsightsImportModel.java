package io.cdap.plugin.cloudai.ccai.load;

import com.google.cloud.contactcenterinsights.v1alpha1.Analysis;
import com.google.cloud.contactcenterinsights.v1alpha1.CallAnnotation;
import com.google.cloud.contactcenterinsights.v1alpha1.Conversation;
import com.google.cloud.contactcenterinsights.v1alpha1.Conversation.Transcript.TranscriptSegment;
import com.google.cloud.contactcenterinsights.v1alpha1.Conversation.Transcript.TranscriptSegment.WordInfo;
import com.google.cloud.contactcenterinsights.v1alpha1.Entity;
import com.google.cloud.contactcenterinsights.v1alpha1.SentimentData;
import com.google.common.collect.Lists;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.api.data.schema.Schema.Field;
import io.cdap.plugin.cloudai.p2p.InvoiceParserPlugin;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Stores Insights schema and models. */
final class InsightsImportModel {
  private static final Logger LOG = LoggerFactory.getLogger(InsightsImportModel.class);

  public static final String CONVERSATION_NAME = "conversation_name";
  public static final String GCS_SOURCE = "gcs_source";
  public static final String IMPORT_ERROR = "import_error";

  public static Schema getTestSchema() {
    List<Field> fields = Lists.newArrayList();
    fields.add(Field.of(CONVERSATION_NAME, Schema.of(Schema.Type.STRING)));
    return Schema.recordOf("test", fields);
  }

  public static Schema getImportSchema() {
    List<Field> fields = Lists.newArrayList();
    fields.add(Field.of(GCS_SOURCE, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(CONVERSATION_NAME, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(IMPORT_ERROR, Schema.of(Schema.Type.STRING)));
    return Schema.recordOf("output", fields);
  }

  public static StructuredRecord getTestRecord() {
    StructuredRecord.Builder record = StructuredRecord.builder(getTestSchema());
    record.set(CONVERSATION_NAME, "testing");
    return record.build();
  }

  public static StructuredRecord generateImportRecord(
      String transcriptUrl, Conversation conversation) {
    StructuredRecord.Builder record = StructuredRecord.builder(getImportSchema());
    record.set(CONVERSATION_NAME, conversation.getName());
    record.set(GCS_SOURCE, transcriptUrl);
    record.set(IMPORT_ERROR, "");
    return record.build();
  }

  public static StructuredRecord generateImportErrorRecord(
      String transcriptUrl, Exception exception) {
    StructuredRecord.Builder record = StructuredRecord.builder(getImportSchema());
    record.set(CONVERSATION_NAME, "");
    record.set(GCS_SOURCE, transcriptUrl);
    record.set(IMPORT_ERROR, exception.getMessage());
    return record.build();
  }
}
