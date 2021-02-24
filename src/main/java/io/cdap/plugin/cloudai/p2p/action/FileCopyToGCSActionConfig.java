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

package io.cdap.plugin.cloudai.p2p.action;

import com.google.common.annotations.VisibleForTesting;
import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.etl.api.FailureCollector;

/**
 * Config class that contains all properties necessary to execute a file copy to GCS.
 */
public class FileCopyToGCSActionConfig extends AbstractGCSCopyActionConfig {
  @Description("The full HDFS path of the file or directory that is to be copied. In the case of a directory, if " +
    "fileRegex is set, then only files in the source directory matching the wildcard regex will be copied. " +
    "Otherwise, all files in the directory will be moved.")
  protected String sourcePath;

  @VisibleForTesting
  FileCopyToGCSActionConfig(String sourcePath, String destPath,
                            String fileRegex, Integer numThreads, Integer chunkSize) {
    super(destPath, fileRegex, numThreads, chunkSize);
    this.sourcePath = sourcePath;
  }

  @Override
  public void validate(FailureCollector failureCollector) {
    super.validate(failureCollector);
  }
}
