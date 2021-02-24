/*
 * Copyright © 2017 Cask Data, Inc.
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

import io.cdap.cdap.etl.api.validation.CauseAttributes;
import io.cdap.cdap.etl.api.validation.ValidationFailure;
import io.cdap.cdap.etl.mock.validation.MockFailureCollector;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link FileCopyToGCSActionConfigTest}.
 */
public class FileCopyToGCSActionConfigTest {
  @Test
  public void testValidateRegex() throws Exception {
    FileCopyToGCSActionConfig config = new FileCopyToGCSActionConfig(
      "src", "dst",
      "*", null, null);
    MockFailureCollector collector = new MockFailureCollector();
    config.validate(collector);
    Assert.assertEquals(1, collector.getValidationFailures().size());
    ValidationFailure failure = collector.getValidationFailures().get(0);
    Assert.assertEquals(AbstractGCSCopyActionConfig.FILE_REGEX,
                        failure.getCauses().get(0).getAttribute(CauseAttributes.STAGE_CONFIG));
  }

  @Test
  public void testNumThreads() throws Exception {
    FileCopyToGCSActionConfig config = new FileCopyToGCSActionConfig(
      "src", "dst",
      ".*", -1, 1024);
    MockFailureCollector collector = new MockFailureCollector();
    config.validate(collector);
    Assert.assertEquals(1, collector.getValidationFailures().size());
    ValidationFailure failure = collector.getValidationFailures().get(0);
    Assert.assertEquals(AbstractGCSCopyActionConfig.NUM_THREADS,
                        failure.getCauses().get(0).getAttribute(CauseAttributes.STAGE_CONFIG));
  }

  @Test
  public void testChunkSize() throws Exception {
    FileCopyToGCSActionConfig config = new FileCopyToGCSActionConfig(
      "src", "dst",
      ".*", 1, 0);
    MockFailureCollector collector = new MockFailureCollector();
    config.validate(collector);
    Assert.assertEquals(1, collector.getValidationFailures().size());
    ValidationFailure failure = collector.getValidationFailures().get(0);
    Assert.assertEquals(AbstractGCSCopyActionConfig.CHUNK_SIZE,
                        failure.getCauses().get(0).getAttribute(CauseAttributes.STAGE_CONFIG));
  }
}
