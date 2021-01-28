package io.cdap.plugin.cloudai.p2p;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.ServiceOptions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Macro;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.api.data.schema.Schema.Field;
import io.cdap.cdap.api.plugin.PluginConfig;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.plugin.common.Constants;
import io.cdap.plugin.common.IdUtils;

import org.apache.hadoop.conf.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Nullable;

/** Invoice Parser plugin config. */
public final class InvoiceParserConfig extends PluginConfig {
  static final String NAME_PROJECT = "project";
  static final String NAME_SERVICE_ACCOUNT_FILE_PATH = "serviceFilePath";
  static final String NAME_GCS_FILE_PATH = "gcsFilePath";
  static final String NAME_OUTPUT_GCS_BUCKET = "gcsOutputBucket";
  static final String AUTO_DETECT = "auto-detect";
  static final String NAME_INPUT_SCHEMA = "inputSchema";

  @Name(Constants.Reference.REFERENCE_NAME)
  @Description("Uniquely identifies this plugin for lineage, annotation metadata etc.")
  String referenceName;

  @Name(NAME_PROJECT)
  @Description("Google Cloud Project ID, which uniquely identifies a project, "
    + "It can be found on the Dashboard in the Google Cloud Platform console.")
  @Macro
  @Nullable
  protected String project;

  @Name(NAME_SERVICE_ACCOUNT_FILE_PATH)
  @Description("Local file system path for the service account used for authorization. "
    + "Can be set to 'auto-detect' when running on a Dataproc cluster. "
    + "When running on other clusters, the file must be present on every node in the cluster.")
  @Macro
  @Nullable
  String serviceAccountFilePath;

  @Name(NAME_GCS_FILE_PATH)
  @Description("GCS File path which is used to store the invoice content.")
  @Macro
  @Nullable
  protected String gcsFilePath;

  @Name(NAME_OUTPUT_GCS_BUCKET)
  @Description("GCS bucket used to store the parsed JSON output.")
  @Macro
  @Nullable
  protected String outputGcsBucket;

  String getReferenceName() {
    return referenceName;
  }

  String getProject() {
    String projectId = tryGetProject();
    if (projectId == null) {
      throw new IllegalArgumentException(
        "Could not detect Google Cloud project id from the environment. Please specify a project id.");
    }
    return projectId;
  }

  @Nullable
  private String tryGetProject() {
    if (containsMacro(NAME_PROJECT) && Strings.isNullOrEmpty(project)) {
      return null;
    }
    String projectId = project;
    if (Strings.isNullOrEmpty(project) || AUTO_DETECT.equals(project)) {
      projectId = ServiceOptions.getDefaultProjectId();
    }
    return projectId;
  }

  @Nullable
  String getServiceAccountFilePath() {
    return (containsMacro(NAME_SERVICE_ACCOUNT_FILE_PATH)
      || Strings.isNullOrEmpty(serviceAccountFilePath)
      || AUTO_DETECT.equals(serviceAccountFilePath)) ? null : serviceAccountFilePath;
  }

  @Nullable
  String getGcsFilePath() {
    return containsMacro(NAME_GCS_FILE_PATH)
      || Strings.isNullOrEmpty(gcsFilePath) ? null : gcsFilePath;
  }

  @Nullable
  String getOutputGcsBucket() {
    return containsMacro(NAME_OUTPUT_GCS_BUCKET)
      || Strings.isNullOrEmpty(outputGcsBucket) ? null : outputGcsBucket;
  }

  /**
   * Validates the given reference name to consist of characters allowed to represent a dataset.
   * @param collector
   */
  void validate(FailureCollector collector) {
    IdUtils.validateReferenceName(referenceName, collector);
  }

  boolean autoServiceAccountUnvailable() {
    if (!AUTO_DETECT.equals(serviceAccountFilePath)) {
      return false;
    }

    try {
      ServiceAccountCredentials.getApplicationDefault();
    } catch (IOException e) {
      return true;
    }
    return false;
  }

  /**
   * Obtains Google Cloud credential from given JSON file.
   * If give path is null or empty, obtains application default credentials.
   *
   * @param serviceAccount path to credentials defined in JSON file
   * @param isServiceAccountFilePath indicator whether service account is file path or JSON
   * @return Google Cloud credential
   * @throws IOException if the credential cannot be created in the current environment
   */
  private static GoogleCredentials getCredential(@Nullable String serviceAccount,
    @Nullable Boolean isServiceAccountFilePath) throws IOException {
    GoogleCredentials credential;
    if (!Strings.isNullOrEmpty(serviceAccount)) {
      if (!isServiceAccountFilePath) {
        credential = loadCredentialFromStream(serviceAccount);
      } else {
        credential = loadCredentialFromFile(serviceAccount);
      }
    } else {
      credential = ServiceAccountCredentials.getApplicationDefault();
    }
    return credential;
  }

  /**
   * Generate credentials from JSON key
   * @param serviceAccount contents of service account JSON
   * @return Google Cloud credential
   * @throws IOException if the credential cannot be created in the current environment
   */
  private static ServiceAccountCredentials loadCredentialFromStream(@Nullable String serviceAccount)
    throws IOException {
      try (InputStream jsonInputStream = new ByteArrayInputStream(serviceAccount.getBytes())) {
        return ServiceAccountCredentials.fromStream(jsonInputStream);
      }
  }

  /**
   * Generate credentials from JSON key
   * @param serviceAccountFilePath path to service account file
   * @return Google Cloud credential
   * @throws IOException if the credential cannot be created in the current environment
   */
  private static ServiceAccountCredentials loadCredentialFromFile(
    @Nullable String serviceAccountFilePath) throws IOException {
      try (InputStream inputStream = new FileInputStream(serviceAccountFilePath)) {
        return ServiceAccountCredentials.fromStream(inputStream);
      }
  }

  Configuration getConfiguration() {
    Configuration conf = new Configuration();
    conf.set(NAME_PROJECT, getProject());
    conf.set(NAME_GCS_FILE_PATH, getGcsFilePath());
    conf.set(NAME_OUTPUT_GCS_BUCKET, getOutputGcsBucket());
    //conf.set(NAME_SERVICE_ACCOUNT_FILE_PATH, getServiceAccountFilePath());
    conf.set(NAME_INPUT_SCHEMA, getInputSchema().toString());
    return conf;
  }

  private Schema getInputSchema() {
    List<Field> fields = Lists.newArrayList();
    fields.add(Field.of("gcsPath", Schema.of(Schema.Type.STRING)));
    return Schema.recordOf("inputSchema", fields);
  }

  static Schema getOutputSchema() {
    List<Field> fields = Lists.newArrayList();
    fields.add(Field.of("gcsPath", Schema.of(Schema.Type.STRING)));
    fields.add(Field.of("json", Schema.of(Schema.Type.STRING)));
    return Schema.recordOf("outputSchema", fields);
  }
}
