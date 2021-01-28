// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/cloud/contactcenterinsights/v1alpha1/contact_center_insights.proto

package com.google.cloud.contactcenterinsights.v1alpha1;

public interface ListConversationsRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Required. The parent resource of the conversation.
   * </pre>
   *
   * <code>string parent = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }</code>
   * @return The parent.
   */
  java.lang.String getParent();
  /**
   * <pre>
   * Required. The parent resource of the conversation.
   * </pre>
   *
   * <code>string parent = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }</code>
   * @return The bytes for parent.
   */
  com.google.protobuf.ByteString
      getParentBytes();

  /**
   * <pre>
   * The maximum number of conversations to return in the response. If this
   * value is zero, the service will select a default size. A call may return
   * fewer objects than requested. A non-empty `next_page_token` in the response
   * indicates that more data is available.
   * </pre>
   *
   * <code>int32 page_size = 2;</code>
   * @return The pageSize.
   */
  int getPageSize();

  /**
   * <pre>
   * The value returned by the last `ListConversationsResponse`; indicates
   * that this is a continuation of a prior `ListConversations` call and
   * the system should return the next page of data.
   * </pre>
   *
   * <code>string page_token = 3;</code>
   * @return The pageToken.
   */
  java.lang.String getPageToken();
  /**
   * <pre>
   * The value returned by the last `ListConversationsResponse`; indicates
   * that this is a continuation of a prior `ListConversations` call and
   * the system should return the next page of data.
   * </pre>
   *
   * <code>string page_token = 3;</code>
   * @return The bytes for pageToken.
   */
  com.google.protobuf.ByteString
      getPageTokenBytes();

  /**
   * <pre>
   * A filter to reduce results to a specific subset. Useful for querying
   * conversations with specific properties.
   * </pre>
   *
   * <code>string filter = 4;</code>
   * @return The filter.
   */
  java.lang.String getFilter();
  /**
   * <pre>
   * A filter to reduce results to a specific subset. Useful for querying
   * conversations with specific properties.
   * </pre>
   *
   * <code>string filter = 4;</code>
   * @return The bytes for filter.
   */
  com.google.protobuf.ByteString
      getFilterBytes();
}
