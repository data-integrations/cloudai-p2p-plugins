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

// If you change this, make sure to update the exported-packages variable in the pom.xml file
package io.cdap.plugin.cloudai.p2p.action;

import com.google.common.annotations.VisibleForTesting;
import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Macro;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.cdap.etl.api.action.Action;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class <code>FileCopyToGCSAction</code> is a plugin that allows users
 * to copy files from an HDFS path to a GCS bucket.
 */
@Plugin(type = Action.PLUGIN_TYPE)
@Name(FileCopyToGCSAction.PLUGIN_NAME)
@Description("This example action plugin should give you a good starting point for building your own plugin.")
public class FileCopyToGCSAction extends AbstractAction<Path> {
  public static final String PLUGIN_NAME = "FileCopyToGCSAction";

  private final FileCopyToGCSActionConfig config;
  private FileSystem fileSystem;

  public FileCopyToGCSAction(FileCopyToGCSActionConfig actionConfig) {
    this.config = actionConfig;
  }

  protected FileCopyToGCSActionConfig getConfig() {
    return config;
  }

  @Override
  protected List<Path> listFilePaths(Path path) throws IOException {
    FileSystem fileSystem = path.getFileSystem(new Configuration());
    RemoteIterator<LocatedFileStatus> iter = fileSystem.listFiles(path, true);
    List<Path> filePaths = new ArrayList<>();
    while (iter.hasNext()) {
      LocatedFileStatus lfs = iter.next();
      if (lfs.isDirectory()) {
        continue;
      }
      if (lfs.getPath().toString().matches(config.fileRegex)) {
        filePaths.add(lfs.getPath());
      }
    }
    return filePaths;
  }

  @Override
  protected void runInternal() throws IOException {
    source = new Path("file", "", config.sourcePath);
    fileSystem = source.getFileSystem(new Configuration());
  }

  @Override
  protected String getRelativePath(Path source, Path path) {
    return source.toUri().relativize(path.toUri()).toString();
  }

  @Override
  protected InputStream getInputStream(Path path) throws IOException {
    return fileSystem.open(path);
  }

  /**
   * Config class that contains all properties necessary to execute a file copy to GCS.
   */
  public static class FileCopyToGCSActionConfig extends AbstractActionConfig {
    @Description("The full HDFS path of the file or directory that is to be copied. In the case of a directory, if " +
      "fileRegex is set, then only files in the source directory matching the wildcard regex will be copied. " +
      "Otherwise, all files in the directory will be moved.")
    @Macro
    protected String sourcePath;

    @VisibleForTesting
    FileCopyToGCSActionConfig(String sourcePath, String destPath, String fileRegex, Integer numThreads) {
      super(destPath, fileRegex, numThreads);
      this.sourcePath = sourcePath;
    }

    @Override
    public void validate(FailureCollector failureCollector) {
      super.validate(failureCollector);
    }
  }
}

