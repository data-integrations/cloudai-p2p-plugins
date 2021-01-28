package io.cdap.plugin.cloudai.ccai;

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
final class InsightsModel {
  private static final Logger LOG = LoggerFactory.getLogger(
      io.cdap.plugin.cloudai.ccai.InsightsModel.class);

  public static final String CONVERSATION_NAME = "conversation_name";
  public static final String AUDIO_FILE_URI = "audio_file_uri";
  public static final String AGENT_ID = "agent_id";
  public static final String LOAD_TIMESTAMP_UTC = "load_timestamp_utc";
  public static final String YEAR = "year";
  public static final String MONTH = "month";
  public static final String DAY = "day";
  public static final String DURATION_NANOS = "duration_nanos";
  public static final String SILENCE_NANOS = "silence_nanos";
  public static final String SILENCE_PERCENTAGE = "silence_percentage";
  public static final String AGENT_SPEAKING_PERCENTAGE = "agent_speaking_percentage";
  public static final String CLIENT_SPEAKING_PERCENTAGE = "client_speaking_percentage";
  public static final String AGENT_SENTIMENT_SCORE = "agent_sentiment_score";
  public static final String AGENT_SENTIMENT_MAGNITUDE = "agent_sentiment_magnitude";
  public static final String CLIENT_SENTIMENT_SCORE = "client_sentiment_score";
  public static final String CLIENT_SENTIMENT_MAGNITUDE = "client_sentiment_magnitude";
  public static final String TRANSCRIPT = "transcript";
  public static final String ENTITIES = "entities";
  public static final String WORDS = "words";
  public static final String SENTENCES = "sentences";
  public static final String ANNOTATIONS = "annotations";

  public static final String ENTITY_NAME = "name";
  public static final String ENTITY_TYPE = "type";
  public static final String SENTIMENT_SCORE = "sentiment_score";
  public static final String SENTIMENT_MAGNITUDE = "sentiment_magnitude";
  public static final String SALIENCE = "salience";
  public static final String SPEAKER_TAG = "speaker_tag";

  public static final String WORD = "word";
  public static final String START_OFFSET_NANOS = "start_offset_nanos";
  public static final String END_OFFSET_NANOS = "end_offset_nanos";
  public static final String LANGUAGE_CODE = "language_code";
  public static final String SENTENCE = "sentence";

  public static final String ANNOTATION_RECORD = "record";
  public static final String CREATETIMENANOS = "createTimeNanos";
  public static final String ANNOTATION_TYPE = "type";
  public static final String CLICKED = "clicked";
  public static final String CORRECTNESSLEVEL = "correctnessLevel";
  public static final String DISPLAYED = "displayed";

  public static final Schema ENTITY_SCHEMA = getEntitySchema();
  public static final Schema WORD_SCHEMA = getWordSchema();
  public static final Schema ANNOTATION_SCHEMA = getAnnotationSchema();
  public static final Schema SENTENCE_SCHEMA = getSentenceSchema();
  public static final Schema EXPORT_SCHEMA = getExportSchema();

  public static Schema getExportSchema() {
    List<Field> fields = Lists.newArrayList();
    fields.add(Field.of(CONVERSATION_NAME, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(AUDIO_FILE_URI, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(AGENT_ID, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(LOAD_TIMESTAMP_UTC, Schema.of(Schema.Type.LONG)));
    fields.add(Field.of(YEAR, Schema.of(Schema.Type.INT)));
    fields.add(Field.of(MONTH, Schema.of(Schema.Type.INT)));
    fields.add(Field.of(DAY, Schema.of(Schema.Type.INT)));
    fields.add(Field.of(DURATION_NANOS, Schema.of(Schema.Type.LONG)));
    fields.add(Field.of(SILENCE_NANOS, Schema.of(Schema.Type.LONG)));
    fields.add(Field.of(SILENCE_PERCENTAGE, Schema.of(Schema.Type.FLOAT)));
    fields.add(Field.of(AGENT_SPEAKING_PERCENTAGE, Schema.of(Schema.Type.FLOAT)));
    fields.add(Field.of(CLIENT_SPEAKING_PERCENTAGE, Schema.of(Schema.Type.FLOAT)));
    fields.add(Field.of(AGENT_SENTIMENT_SCORE, Schema.of(Schema.Type.FLOAT)));
    fields.add(Field.of(AGENT_SENTIMENT_MAGNITUDE, Schema.of(Schema.Type.FLOAT)));
    fields.add(Field.of(CLIENT_SENTIMENT_SCORE, Schema.of(Schema.Type.FLOAT)));
    fields.add(Field.of(CLIENT_SENTIMENT_MAGNITUDE, Schema.of(Schema.Type.FLOAT)));
    fields.add(Field.of(TRANSCRIPT, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(ENTITIES, Schema.arrayOf(ENTITY_SCHEMA)));
    fields.add(Field.of(WORDS, Schema.arrayOf(WORD_SCHEMA)));
    fields.add(Field.of(SENTENCES, Schema.arrayOf(SENTENCE_SCHEMA)));

    return Schema.recordOf("output", fields);
  }

  public static Schema getEntitySchema() {
    List<Field> entityFields = Lists.newArrayList();
    entityFields.add(Field.of(ENTITY_NAME, Schema.of(Schema.Type.STRING)));
    entityFields.add(Field.of(ENTITY_TYPE, Schema.of(Schema.Type.STRING)));
    entityFields.add(Field.of(SENTIMENT_SCORE, Schema.of(Schema.Type.FLOAT)));
    entityFields.add(Field.of(SENTIMENT_MAGNITUDE, Schema.of(Schema.Type.FLOAT)));
    entityFields.add(Field.of(SALIENCE, Schema.of(Schema.Type.FLOAT)));
    //entityFields.add(Field.of(SPEAKER_TAG, Schema.nullableOf(Schema.of(Schema.Type.STRING))));
    return Schema.recordOf("entitySchema", entityFields);
  }

  public static Schema getWordSchema() {
    List<Field> wordFields = Lists.newArrayList();
    wordFields.add(Field.of(WORD, Schema.of(Schema.Type.STRING)));
    wordFields.add(Field.of(START_OFFSET_NANOS, Schema.of(Schema.Type.LONG)));
    wordFields.add(Field.of(END_OFFSET_NANOS, Schema.of(Schema.Type.LONG)));
    wordFields.add(Field.of(LANGUAGE_CODE, Schema.of(Schema.Type.STRING)));
    return Schema.recordOf("wordSchema", wordFields);
  }

  public static Schema getAnnotationSchema() {
    List<Field> annotationFields = Lists.newArrayList();
    annotationFields.add(Field.of(ANNOTATION_RECORD, Schema.of(Schema.Type.STRING)));
    annotationFields.add(Field.of(CREATETIMENANOS, Schema.of(Schema.Type.LONG)));
    annotationFields.add(Field.of(ANNOTATION_TYPE, Schema.of(Schema.Type.STRING)));
    annotationFields.add(Field.of(CLICKED, Schema.of(Schema.Type.BOOLEAN)));
    annotationFields.add(Field.of(CORRECTNESSLEVEL, Schema.of(Schema.Type.STRING)));
    annotationFields.add(Field.of(DISPLAYED, Schema.of(Schema.Type.BOOLEAN)));
    return Schema.recordOf("annotationSchema", annotationFields);
  }

  public static Schema getSentenceSchema() {
    List<Field> sentenceFields = Lists.newArrayList();
    sentenceFields.add(Field.of(SENTENCE, Schema.of(Schema.Type.STRING)));
    sentenceFields.add(Field.of(SENTIMENT_SCORE, Schema.of(Schema.Type.FLOAT)));
    sentenceFields.add(Field.of(SENTIMENT_MAGNITUDE, Schema.of(Schema.Type.FLOAT)));
    sentenceFields.add(Field.of(LANGUAGE_CODE, Schema.of(Schema.Type.STRING)));
    //sentenceFields.add(Field.of(SPEAKER_TAG, Schema.nullableOf(Schema.of(Schema.Type.STRING))));
    //sentenceFields.add(Field.of(ANNOTATIONS, Schema.arrayOf(ANNOTATION_SCHEMA)));
    return Schema.recordOf("sentenceSchema", sentenceFields);
  }

  public static StructuredRecord createExportRecord(
      String conversationId, Conversation conversation, Analysis analysis) {
    //LOG.info("Conversation received - {}", conversation);
    //LOG.info("Analysis received - {}", analysis);

    StructuredRecord.Builder record = StructuredRecord.builder(
        io.cdap.plugin.cloudai.ccai.InsightsModel.getExportSchema());
    record.set(CONVERSATION_NAME, conversationId);
    record.set(AUDIO_FILE_URI, conversation.getDataSource().getGcsSource().getTranscriptUri());
    record.set(AGENT_ID, conversation.getAgentId());
    Instant instant = Instant.ofEpochSecond(
        conversation.getCreateTime().getSeconds(),
        conversation.getCreateTime().getNanos());
    LocalDateTime datetime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    record.set(LOAD_TIMESTAMP_UTC, Long.valueOf(datetime.getNano()));
    record.set(YEAR, datetime.getYear());
    record.set(MONTH, datetime.getMonth().getValue());
    record.set(DAY, datetime.getDayOfMonth());
    record.set(DURATION_NANOS, Long.valueOf(analysis.getAnalysisResult().getCallAnalysisMetadata().getTotalDuration().getNanos()));
    record.set(SILENCE_NANOS, 0L);
    record.set(SILENCE_PERCENTAGE, 0.0f);
    record.set(AGENT_SPEAKING_PERCENTAGE, 0.0f);
    record.set(CLIENT_SPEAKING_PERCENTAGE, 0.0f);
    record.set(AGENT_SENTIMENT_SCORE, 0.0f);
    record.set(AGENT_SENTIMENT_MAGNITUDE, 0.0f);
    record.set(CLIENT_SENTIMENT_SCORE, 0.0f);
    record.set(CLIENT_SENTIMENT_MAGNITUDE, 0.0f);
    record.set(TRANSCRIPT, "");

    List<StructuredRecord> entities = new ArrayList<>();
    for (Entity entity : analysis.getAnalysisResult().getCallAnalysisMetadata().getEntitiesMap().values()) {
      StructuredRecord.Builder entityRecord = StructuredRecord.builder(ENTITY_SCHEMA);
      entityRecord.set(ENTITY_NAME, entity.getDisplayName());
      entityRecord.set(ENTITY_TYPE, entity.getType().toString());
      entityRecord.set(SENTIMENT_SCORE, entity.getSentiment().getScore());
      entityRecord.set(SENTIMENT_MAGNITUDE, entity.getSentiment().getMagnitude());
      entityRecord.set(SALIENCE, entity.getSalience());
      //entityRecord.set(SPEAKER_TAG,
      entities.add(entityRecord.build());
    }
    record.set(ENTITIES, entities);

    Map<Integer, List<CallAnnotation>> transcriptAnnotations = new HashMap<>();
    analysis.getAnalysisResult().getCallAnalysisMetadata().getAnnotationsList().forEach(
        annotation -> {
          int channelTag = annotation.getChannelTag();
          List<CallAnnotation> annotations = transcriptAnnotations.get(channelTag);
          if (annotations == null) {
            annotations = new ArrayList<>();
            transcriptAnnotations.put(channelTag, annotations);
          }
          annotations.add(annotation);
        });

    Map<Integer, SentimentData> transcriptSentiments = new HashMap<>();
    analysis.getAnalysisResult().getCallAnalysisMetadata().getSentimentList().forEach(
        sentiment -> transcriptSentiments.put(sentiment.getChannelTag(), sentiment.getSentimentData()));

    List<StructuredRecord> words = new ArrayList<>();
    List<StructuredRecord> sentences = new ArrayList<>();
    for (TranscriptSegment segment : conversation.getTranscript().getTranscriptSegmentsList()) {
      for (WordInfo word : segment.getWordsList()) {
        StructuredRecord.Builder wordRecord = StructuredRecord.builder(WORD_SCHEMA);
        wordRecord.set(WORD, word.getWord());
        wordRecord.set(START_OFFSET_NANOS, Long.valueOf(word.getStartOffset().getNanos()));
        wordRecord.set(END_OFFSET_NANOS, Long.valueOf(word.getEndOffset().getNanos()));
        wordRecord.set(LANGUAGE_CODE, segment.getLanguageCode());
        words.add(wordRecord.build());
      }

      int channelTag = segment.getChannelTag();
      SentimentData sentiment = transcriptSentiments.get(channelTag);
      StructuredRecord.Builder sentenceRecord = StructuredRecord.builder(SENTENCE_SCHEMA);
      sentenceRecord.set(SENTENCE, segment.getText());
      sentenceRecord.set(SENTIMENT_SCORE, sentiment.getScore());
      sentenceRecord.set(SENTIMENT_MAGNITUDE, sentiment.getMagnitude());
      sentenceRecord.set(LANGUAGE_CODE, segment.getLanguageCode());
      sentences.add(sentenceRecord.build());
    }
    record.set(WORDS, words);
    record.set(SENTENCES, sentences);
    return record.build();
  }
}
