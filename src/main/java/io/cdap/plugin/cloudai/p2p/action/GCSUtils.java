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

import com.google.cloud.ServiceOptions;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Utility class for GCS operations.
 */
public final class GCSUtils {
  private static final Logger LOG = LoggerFactory.getLogger(GCSUtils.class);

  private GCSUtils() {
  }

  /**
   * Copies the contents of an InputStream to a GCS bucket.
   *
   * @param in an {@link InputStream} from which to read
   * @param blobName GCS blob to which the InputStream is written
   * @return name of the blob that was written.
   */
  public static String copyToGCS(InputStream in, String blobName, AbstractGCSCopyActionConfig config) throws IOException {
    String project = ServiceOptions.getDefaultProjectId();
    StorageOptions.Builder builder = StorageOptions.newBuilder().setProjectId(project);
    Storage storage = builder.build().getService();
    BlobId blobId = BlobId.of(config.getDestPath(), blobName);
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
    // Write data in chunks using resumable upload.
    try (WriteChannel writer = storage.writer(blobInfo)) {
      int chunkSize = config.getChunkSize();
      byte[] buffer = new byte[chunkSize];
      int limit;
      while ((limit = in.read(buffer)) >= 0) {
        try {
          writer.write(ByteBuffer.wrap(buffer, 0, limit));
        } catch (Exception e) {
          LOG.warn("Failed to write to blob {}", blobName, e);
        }
      }
    }
    return blobName;
  }
}
