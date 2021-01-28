package io.cdap.plugin.cloudai.ccai.load;

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
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.hadoop.conf.Configuration;

/** CCAI Insights Batch Source Config. */
public final class CCAIInsightsBatchImportSourceConfig extends PluginConfig {
  static final String NAME_PROJECT = "project";
  static final String NAME_SERVICE_ACCOUNT_FILE_PATH = "serviceFilePath";
  static final String NAME_GCS_TRANSCRIPT_FILE_PATH = "gcsTranscriptFilePath";
  static final String AUTO_DETECT = "auto-detect";
  public static final String NAME_SCHEMA = "schema";

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

  @Name(NAME_GCS_TRANSCRIPT_FILE_PATH)
  @Description("GCS File path which is used to store the invoice content.")
  @Macro
  @Nullable
  protected String gcsTranscriptFilePath;

  @Name(NAME_SCHEMA)
  @Nullable
  @Macro
  @Description("The schema of the table to read.")
  private String schema;

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

  /**
   * @return the schema of the dataset
   */
  @Nullable
  public Schema getSchema() {
    return InsightsImportModel.getImportSchema();
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
  String getGcsTranscriptFilePath() {
    return containsMacro(NAME_GCS_TRANSCRIPT_FILE_PATH)
        || Strings.isNullOrEmpty(gcsTranscriptFilePath) ? null : gcsTranscriptFilePath;
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
    return conf;
  }
}
