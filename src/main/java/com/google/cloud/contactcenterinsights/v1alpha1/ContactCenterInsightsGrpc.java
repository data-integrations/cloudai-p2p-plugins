package com.google.cloud.contactcenterinsights.v1alpha1;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * An API that lets users analyze and explore their business conversation data.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: google/cloud/contactcenterinsights/v1alpha1/contact_center_insights.proto")
public final class ContactCenterInsightsGrpc {

  private ContactCenterInsightsGrpc() {}

  public static final String SERVICE_NAME = "google.cloud.contactcenterinsights.v1alpha1.ContactCenterInsights";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest,
      com.google.cloud.contactcenterinsights.v1alpha1.Conversation> getCreateConversationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateConversation",
      requestType = com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest.class,
      responseType = com.google.cloud.contactcenterinsights.v1alpha1.Conversation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest,
      com.google.cloud.contactcenterinsights.v1alpha1.Conversation> getCreateConversationMethod() {
    io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest, com.google.cloud.contactcenterinsights.v1alpha1.Conversation> getCreateConversationMethod;
    if ((getCreateConversationMethod = ContactCenterInsightsGrpc.getCreateConversationMethod) == null) {
      synchronized (ContactCenterInsightsGrpc.class) {
        if ((getCreateConversationMethod = ContactCenterInsightsGrpc.getCreateConversationMethod) == null) {
          ContactCenterInsightsGrpc.getCreateConversationMethod = getCreateConversationMethod =
              io.grpc.MethodDescriptor.<com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest, com.google.cloud.contactcenterinsights.v1alpha1.Conversation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateConversation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.Conversation.getDefaultInstance()))
              .setSchemaDescriptor(new ContactCenterInsightsMethodDescriptorSupplier("CreateConversation"))
              .build();
        }
      }
    }
    return getCreateConversationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.UpdateConversationRequest,
      com.google.cloud.contactcenterinsights.v1alpha1.Conversation> getUpdateConversationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateConversation",
      requestType = com.google.cloud.contactcenterinsights.v1alpha1.UpdateConversationRequest.class,
      responseType = com.google.cloud.contactcenterinsights.v1alpha1.Conversation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.UpdateConversationRequest,
      com.google.cloud.contactcenterinsights.v1alpha1.Conversation> getUpdateConversationMethod() {
    io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.UpdateConversationRequest, com.google.cloud.contactcenterinsights.v1alpha1.Conversation> getUpdateConversationMethod;
    if ((getUpdateConversationMethod = ContactCenterInsightsGrpc.getUpdateConversationMethod) == null) {
      synchronized (ContactCenterInsightsGrpc.class) {
        if ((getUpdateConversationMethod = ContactCenterInsightsGrpc.getUpdateConversationMethod) == null) {
          ContactCenterInsightsGrpc.getUpdateConversationMethod = getUpdateConversationMethod =
              io.grpc.MethodDescriptor.<com.google.cloud.contactcenterinsights.v1alpha1.UpdateConversationRequest, com.google.cloud.contactcenterinsights.v1alpha1.Conversation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateConversation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.UpdateConversationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.Conversation.getDefaultInstance()))
              .setSchemaDescriptor(new ContactCenterInsightsMethodDescriptorSupplier("UpdateConversation"))
              .build();
        }
      }
    }
    return getUpdateConversationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.GetConversationRequest,
      com.google.cloud.contactcenterinsights.v1alpha1.Conversation> getGetConversationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConversation",
      requestType = com.google.cloud.contactcenterinsights.v1alpha1.GetConversationRequest.class,
      responseType = com.google.cloud.contactcenterinsights.v1alpha1.Conversation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.GetConversationRequest,
      com.google.cloud.contactcenterinsights.v1alpha1.Conversation> getGetConversationMethod() {
    io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.GetConversationRequest, com.google.cloud.contactcenterinsights.v1alpha1.Conversation> getGetConversationMethod;
    if ((getGetConversationMethod = ContactCenterInsightsGrpc.getGetConversationMethod) == null) {
      synchronized (ContactCenterInsightsGrpc.class) {
        if ((getGetConversationMethod = ContactCenterInsightsGrpc.getGetConversationMethod) == null) {
          ContactCenterInsightsGrpc.getGetConversationMethod = getGetConversationMethod =
              io.grpc.MethodDescriptor.<com.google.cloud.contactcenterinsights.v1alpha1.GetConversationRequest, com.google.cloud.contactcenterinsights.v1alpha1.Conversation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetConversation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.GetConversationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.Conversation.getDefaultInstance()))
              .setSchemaDescriptor(new ContactCenterInsightsMethodDescriptorSupplier("GetConversation"))
              .build();
        }
      }
    }
    return getGetConversationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest,
      com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse> getListConversationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListConversations",
      requestType = com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest.class,
      responseType = com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest,
      com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse> getListConversationsMethod() {
    io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest, com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse> getListConversationsMethod;
    if ((getListConversationsMethod = ContactCenterInsightsGrpc.getListConversationsMethod) == null) {
      synchronized (ContactCenterInsightsGrpc.class) {
        if ((getListConversationsMethod = ContactCenterInsightsGrpc.getListConversationsMethod) == null) {
          ContactCenterInsightsGrpc.getListConversationsMethod = getListConversationsMethod =
              io.grpc.MethodDescriptor.<com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest, com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListConversations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ContactCenterInsightsMethodDescriptorSupplier("ListConversations"))
              .build();
        }
      }
    }
    return getListConversationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.DeleteConversationRequest,
      com.google.protobuf.Empty> getDeleteConversationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteConversation",
      requestType = com.google.cloud.contactcenterinsights.v1alpha1.DeleteConversationRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.DeleteConversationRequest,
      com.google.protobuf.Empty> getDeleteConversationMethod() {
    io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.DeleteConversationRequest, com.google.protobuf.Empty> getDeleteConversationMethod;
    if ((getDeleteConversationMethod = ContactCenterInsightsGrpc.getDeleteConversationMethod) == null) {
      synchronized (ContactCenterInsightsGrpc.class) {
        if ((getDeleteConversationMethod = ContactCenterInsightsGrpc.getDeleteConversationMethod) == null) {
          ContactCenterInsightsGrpc.getDeleteConversationMethod = getDeleteConversationMethod =
              io.grpc.MethodDescriptor.<com.google.cloud.contactcenterinsights.v1alpha1.DeleteConversationRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteConversation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.DeleteConversationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ContactCenterInsightsMethodDescriptorSupplier("DeleteConversation"))
              .build();
        }
      }
    }
    return getDeleteConversationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.CreateAnalysisRequest,
      com.google.longrunning.Operation> getCreateAnalysisMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateAnalysis",
      requestType = com.google.cloud.contactcenterinsights.v1alpha1.CreateAnalysisRequest.class,
      responseType = com.google.longrunning.Operation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.CreateAnalysisRequest,
      com.google.longrunning.Operation> getCreateAnalysisMethod() {
    io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.CreateAnalysisRequest, com.google.longrunning.Operation> getCreateAnalysisMethod;
    if ((getCreateAnalysisMethod = ContactCenterInsightsGrpc.getCreateAnalysisMethod) == null) {
      synchronized (ContactCenterInsightsGrpc.class) {
        if ((getCreateAnalysisMethod = ContactCenterInsightsGrpc.getCreateAnalysisMethod) == null) {
          ContactCenterInsightsGrpc.getCreateAnalysisMethod = getCreateAnalysisMethod =
              io.grpc.MethodDescriptor.<com.google.cloud.contactcenterinsights.v1alpha1.CreateAnalysisRequest, com.google.longrunning.Operation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateAnalysis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.CreateAnalysisRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.longrunning.Operation.getDefaultInstance()))
              .setSchemaDescriptor(new ContactCenterInsightsMethodDescriptorSupplier("CreateAnalysis"))
              .build();
        }
      }
    }
    return getCreateAnalysisMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.GetAnalysisRequest,
      com.google.cloud.contactcenterinsights.v1alpha1.Analysis> getGetAnalysisMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAnalysis",
      requestType = com.google.cloud.contactcenterinsights.v1alpha1.GetAnalysisRequest.class,
      responseType = com.google.cloud.contactcenterinsights.v1alpha1.Analysis.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.GetAnalysisRequest,
      com.google.cloud.contactcenterinsights.v1alpha1.Analysis> getGetAnalysisMethod() {
    io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.GetAnalysisRequest, com.google.cloud.contactcenterinsights.v1alpha1.Analysis> getGetAnalysisMethod;
    if ((getGetAnalysisMethod = ContactCenterInsightsGrpc.getGetAnalysisMethod) == null) {
      synchronized (ContactCenterInsightsGrpc.class) {
        if ((getGetAnalysisMethod = ContactCenterInsightsGrpc.getGetAnalysisMethod) == null) {
          ContactCenterInsightsGrpc.getGetAnalysisMethod = getGetAnalysisMethod =
              io.grpc.MethodDescriptor.<com.google.cloud.contactcenterinsights.v1alpha1.GetAnalysisRequest, com.google.cloud.contactcenterinsights.v1alpha1.Analysis>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAnalysis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.GetAnalysisRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.Analysis.getDefaultInstance()))
              .setSchemaDescriptor(new ContactCenterInsightsMethodDescriptorSupplier("GetAnalysis"))
              .build();
        }
      }
    }
    return getGetAnalysisMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest,
      com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse> getListAnalysesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListAnalyses",
      requestType = com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest.class,
      responseType = com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest,
      com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse> getListAnalysesMethod() {
    io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest, com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse> getListAnalysesMethod;
    if ((getListAnalysesMethod = ContactCenterInsightsGrpc.getListAnalysesMethod) == null) {
      synchronized (ContactCenterInsightsGrpc.class) {
        if ((getListAnalysesMethod = ContactCenterInsightsGrpc.getListAnalysesMethod) == null) {
          ContactCenterInsightsGrpc.getListAnalysesMethod = getListAnalysesMethod =
              io.grpc.MethodDescriptor.<com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest, com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListAnalyses"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ContactCenterInsightsMethodDescriptorSupplier("ListAnalyses"))
              .build();
        }
      }
    }
    return getListAnalysesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.DeleteAnalysisRequest,
      com.google.protobuf.Empty> getDeleteAnalysisMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteAnalysis",
      requestType = com.google.cloud.contactcenterinsights.v1alpha1.DeleteAnalysisRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.DeleteAnalysisRequest,
      com.google.protobuf.Empty> getDeleteAnalysisMethod() {
    io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.DeleteAnalysisRequest, com.google.protobuf.Empty> getDeleteAnalysisMethod;
    if ((getDeleteAnalysisMethod = ContactCenterInsightsGrpc.getDeleteAnalysisMethod) == null) {
      synchronized (ContactCenterInsightsGrpc.class) {
        if ((getDeleteAnalysisMethod = ContactCenterInsightsGrpc.getDeleteAnalysisMethod) == null) {
          ContactCenterInsightsGrpc.getDeleteAnalysisMethod = getDeleteAnalysisMethod =
              io.grpc.MethodDescriptor.<com.google.cloud.contactcenterinsights.v1alpha1.DeleteAnalysisRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteAnalysis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.DeleteAnalysisRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ContactCenterInsightsMethodDescriptorSupplier("DeleteAnalysis"))
              .build();
        }
      }
    }
    return getDeleteAnalysisMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.ExportInsightsDataRequest,
      com.google.longrunning.Operation> getExportInsightsDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ExportInsightsData",
      requestType = com.google.cloud.contactcenterinsights.v1alpha1.ExportInsightsDataRequest.class,
      responseType = com.google.longrunning.Operation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.ExportInsightsDataRequest,
      com.google.longrunning.Operation> getExportInsightsDataMethod() {
    io.grpc.MethodDescriptor<com.google.cloud.contactcenterinsights.v1alpha1.ExportInsightsDataRequest, com.google.longrunning.Operation> getExportInsightsDataMethod;
    if ((getExportInsightsDataMethod = ContactCenterInsightsGrpc.getExportInsightsDataMethod) == null) {
      synchronized (ContactCenterInsightsGrpc.class) {
        if ((getExportInsightsDataMethod = ContactCenterInsightsGrpc.getExportInsightsDataMethod) == null) {
          ContactCenterInsightsGrpc.getExportInsightsDataMethod = getExportInsightsDataMethod =
              io.grpc.MethodDescriptor.<com.google.cloud.contactcenterinsights.v1alpha1.ExportInsightsDataRequest, com.google.longrunning.Operation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ExportInsightsData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.cloud.contactcenterinsights.v1alpha1.ExportInsightsDataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.longrunning.Operation.getDefaultInstance()))
              .setSchemaDescriptor(new ContactCenterInsightsMethodDescriptorSupplier("ExportInsightsData"))
              .build();
        }
      }
    }
    return getExportInsightsDataMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ContactCenterInsightsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ContactCenterInsightsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ContactCenterInsightsStub>() {
        @java.lang.Override
        public ContactCenterInsightsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ContactCenterInsightsStub(channel, callOptions);
        }
      };
    return ContactCenterInsightsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ContactCenterInsightsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ContactCenterInsightsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ContactCenterInsightsBlockingStub>() {
        @java.lang.Override
        public ContactCenterInsightsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ContactCenterInsightsBlockingStub(channel, callOptions);
        }
      };
    return ContactCenterInsightsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ContactCenterInsightsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ContactCenterInsightsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ContactCenterInsightsFutureStub>() {
        @java.lang.Override
        public ContactCenterInsightsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ContactCenterInsightsFutureStub(channel, callOptions);
        }
      };
    return ContactCenterInsightsFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * An API that lets users analyze and explore their business conversation data.
   * </pre>
   */
  public static abstract class ContactCenterInsightsImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Creates a conversation.
     * </pre>
     */
    public void createConversation(com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest request,
        io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.Conversation> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateConversationMethod(), responseObserver);
    }

    /**
     * <pre>
     * Updates a conversation.
     * </pre>
     */
    public void updateConversation(com.google.cloud.contactcenterinsights.v1alpha1.UpdateConversationRequest request,
        io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.Conversation> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateConversationMethod(), responseObserver);
    }

    /**
     * <pre>
     * Gets a conversation.
     * </pre>
     */
    public void getConversation(com.google.cloud.contactcenterinsights.v1alpha1.GetConversationRequest request,
        io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.Conversation> responseObserver) {
      asyncUnimplementedUnaryCall(getGetConversationMethod(), responseObserver);
    }

    /**
     * <pre>
     * Lists conversations.
     * </pre>
     */
    public void listConversations(com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest request,
        io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListConversationsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Deletes a conversation.
     * </pre>
     */
    public void deleteConversation(com.google.cloud.contactcenterinsights.v1alpha1.DeleteConversationRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteConversationMethod(), responseObserver);
    }

    /**
     * <pre>
     * Creates an analysis. The long running operation is done when the analysis
     * has completed.
     * </pre>
     */
    public void createAnalysis(com.google.cloud.contactcenterinsights.v1alpha1.CreateAnalysisRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateAnalysisMethod(), responseObserver);
    }

    /**
     * <pre>
     * Gets an analysis.
     * </pre>
     */
    public void getAnalysis(com.google.cloud.contactcenterinsights.v1alpha1.GetAnalysisRequest request,
        io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.Analysis> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAnalysisMethod(), responseObserver);
    }

    /**
     * <pre>
     * Lists analyses.
     * </pre>
     */
    public void listAnalyses(com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest request,
        io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListAnalysesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Deletes an analysis.
     * </pre>
     */
    public void deleteAnalysis(com.google.cloud.contactcenterinsights.v1alpha1.DeleteAnalysisRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteAnalysisMethod(), responseObserver);
    }

    /**
     * <pre>
     * Export insights data to a destination defined in the request body.
     * </pre>
     */
    public void exportInsightsData(com.google.cloud.contactcenterinsights.v1alpha1.ExportInsightsDataRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnimplementedUnaryCall(getExportInsightsDataMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateConversationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest,
                com.google.cloud.contactcenterinsights.v1alpha1.Conversation>(
                  this, METHODID_CREATE_CONVERSATION)))
          .addMethod(
            getUpdateConversationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.cloud.contactcenterinsights.v1alpha1.UpdateConversationRequest,
                com.google.cloud.contactcenterinsights.v1alpha1.Conversation>(
                  this, METHODID_UPDATE_CONVERSATION)))
          .addMethod(
            getGetConversationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.cloud.contactcenterinsights.v1alpha1.GetConversationRequest,
                com.google.cloud.contactcenterinsights.v1alpha1.Conversation>(
                  this, METHODID_GET_CONVERSATION)))
          .addMethod(
            getListConversationsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest,
                com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse>(
                  this, METHODID_LIST_CONVERSATIONS)))
          .addMethod(
            getDeleteConversationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.cloud.contactcenterinsights.v1alpha1.DeleteConversationRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_DELETE_CONVERSATION)))
          .addMethod(
            getCreateAnalysisMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.cloud.contactcenterinsights.v1alpha1.CreateAnalysisRequest,
                com.google.longrunning.Operation>(
                  this, METHODID_CREATE_ANALYSIS)))
          .addMethod(
            getGetAnalysisMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.cloud.contactcenterinsights.v1alpha1.GetAnalysisRequest,
                com.google.cloud.contactcenterinsights.v1alpha1.Analysis>(
                  this, METHODID_GET_ANALYSIS)))
          .addMethod(
            getListAnalysesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest,
                com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse>(
                  this, METHODID_LIST_ANALYSES)))
          .addMethod(
            getDeleteAnalysisMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.cloud.contactcenterinsights.v1alpha1.DeleteAnalysisRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_DELETE_ANALYSIS)))
          .addMethod(
            getExportInsightsDataMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.cloud.contactcenterinsights.v1alpha1.ExportInsightsDataRequest,
                com.google.longrunning.Operation>(
                  this, METHODID_EXPORT_INSIGHTS_DATA)))
          .build();
    }
  }

  /**
   * <pre>
   * An API that lets users analyze and explore their business conversation data.
   * </pre>
   */
  public static final class ContactCenterInsightsStub extends io.grpc.stub.AbstractAsyncStub<ContactCenterInsightsStub> {
    private ContactCenterInsightsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ContactCenterInsightsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ContactCenterInsightsStub(channel, callOptions);
    }

    /**
     * <pre>
     * Creates a conversation.
     * </pre>
     */
    public void createConversation(com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest request,
        io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.Conversation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateConversationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Updates a conversation.
     * </pre>
     */
    public void updateConversation(com.google.cloud.contactcenterinsights.v1alpha1.UpdateConversationRequest request,
        io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.Conversation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateConversationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Gets a conversation.
     * </pre>
     */
    public void getConversation(com.google.cloud.contactcenterinsights.v1alpha1.GetConversationRequest request,
        io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.Conversation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetConversationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Lists conversations.
     * </pre>
     */
    public void listConversations(com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest request,
        io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListConversationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Deletes a conversation.
     * </pre>
     */
    public void deleteConversation(com.google.cloud.contactcenterinsights.v1alpha1.DeleteConversationRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteConversationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Creates an analysis. The long running operation is done when the analysis
     * has completed.
     * </pre>
     */
    public void createAnalysis(com.google.cloud.contactcenterinsights.v1alpha1.CreateAnalysisRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateAnalysisMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Gets an analysis.
     * </pre>
     */
    public void getAnalysis(com.google.cloud.contactcenterinsights.v1alpha1.GetAnalysisRequest request,
        io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.Analysis> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAnalysisMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Lists analyses.
     * </pre>
     */
    public void listAnalyses(com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest request,
        io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListAnalysesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Deletes an analysis.
     * </pre>
     */
    public void deleteAnalysis(com.google.cloud.contactcenterinsights.v1alpha1.DeleteAnalysisRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteAnalysisMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Export insights data to a destination defined in the request body.
     * </pre>
     */
    public void exportInsightsData(com.google.cloud.contactcenterinsights.v1alpha1.ExportInsightsDataRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getExportInsightsDataMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * An API that lets users analyze and explore their business conversation data.
   * </pre>
   */
  public static final class ContactCenterInsightsBlockingStub extends io.grpc.stub.AbstractBlockingStub<ContactCenterInsightsBlockingStub> {
    private ContactCenterInsightsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ContactCenterInsightsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ContactCenterInsightsBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Creates a conversation.
     * </pre>
     */
    public com.google.cloud.contactcenterinsights.v1alpha1.Conversation createConversation(com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateConversationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Updates a conversation.
     * </pre>
     */
    public com.google.cloud.contactcenterinsights.v1alpha1.Conversation updateConversation(com.google.cloud.contactcenterinsights.v1alpha1.UpdateConversationRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateConversationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Gets a conversation.
     * </pre>
     */
    public com.google.cloud.contactcenterinsights.v1alpha1.Conversation getConversation(com.google.cloud.contactcenterinsights.v1alpha1.GetConversationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetConversationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Lists conversations.
     * </pre>
     */
    public com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse listConversations(com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListConversationsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Deletes a conversation.
     * </pre>
     */
    public com.google.protobuf.Empty deleteConversation(com.google.cloud.contactcenterinsights.v1alpha1.DeleteConversationRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteConversationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Creates an analysis. The long running operation is done when the analysis
     * has completed.
     * </pre>
     */
    public com.google.longrunning.Operation createAnalysis(com.google.cloud.contactcenterinsights.v1alpha1.CreateAnalysisRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateAnalysisMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Gets an analysis.
     * </pre>
     */
    public com.google.cloud.contactcenterinsights.v1alpha1.Analysis getAnalysis(com.google.cloud.contactcenterinsights.v1alpha1.GetAnalysisRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetAnalysisMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Lists analyses.
     * </pre>
     */
    public com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse listAnalyses(com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest request) {
      return blockingUnaryCall(
          getChannel(), getListAnalysesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Deletes an analysis.
     * </pre>
     */
    public com.google.protobuf.Empty deleteAnalysis(com.google.cloud.contactcenterinsights.v1alpha1.DeleteAnalysisRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteAnalysisMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Export insights data to a destination defined in the request body.
     * </pre>
     */
    public com.google.longrunning.Operation exportInsightsData(com.google.cloud.contactcenterinsights.v1alpha1.ExportInsightsDataRequest request) {
      return blockingUnaryCall(
          getChannel(), getExportInsightsDataMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * An API that lets users analyze and explore their business conversation data.
   * </pre>
   */
  public static final class ContactCenterInsightsFutureStub extends io.grpc.stub.AbstractFutureStub<ContactCenterInsightsFutureStub> {
    private ContactCenterInsightsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ContactCenterInsightsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ContactCenterInsightsFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Creates a conversation.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.cloud.contactcenterinsights.v1alpha1.Conversation> createConversation(
        com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateConversationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Updates a conversation.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.cloud.contactcenterinsights.v1alpha1.Conversation> updateConversation(
        com.google.cloud.contactcenterinsights.v1alpha1.UpdateConversationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateConversationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Gets a conversation.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.cloud.contactcenterinsights.v1alpha1.Conversation> getConversation(
        com.google.cloud.contactcenterinsights.v1alpha1.GetConversationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetConversationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Lists conversations.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse> listConversations(
        com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListConversationsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Deletes a conversation.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deleteConversation(
        com.google.cloud.contactcenterinsights.v1alpha1.DeleteConversationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteConversationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Creates an analysis. The long running operation is done when the analysis
     * has completed.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> createAnalysis(
        com.google.cloud.contactcenterinsights.v1alpha1.CreateAnalysisRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateAnalysisMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Gets an analysis.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.cloud.contactcenterinsights.v1alpha1.Analysis> getAnalysis(
        com.google.cloud.contactcenterinsights.v1alpha1.GetAnalysisRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAnalysisMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Lists analyses.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse> listAnalyses(
        com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListAnalysesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Deletes an analysis.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deleteAnalysis(
        com.google.cloud.contactcenterinsights.v1alpha1.DeleteAnalysisRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteAnalysisMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Export insights data to a destination defined in the request body.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> exportInsightsData(
        com.google.cloud.contactcenterinsights.v1alpha1.ExportInsightsDataRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getExportInsightsDataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_CONVERSATION = 0;
  private static final int METHODID_UPDATE_CONVERSATION = 1;
  private static final int METHODID_GET_CONVERSATION = 2;
  private static final int METHODID_LIST_CONVERSATIONS = 3;
  private static final int METHODID_DELETE_CONVERSATION = 4;
  private static final int METHODID_CREATE_ANALYSIS = 5;
  private static final int METHODID_GET_ANALYSIS = 6;
  private static final int METHODID_LIST_ANALYSES = 7;
  private static final int METHODID_DELETE_ANALYSIS = 8;
  private static final int METHODID_EXPORT_INSIGHTS_DATA = 9;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ContactCenterInsightsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ContactCenterInsightsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_CONVERSATION:
          serviceImpl.createConversation((com.google.cloud.contactcenterinsights.v1alpha1.CreateConversationRequest) request,
              (io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.Conversation>) responseObserver);
          break;
        case METHODID_UPDATE_CONVERSATION:
          serviceImpl.updateConversation((com.google.cloud.contactcenterinsights.v1alpha1.UpdateConversationRequest) request,
              (io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.Conversation>) responseObserver);
          break;
        case METHODID_GET_CONVERSATION:
          serviceImpl.getConversation((com.google.cloud.contactcenterinsights.v1alpha1.GetConversationRequest) request,
              (io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.Conversation>) responseObserver);
          break;
        case METHODID_LIST_CONVERSATIONS:
          serviceImpl.listConversations((com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsRequest) request,
              (io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.ListConversationsResponse>) responseObserver);
          break;
        case METHODID_DELETE_CONVERSATION:
          serviceImpl.deleteConversation((com.google.cloud.contactcenterinsights.v1alpha1.DeleteConversationRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_CREATE_ANALYSIS:
          serviceImpl.createAnalysis((com.google.cloud.contactcenterinsights.v1alpha1.CreateAnalysisRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.Operation>) responseObserver);
          break;
        case METHODID_GET_ANALYSIS:
          serviceImpl.getAnalysis((com.google.cloud.contactcenterinsights.v1alpha1.GetAnalysisRequest) request,
              (io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.Analysis>) responseObserver);
          break;
        case METHODID_LIST_ANALYSES:
          serviceImpl.listAnalyses((com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesRequest) request,
              (io.grpc.stub.StreamObserver<com.google.cloud.contactcenterinsights.v1alpha1.ListAnalysesResponse>) responseObserver);
          break;
        case METHODID_DELETE_ANALYSIS:
          serviceImpl.deleteAnalysis((com.google.cloud.contactcenterinsights.v1alpha1.DeleteAnalysisRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_EXPORT_INSIGHTS_DATA:
          serviceImpl.exportInsightsData((com.google.cloud.contactcenterinsights.v1alpha1.ExportInsightsDataRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.Operation>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ContactCenterInsightsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ContactCenterInsightsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.google.cloud.contactcenterinsights.v1alpha1.ContactCenterInsightsProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ContactCenterInsights");
    }
  }

  private static final class ContactCenterInsightsFileDescriptorSupplier
      extends ContactCenterInsightsBaseDescriptorSupplier {
    ContactCenterInsightsFileDescriptorSupplier() {}
  }

  private static final class ContactCenterInsightsMethodDescriptorSupplier
      extends ContactCenterInsightsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ContactCenterInsightsMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ContactCenterInsightsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ContactCenterInsightsFileDescriptorSupplier())
              .addMethod(getCreateConversationMethod())
              .addMethod(getUpdateConversationMethod())
              .addMethod(getGetConversationMethod())
              .addMethod(getListConversationsMethod())
              .addMethod(getDeleteConversationMethod())
              .addMethod(getCreateAnalysisMethod())
              .addMethod(getGetAnalysisMethod())
              .addMethod(getListAnalysesMethod())
              .addMethod(getDeleteAnalysisMethod())
              .addMethod(getExportInsightsDataMethod())
              .build();
        }
      }
    }
    return result;
  }
}
