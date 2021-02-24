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

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.cdap.etl.api.PipelineConfigurer;
import io.cdap.cdap.etl.api.StageConfigurer;
import io.cdap.cdap.etl.api.action.Action;
import io.cdap.cdap.etl.api.action.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Base class for action plugins that write to GCS.
 */
public abstract class AbstractGCSCopyAction<T> extends Action {
  private static final Logger LOG = LoggerFactory.getLogger(AbstractGCSCopyAction.class);

  protected T source;

  protected AbstractGCSCopyAction() {
  }

  /**
   * This function is called in {@link AbstractGCSCopyAction#run} before doing the copy operation.
   * Any initiation steps can be done here.
   */
  protected abstract void init() throws IOException;

  /**
   * Returns a list of  paths that need to be copied to the destination, given a source location.
   *
   * @param source the source location from which files/objects need to be copied.
   * @return a list of  paths that need to be copied.
   */
  protected abstract List<T> listFilePaths(T source) throws IOException;

  /**
   * Returns the relative path of a file/object to a source location.
   * For example if this function is called with source=/root/dir, path=/root/dir/file.txt, it will return file.txt.
   *
   * @param source the source location from which files need to be copied.
   * @param path the path of a file/object that needs to be copied.
   * @return the path of {@code path} relative to {@code source}.
   */
  protected abstract String getRelativePath(T source, T path);

  /**
   * Returns an InputStream for the given path.
   *
   * @param path path of a file/object.
   * @return {@link InputStream} for {@code path}.
   */
  protected abstract InputStream getInputStream(T path) throws IOException;

  /**
   * Returns plugin configuration.
   *
   * @return plugin configuration.
   */
  protected abstract AbstractGCSCopyActionConfig getConfig();

  @Override
  public void configurePipeline(PipelineConfigurer pipelineConfigurer) throws IllegalArgumentException {
    StageConfigurer stageConfigurer = pipelineConfigurer.getStageConfigurer();
    FailureCollector collector = stageConfigurer.getFailureCollector();
    getConfig().validate(collector);
  }

  @Override
  public void run(ActionContext context) throws Exception {
    init();
    List<T> filePaths = listFilePaths(source);
    copyFiles(filePaths);
  }

  protected void copyFiles(List<T> filePaths) throws InterruptedException {
    if (filePaths.size() == 0) {
      LOG.warn("No files found in source {}. Please make sure there are files available in the source and file " +
               "filters are configured correctly.", source.toString());
      return;
    }

    AbstractGCSCopyActionConfig config = getConfig();
    // Copies the files in a multithreaded way
    CountDownLatch executorTerminateLatch = new CountDownLatch(1);
    ExecutorService executorService = createExecutor(config.getNumThreads(), executorTerminateLatch);
    CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);

    try {
      int count = 0;
      for (final T path : filePaths) {
        count++;
        completionService.submit(new Callable<String>() {
          @Override
          public String call() throws Exception {
            return copyToGCS(path);
          }
        });
      }
      int i = 0;
      while (i < count) {
        try {
          Future<String> fileWritten = completionService.take();
          String fileName = fileWritten.get();
          if (fileName != null) {
            LOG.trace("{} is copied", fileName);
          }
        } catch (Throwable t) {
          throw Throwables.propagate(t);
        }
        i++;
      }

    } finally {
      executorService.shutdownNow();
      executorTerminateLatch.await();
    }
  }

  String copyToGCS(T path) throws IOException {
    String relativePath = getRelativePath(source, path);
    InputStream is = getInputStream(path);
    return GCSUtils.copyToGCS(is, relativePath, getConfig());
  }

  /**
   * Creates an {@link ExecutorService} that has the given number of threads.
   *
   * @param threads          number of core threads in the executor
   * @param terminationLatch a {@link CountDownLatch} that will be counted down when the executor terminated
   * @return a new {@link ExecutorService}.
   */
  private ExecutorService createExecutor(Integer threads, final CountDownLatch terminationLatch) {
    return new ThreadPoolExecutor(
      threads, threads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
      new ThreadFactoryBuilder().build()) {
      @Override
      protected void terminated() {
        terminationLatch.countDown();
      }
    };
  }
}
