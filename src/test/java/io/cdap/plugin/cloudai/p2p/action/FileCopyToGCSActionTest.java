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

import io.cdap.cdap.etl.mock.action.MockActionContext;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class FileCopyToGCSActionTest {
  @Test
  public void testCopy() throws Exception {
    String sourcePath = FileCopyToGCSActionTest.class.getResource("/gcscopytest").getPath();
    FileCopyToGCSActionConfig config = new FileCopyToGCSActionConfig(
      sourcePath, "gs://bucket",
      null, null, null);
    FileCopyToGCSAction action = new FileCopyToGCSAction(config);
    FileCopyToGCSAction a = Mockito.spy(action);
    MockActionContext context = new MockActionContext();
    Mockito.doReturn("").when(a).copyToGCS(any(Path.class));
    a.run(context);
    Mockito.verify(a, Mockito.times(3)).copyToGCS(any(Path.class));
  }

  @Test
  public void testCopyWithFileRegex() throws Exception {
    String sourcePath = FileCopyToGCSActionTest.class.getResource("/gcscopytest").getPath();
    FileCopyToGCSActionConfig config = new FileCopyToGCSActionConfig(
      sourcePath, "gs://bucket",
      ".*txt", 5, 1024);
    FileCopyToGCSAction action = new FileCopyToGCSAction(config);
    FileCopyToGCSAction a = Mockito.spy(action);
    MockActionContext context = new MockActionContext();
    Mockito.doReturn("").when(a).copyToGCS(any(Path.class));
    a.run(context);
    Mockito.verify(a, Mockito.times(2)).copyToGCS(any(Path.class));
  }
}
