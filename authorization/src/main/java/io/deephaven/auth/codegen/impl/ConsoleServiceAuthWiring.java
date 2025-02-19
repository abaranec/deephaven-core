//
// Copyright (c) 2016-2025 Deephaven Data Labs and Patent Pending
//
package io.deephaven.auth.codegen.impl;

import io.deephaven.auth.AuthContext;
import io.deephaven.auth.ServiceAuthWiring;
import io.deephaven.proto.backplane.script.grpc.AutoCompleteRequest;
import io.deephaven.proto.backplane.script.grpc.BindTableToVariableRequest;
import io.deephaven.proto.backplane.script.grpc.CancelAutoCompleteRequest;
import io.deephaven.proto.backplane.script.grpc.CancelCommandRequest;
import io.deephaven.proto.backplane.script.grpc.ConsoleServiceGrpc;
import io.deephaven.proto.backplane.script.grpc.ExecuteCommandRequest;
import io.deephaven.proto.backplane.script.grpc.GetConsoleTypesRequest;
import io.deephaven.proto.backplane.script.grpc.GetHeapInfoRequest;
import io.deephaven.proto.backplane.script.grpc.LogSubscriptionRequest;
import io.deephaven.proto.backplane.script.grpc.StartConsoleRequest;
import io.grpc.ServerServiceDefinition;

/**
 * This interface provides type-safe authorization hooks for ConsoleServiceGrpc.
 */
public interface ConsoleServiceAuthWiring extends ServiceAuthWiring<ConsoleServiceGrpc.ConsoleServiceImplBase> {
    /**
     * Wrap the real implementation with authorization checks.
     *
     * @param delegate the real service implementation
     * @return the wrapped service implementation
     */
    default ServerServiceDefinition intercept(ConsoleServiceGrpc.ConsoleServiceImplBase delegate) {
        final ServerServiceDefinition service = delegate.bindService();
        final ServerServiceDefinition.Builder serviceBuilder =
                ServerServiceDefinition.builder(service.getServiceDescriptor());

        serviceBuilder.addMethod(ServiceAuthWiring.intercept(
                service, "GetConsoleTypes", null, this::onMessageReceivedGetConsoleTypes));
        serviceBuilder.addMethod(ServiceAuthWiring.intercept(
                service, "StartConsole", null, this::onMessageReceivedStartConsole));
        serviceBuilder.addMethod(ServiceAuthWiring.intercept(
                service, "GetHeapInfo", null, this::onMessageReceivedGetHeapInfo));
        serviceBuilder.addMethod(ServiceAuthWiring.intercept(
                service, "SubscribeToLogs", null, this::onMessageReceivedSubscribeToLogs));
        serviceBuilder.addMethod(ServiceAuthWiring.intercept(
                service, "ExecuteCommand", null, this::onMessageReceivedExecuteCommand));
        serviceBuilder.addMethod(ServiceAuthWiring.intercept(
                service, "CancelCommand", null, this::onMessageReceivedCancelCommand));
        serviceBuilder.addMethod(ServiceAuthWiring.intercept(
                service, "BindTableToVariable", null, this::onMessageReceivedBindTableToVariable));
        serviceBuilder.addMethod(ServiceAuthWiring.intercept(
                service, "AutoCompleteStream", this::onCallStartedAutoCompleteStream,
                this::onMessageReceivedAutoCompleteStream));
        serviceBuilder.addMethod(ServiceAuthWiring.intercept(
                service, "CancelAutoComplete", null, this::onMessageReceivedCancelAutoComplete));
        serviceBuilder.addMethod(ServiceAuthWiring.intercept(
                service, "OpenAutoCompleteStream", null, this::onMessageReceivedOpenAutoCompleteStream));
        serviceBuilder.addMethod(ServiceAuthWiring.intercept(
                service, "NextAutoCompleteStream", null, this::onMessageReceivedNextAutoCompleteStream));

        return serviceBuilder.build();
    }

    /**
     * Authorize a request to GetConsoleTypes.
     *
     * @param authContext the authentication context of the request
     * @param request the request to authorize
     * @throws io.grpc.StatusRuntimeException if the user is not authorized to invoke GetConsoleTypes
     */
    void onMessageReceivedGetConsoleTypes(AuthContext authContext, GetConsoleTypesRequest request);

    /**
     * Authorize a request to StartConsole.
     *
     * @param authContext the authentication context of the request
     * @param request the request to authorize
     * @throws io.grpc.StatusRuntimeException if the user is not authorized to invoke StartConsole
     */
    void onMessageReceivedStartConsole(AuthContext authContext, StartConsoleRequest request);

    /**
     * Authorize a request to GetHeapInfo.
     *
     * @param authContext the authentication context of the request
     * @param request the request to authorize
     * @throws io.grpc.StatusRuntimeException if the user is not authorized to invoke GetHeapInfo
     */
    void onMessageReceivedGetHeapInfo(AuthContext authContext, GetHeapInfoRequest request);

    /**
     * Authorize a request to SubscribeToLogs.
     *
     * @param authContext the authentication context of the request
     * @param request the request to authorize
     * @throws io.grpc.StatusRuntimeException if the user is not authorized to invoke SubscribeToLogs
     */
    void onMessageReceivedSubscribeToLogs(AuthContext authContext, LogSubscriptionRequest request);

    /**
     * Authorize a request to ExecuteCommand.
     *
     * @param authContext the authentication context of the request
     * @param request the request to authorize
     * @throws io.grpc.StatusRuntimeException if the user is not authorized to invoke ExecuteCommand
     */
    void onMessageReceivedExecuteCommand(AuthContext authContext, ExecuteCommandRequest request);

    /**
     * Authorize a request to CancelCommand.
     *
     * @param authContext the authentication context of the request
     * @param request the request to authorize
     * @throws io.grpc.StatusRuntimeException if the user is not authorized to invoke CancelCommand
     */
    void onMessageReceivedCancelCommand(AuthContext authContext, CancelCommandRequest request);

    /**
     * Authorize a request to BindTableToVariable.
     *
     * @param authContext the authentication context of the request
     * @param request the request to authorize
     * @throws io.grpc.StatusRuntimeException if the user is not authorized to invoke BindTableToVariable
     */
    void onMessageReceivedBindTableToVariable(AuthContext authContext,
            BindTableToVariableRequest request);

    /**
     * Authorize a request to open a client-streaming rpc AutoCompleteStream.
     *
     * @param authContext the authentication context of the request
     * @throws io.grpc.StatusRuntimeException if the user is not authorized to invoke AutoCompleteStream
     */
    void onCallStartedAutoCompleteStream(AuthContext authContext);

    /**
     * Authorize a request to AutoCompleteStream.
     *
     * @param authContext the authentication context of the request
     * @param request the request to authorize
     * @throws io.grpc.StatusRuntimeException if the user is not authorized to invoke AutoCompleteStream
     */
    void onMessageReceivedAutoCompleteStream(AuthContext authContext, AutoCompleteRequest request);

    /**
     * Authorize a request to CancelAutoComplete.
     *
     * @param authContext the authentication context of the request
     * @param request the request to authorize
     * @throws io.grpc.StatusRuntimeException if the user is not authorized to invoke CancelAutoComplete
     */
    void onMessageReceivedCancelAutoComplete(AuthContext authContext,
            CancelAutoCompleteRequest request);

    /**
     * Authorize a request to OpenAutoCompleteStream.
     *
     * @param authContext the authentication context of the request
     * @param request the request to authorize
     * @throws io.grpc.StatusRuntimeException if the user is not authorized to invoke OpenAutoCompleteStream
     */
    void onMessageReceivedOpenAutoCompleteStream(AuthContext authContext,
            AutoCompleteRequest request);

    /**
     * Authorize a request to NextAutoCompleteStream.
     *
     * @param authContext the authentication context of the request
     * @param request the request to authorize
     * @throws io.grpc.StatusRuntimeException if the user is not authorized to invoke NextAutoCompleteStream
     */
    void onMessageReceivedNextAutoCompleteStream(AuthContext authContext,
            AutoCompleteRequest request);

    class AllowAll implements ConsoleServiceAuthWiring {
        public void onMessageReceivedGetConsoleTypes(AuthContext authContext,
                GetConsoleTypesRequest request) {}

        public void onMessageReceivedStartConsole(AuthContext authContext,
                StartConsoleRequest request) {}

        public void onMessageReceivedGetHeapInfo(AuthContext authContext, GetHeapInfoRequest request) {}

        public void onMessageReceivedSubscribeToLogs(AuthContext authContext,
                LogSubscriptionRequest request) {}

        public void onMessageReceivedExecuteCommand(AuthContext authContext,
                ExecuteCommandRequest request) {}

        public void onMessageReceivedCancelCommand(AuthContext authContext,
                CancelCommandRequest request) {}

        public void onMessageReceivedBindTableToVariable(AuthContext authContext,
                BindTableToVariableRequest request) {}

        public void onCallStartedAutoCompleteStream(AuthContext authContext) {}

        public void onMessageReceivedAutoCompleteStream(AuthContext authContext,
                AutoCompleteRequest request) {}

        public void onMessageReceivedCancelAutoComplete(AuthContext authContext,
                CancelAutoCompleteRequest request) {}

        public void onMessageReceivedOpenAutoCompleteStream(AuthContext authContext,
                AutoCompleteRequest request) {}

        public void onMessageReceivedNextAutoCompleteStream(AuthContext authContext,
                AutoCompleteRequest request) {}
    }

    class DenyAll implements ConsoleServiceAuthWiring {
        public void onMessageReceivedGetConsoleTypes(AuthContext authContext,
                GetConsoleTypesRequest request) {
            ServiceAuthWiring.operationNotAllowed();
        }

        public void onMessageReceivedStartConsole(AuthContext authContext,
                StartConsoleRequest request) {
            ServiceAuthWiring.operationNotAllowed();
        }

        public void onMessageReceivedGetHeapInfo(AuthContext authContext, GetHeapInfoRequest request) {
            ServiceAuthWiring.operationNotAllowed();
        }

        public void onMessageReceivedSubscribeToLogs(AuthContext authContext,
                LogSubscriptionRequest request) {
            ServiceAuthWiring.operationNotAllowed();
        }

        public void onMessageReceivedExecuteCommand(AuthContext authContext,
                ExecuteCommandRequest request) {
            ServiceAuthWiring.operationNotAllowed();
        }

        public void onMessageReceivedCancelCommand(AuthContext authContext,
                CancelCommandRequest request) {
            ServiceAuthWiring.operationNotAllowed();
        }

        public void onMessageReceivedBindTableToVariable(AuthContext authContext,
                BindTableToVariableRequest request) {
            ServiceAuthWiring.operationNotAllowed();
        }

        public void onCallStartedAutoCompleteStream(AuthContext authContext) {
            ServiceAuthWiring.operationNotAllowed();
        }

        public void onMessageReceivedAutoCompleteStream(AuthContext authContext,
                AutoCompleteRequest request) {
            ServiceAuthWiring.operationNotAllowed();
        }

        public void onMessageReceivedCancelAutoComplete(AuthContext authContext,
                CancelAutoCompleteRequest request) {
            ServiceAuthWiring.operationNotAllowed();
        }

        public void onMessageReceivedOpenAutoCompleteStream(AuthContext authContext,
                AutoCompleteRequest request) {
            ServiceAuthWiring.operationNotAllowed();
        }

        public void onMessageReceivedNextAutoCompleteStream(AuthContext authContext,
                AutoCompleteRequest request) {
            ServiceAuthWiring.operationNotAllowed();
        }
    }

    class TestUseOnly implements ConsoleServiceAuthWiring {
        public ConsoleServiceAuthWiring delegate;

        public void onMessageReceivedGetConsoleTypes(AuthContext authContext,
                GetConsoleTypesRequest request) {
            if (delegate != null) {
                delegate.onMessageReceivedGetConsoleTypes(authContext, request);
            }
        }

        public void onMessageReceivedStartConsole(AuthContext authContext,
                StartConsoleRequest request) {
            if (delegate != null) {
                delegate.onMessageReceivedStartConsole(authContext, request);
            }
        }

        public void onMessageReceivedGetHeapInfo(AuthContext authContext, GetHeapInfoRequest request) {
            if (delegate != null) {
                delegate.onMessageReceivedGetHeapInfo(authContext, request);
            }
        }

        public void onMessageReceivedSubscribeToLogs(AuthContext authContext,
                LogSubscriptionRequest request) {
            if (delegate != null) {
                delegate.onMessageReceivedSubscribeToLogs(authContext, request);
            }
        }

        public void onMessageReceivedExecuteCommand(AuthContext authContext,
                ExecuteCommandRequest request) {
            if (delegate != null) {
                delegate.onMessageReceivedExecuteCommand(authContext, request);
            }
        }

        public void onMessageReceivedCancelCommand(AuthContext authContext,
                CancelCommandRequest request) {
            if (delegate != null) {
                delegate.onMessageReceivedCancelCommand(authContext, request);
            }
        }

        public void onMessageReceivedBindTableToVariable(AuthContext authContext,
                BindTableToVariableRequest request) {
            if (delegate != null) {
                delegate.onMessageReceivedBindTableToVariable(authContext, request);
            }
        }

        public void onCallStartedAutoCompleteStream(AuthContext authContext) {
            if (delegate != null) {
                delegate.onCallStartedAutoCompleteStream(authContext);
            }
        }

        public void onMessageReceivedAutoCompleteStream(AuthContext authContext,
                AutoCompleteRequest request) {
            if (delegate != null) {
                delegate.onMessageReceivedAutoCompleteStream(authContext, request);
            }
        }

        public void onMessageReceivedCancelAutoComplete(AuthContext authContext,
                CancelAutoCompleteRequest request) {
            if (delegate != null) {
                delegate.onMessageReceivedCancelAutoComplete(authContext, request);
            }
        }

        public void onMessageReceivedOpenAutoCompleteStream(AuthContext authContext,
                AutoCompleteRequest request) {
            if (delegate != null) {
                delegate.onMessageReceivedOpenAutoCompleteStream(authContext, request);
            }
        }

        public void onMessageReceivedNextAutoCompleteStream(AuthContext authContext,
                AutoCompleteRequest request) {
            if (delegate != null) {
                delegate.onMessageReceivedNextAutoCompleteStream(authContext, request);
            }
        }
    }
}
