package io.cdap.plugin.cloudai.p2p;

import com.google.cloud.ReadChannel;
import com.google.cloud.documentai.v1beta2.Document;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** */
final class JsonParser {
  public static final Pattern GS_URL_PATTERN = Pattern.compile("gs://([^/]+)/(.+)");

  private final Storage storageClient = StorageOptions.getDefaultInstance().getService();

  // https://github.com/googleapis/google-cloud-java/issues/2397
  /** Parse an ObjectId from a Cloud Storage gs:// URL. */
  private BlobId fromStorageUrl(String url) {
    Matcher matcher = GS_URL_PATTERN.matcher(url);
    if (matcher.matches()) {
      return BlobId.of(matcher.group(1), matcher.group(2));
    } else {
      throw new IllegalArgumentException(
          "Invalid GCS URL - " + url + " Expected something like gs://bucket/path/to/object");
    }
  }

  public Document getDocument(String gcsUrl) {
    BlobId blobId = fromStorageUrl(gcsUrl);
    Blob blob = storageClient.get(blobId);
    ReadChannel readerChannel = blob.reader();
    readerChannel.setChunkSize(16384);
    try (InputStream inputStream = Channels.newInputStream(readerChannel);
        InputStreamReader reader = new InputStreamReader(inputStream)) {
      Document.Builder document = Document.newBuilder();
      JsonFormat.parser().merge(reader, document);
      return document.build();
    } catch (InvalidProtocolBufferException e) {
      throw new RuntimeException(
          "Exception while converting DocAI response to document object.", e);
    } catch (IOException e) {
      throw new RuntimeException(
          "IOException while converting DocAI response to document object.", e);
    }
  }
}
