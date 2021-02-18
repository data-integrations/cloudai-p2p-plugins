/*
 * Copyright © 2021 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.plugin.cloudai.p2p;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Macro;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.etl.api.FailureCollector;

import java.io.File;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

/**
 * Base class for action plugin configuration.
 */
public class AbstractActionConfig extends GCPConfig {
  // Constants for property names
  protected static final String FILE_REGEX = "fileRegex";
  protected static final int MIN_NUM_THREADS = 1;
  protected static final String NUM_THREADS = "numThreads";
  protected static final String GCS_SCHEME = "gs://";

  @Description("The GCS bucket into which objects will be copied.")
  @Macro
  protected String destPath;

  @Description("Wildcard regular expression to filter the files in the source directory that will be copied")
  @Nullable
  @Macro
  protected String fileRegex;

  @Name(NUM_THREADS)
  @Description("Specifies the number of parallel tasks to use when executing the copy operation; defaults to 1.")
  @Nullable
  @Macro
  protected Integer numThreads;

  // Validate plugin configuration
  public void validate(FailureCollector collector) {
    if (!containsMacro(FILE_REGEX) && fileRegex != null) {
      try {
        Pattern.compile(fileRegex);
      } catch (Exception e) {
        collector.addFailure(String.format("File regex '%s' is invalid: %s.", fileRegex, e.getMessage()),
                             "Provide valid regex.").withConfigProperty(FILE_REGEX);
      }
    }

    if (!containsMacro(NUM_THREADS) && numThreads < AbstractActionConfig.MIN_NUM_THREADS) {
      collector.addFailure("Invalid number of parallel tasks.",
                           "Number of parallel tasks must be greater than zero.")
        .withConfigProperty(NUM_THREADS);
    }

    String serviceAccount = getServiceAccount();
    if (!(containsMacro(NAME_SERVICE_ACCOUNT_FILE_PATH) || containsMacro(NAME_SERVICE_ACCOUNT_JSON)) &&
      serviceAccount != null) {
      if (isServiceAccountFilePath()) {
        File serviceAccountFile = new File(serviceAccount);
        if (!serviceAccountFile.exists()) {
          collector.addFailure(String.format("Service account file '%s' does not exist.", serviceAccount),
                               "Ensure the service account file is available on the local filesystem.")
            .withConfigProperty(NAME_SERVICE_ACCOUNT_FILE_PATH);
        }
      }
    }
  }

  public String getDestPath() {
    return destPath.startsWith(GCS_SCHEME) ? destPath.substring(GCS_SCHEME.length()) : destPath;
  }

  public Integer getNumThreads() {
    return numThreads;
  }

  AbstractActionConfig(String destPath, String fileRegex, Integer numThreads) {
    this.destPath = destPath;
    this.fileRegex = fileRegex;
    this.numThreads = numThreads;
  }
}
