# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc
import warnings

from deephaven_core.proto import session_pb2 as deephaven__core_dot_proto_dot_session__pb2

GRPC_GENERATED_VERSION = '1.63.0'
GRPC_VERSION = grpc.__version__
EXPECTED_ERROR_RELEASE = '1.65.0'
SCHEDULED_RELEASE_DATE = 'June 25, 2024'
_version_not_supported = False

try:
    from grpc._utilities import first_version_is_lower
    _version_not_supported = first_version_is_lower(GRPC_VERSION, GRPC_GENERATED_VERSION)
except ImportError:
    _version_not_supported = True

if _version_not_supported:
    warnings.warn(
        f'The grpc package installed is at version {GRPC_VERSION},'
        + f' but the generated code in deephaven_core/proto/session_pb2_grpc.py depends on'
        + f' grpcio>={GRPC_GENERATED_VERSION}.'
        + f' Please upgrade your grpc module to grpcio>={GRPC_GENERATED_VERSION}'
        + f' or downgrade your generated code using grpcio-tools<={GRPC_VERSION}.'
        + f' This warning will become an error in {EXPECTED_ERROR_RELEASE},'
        + f' scheduled for release on {SCHEDULED_RELEASE_DATE}.',
        RuntimeWarning
    )


class SessionServiceStub(object):
    """
    User supplied Flight.Ticket(s) should begin with 'e' byte followed by an signed little-endian int. The client is only
    allowed to use the positive exportId key-space (client generated exportIds should be greater than 0). The client is
    encouraged to use a packed ranges of ids as this yields the smallest footprint server side for long running sessions.

    The client is responsible for releasing all Flight.Tickets that they create or that were created for them via a gRPC
    call. The documentation for the gRPC call will indicate that the exports must be released. Exports that need to be
    released will always be communicated over the session's ExportNotification stream.

    When a session ends, either explicitly or due to timeout, all exported objects in that session are released
    automatically.

    Some parts of the API return a Flight.Ticket that does not need to be released. It is not an error to attempt to
    release them.
    """

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.NewSession = channel.unary_unary(
                '/io.deephaven.proto.backplane.grpc.SessionService/NewSession',
                request_serializer=deephaven__core_dot_proto_dot_session__pb2.HandshakeRequest.SerializeToString,
                response_deserializer=deephaven__core_dot_proto_dot_session__pb2.HandshakeResponse.FromString,
                _registered_method=True)
        self.RefreshSessionToken = channel.unary_unary(
                '/io.deephaven.proto.backplane.grpc.SessionService/RefreshSessionToken',
                request_serializer=deephaven__core_dot_proto_dot_session__pb2.HandshakeRequest.SerializeToString,
                response_deserializer=deephaven__core_dot_proto_dot_session__pb2.HandshakeResponse.FromString,
                _registered_method=True)
        self.CloseSession = channel.unary_unary(
                '/io.deephaven.proto.backplane.grpc.SessionService/CloseSession',
                request_serializer=deephaven__core_dot_proto_dot_session__pb2.HandshakeRequest.SerializeToString,
                response_deserializer=deephaven__core_dot_proto_dot_session__pb2.CloseSessionResponse.FromString,
                _registered_method=True)
        self.Release = channel.unary_unary(
                '/io.deephaven.proto.backplane.grpc.SessionService/Release',
                request_serializer=deephaven__core_dot_proto_dot_session__pb2.ReleaseRequest.SerializeToString,
                response_deserializer=deephaven__core_dot_proto_dot_session__pb2.ReleaseResponse.FromString,
                _registered_method=True)
        self.ExportFromTicket = channel.unary_unary(
                '/io.deephaven.proto.backplane.grpc.SessionService/ExportFromTicket',
                request_serializer=deephaven__core_dot_proto_dot_session__pb2.ExportRequest.SerializeToString,
                response_deserializer=deephaven__core_dot_proto_dot_session__pb2.ExportResponse.FromString,
                _registered_method=True)
        self.PublishFromTicket = channel.unary_unary(
                '/io.deephaven.proto.backplane.grpc.SessionService/PublishFromTicket',
                request_serializer=deephaven__core_dot_proto_dot_session__pb2.PublishRequest.SerializeToString,
                response_deserializer=deephaven__core_dot_proto_dot_session__pb2.PublishResponse.FromString,
                _registered_method=True)
        self.ExportNotifications = channel.unary_stream(
                '/io.deephaven.proto.backplane.grpc.SessionService/ExportNotifications',
                request_serializer=deephaven__core_dot_proto_dot_session__pb2.ExportNotificationRequest.SerializeToString,
                response_deserializer=deephaven__core_dot_proto_dot_session__pb2.ExportNotification.FromString,
                _registered_method=True)
        self.TerminationNotification = channel.unary_unary(
                '/io.deephaven.proto.backplane.grpc.SessionService/TerminationNotification',
                request_serializer=deephaven__core_dot_proto_dot_session__pb2.TerminationNotificationRequest.SerializeToString,
                response_deserializer=deephaven__core_dot_proto_dot_session__pb2.TerminationNotificationResponse.FromString,
                _registered_method=True)


class SessionServiceServicer(object):
    """
    User supplied Flight.Ticket(s) should begin with 'e' byte followed by an signed little-endian int. The client is only
    allowed to use the positive exportId key-space (client generated exportIds should be greater than 0). The client is
    encouraged to use a packed ranges of ids as this yields the smallest footprint server side for long running sessions.

    The client is responsible for releasing all Flight.Tickets that they create or that were created for them via a gRPC
    call. The documentation for the gRPC call will indicate that the exports must be released. Exports that need to be
    released will always be communicated over the session's ExportNotification stream.

    When a session ends, either explicitly or due to timeout, all exported objects in that session are released
    automatically.

    Some parts of the API return a Flight.Ticket that does not need to be released. It is not an error to attempt to
    release them.
    """

    def NewSession(self, request, context):
        """
        Handshake between client and server to create a new session. The response includes a metadata header name and the
        token to send on every subsequent request. The auth mechanisms here are unary to best support grpc-web.

        Deprecated: Please use Flight's Handshake or http authorization headers instead.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def RefreshSessionToken(self, request, context):
        """
        Keep-alive a given token to ensure that a session is not cleaned prematurely. The response may include an updated
        token that should replace the existing token for subsequent requests.

        Deprecated: Please use Flight's Handshake with an empty payload.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def CloseSession(self, request, context):
        """
        Proactively close an open session. Sessions will automatically close on timeout. When a session is closed, all
        unreleased exports will be automatically released.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def Release(self, request, context):
        """
        Attempts to release an export by its ticket. Returns true if an existing export was found. It is the client's
        responsibility to release all resources they no longer want the server to hold on to. Proactively cancels work; do
        not release a ticket that is needed by dependent work that has not yet finished
        (i.e. the dependencies that are staying around should first be in EXPORTED state).
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def ExportFromTicket(self, request, context):
        """
        Makes a copy from a source ticket to a client managed result ticket. The source ticket does not need to be
        a client managed ticket.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def PublishFromTicket(self, request, context):
        """
        Makes a copy from a source ticket and publishes to a result ticket. Neither the source ticket, nor the destination
        ticket, need to be a client managed ticket.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def ExportNotifications(self, request, context):
        """
        Establish a stream to manage all session exports, including those lost due to partially complete rpc calls.

        New streams will flush notifications for all un-released exports, prior to seeing any new or updated exports
        for all live exports. After the refresh of existing state, subscribers will receive notifications of new and
        updated exports. An export id of zero will be sent to indicate all pre-existing exports have been sent.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def TerminationNotification(self, request, context):
        """
        Receive a best-effort message on-exit indicating why this server is exiting. Reception of this message cannot be
        guaranteed.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_SessionServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'NewSession': grpc.unary_unary_rpc_method_handler(
                    servicer.NewSession,
                    request_deserializer=deephaven__core_dot_proto_dot_session__pb2.HandshakeRequest.FromString,
                    response_serializer=deephaven__core_dot_proto_dot_session__pb2.HandshakeResponse.SerializeToString,
            ),
            'RefreshSessionToken': grpc.unary_unary_rpc_method_handler(
                    servicer.RefreshSessionToken,
                    request_deserializer=deephaven__core_dot_proto_dot_session__pb2.HandshakeRequest.FromString,
                    response_serializer=deephaven__core_dot_proto_dot_session__pb2.HandshakeResponse.SerializeToString,
            ),
            'CloseSession': grpc.unary_unary_rpc_method_handler(
                    servicer.CloseSession,
                    request_deserializer=deephaven__core_dot_proto_dot_session__pb2.HandshakeRequest.FromString,
                    response_serializer=deephaven__core_dot_proto_dot_session__pb2.CloseSessionResponse.SerializeToString,
            ),
            'Release': grpc.unary_unary_rpc_method_handler(
                    servicer.Release,
                    request_deserializer=deephaven__core_dot_proto_dot_session__pb2.ReleaseRequest.FromString,
                    response_serializer=deephaven__core_dot_proto_dot_session__pb2.ReleaseResponse.SerializeToString,
            ),
            'ExportFromTicket': grpc.unary_unary_rpc_method_handler(
                    servicer.ExportFromTicket,
                    request_deserializer=deephaven__core_dot_proto_dot_session__pb2.ExportRequest.FromString,
                    response_serializer=deephaven__core_dot_proto_dot_session__pb2.ExportResponse.SerializeToString,
            ),
            'PublishFromTicket': grpc.unary_unary_rpc_method_handler(
                    servicer.PublishFromTicket,
                    request_deserializer=deephaven__core_dot_proto_dot_session__pb2.PublishRequest.FromString,
                    response_serializer=deephaven__core_dot_proto_dot_session__pb2.PublishResponse.SerializeToString,
            ),
            'ExportNotifications': grpc.unary_stream_rpc_method_handler(
                    servicer.ExportNotifications,
                    request_deserializer=deephaven__core_dot_proto_dot_session__pb2.ExportNotificationRequest.FromString,
                    response_serializer=deephaven__core_dot_proto_dot_session__pb2.ExportNotification.SerializeToString,
            ),
            'TerminationNotification': grpc.unary_unary_rpc_method_handler(
                    servicer.TerminationNotification,
                    request_deserializer=deephaven__core_dot_proto_dot_session__pb2.TerminationNotificationRequest.FromString,
                    response_serializer=deephaven__core_dot_proto_dot_session__pb2.TerminationNotificationResponse.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'io.deephaven.proto.backplane.grpc.SessionService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class SessionService(object):
    """
    User supplied Flight.Ticket(s) should begin with 'e' byte followed by an signed little-endian int. The client is only
    allowed to use the positive exportId key-space (client generated exportIds should be greater than 0). The client is
    encouraged to use a packed ranges of ids as this yields the smallest footprint server side for long running sessions.

    The client is responsible for releasing all Flight.Tickets that they create or that were created for them via a gRPC
    call. The documentation for the gRPC call will indicate that the exports must be released. Exports that need to be
    released will always be communicated over the session's ExportNotification stream.

    When a session ends, either explicitly or due to timeout, all exported objects in that session are released
    automatically.

    Some parts of the API return a Flight.Ticket that does not need to be released. It is not an error to attempt to
    release them.
    """

    @staticmethod
    def NewSession(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/io.deephaven.proto.backplane.grpc.SessionService/NewSession',
            deephaven__core_dot_proto_dot_session__pb2.HandshakeRequest.SerializeToString,
            deephaven__core_dot_proto_dot_session__pb2.HandshakeResponse.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def RefreshSessionToken(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/io.deephaven.proto.backplane.grpc.SessionService/RefreshSessionToken',
            deephaven__core_dot_proto_dot_session__pb2.HandshakeRequest.SerializeToString,
            deephaven__core_dot_proto_dot_session__pb2.HandshakeResponse.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def CloseSession(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/io.deephaven.proto.backplane.grpc.SessionService/CloseSession',
            deephaven__core_dot_proto_dot_session__pb2.HandshakeRequest.SerializeToString,
            deephaven__core_dot_proto_dot_session__pb2.CloseSessionResponse.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def Release(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/io.deephaven.proto.backplane.grpc.SessionService/Release',
            deephaven__core_dot_proto_dot_session__pb2.ReleaseRequest.SerializeToString,
            deephaven__core_dot_proto_dot_session__pb2.ReleaseResponse.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def ExportFromTicket(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/io.deephaven.proto.backplane.grpc.SessionService/ExportFromTicket',
            deephaven__core_dot_proto_dot_session__pb2.ExportRequest.SerializeToString,
            deephaven__core_dot_proto_dot_session__pb2.ExportResponse.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def PublishFromTicket(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/io.deephaven.proto.backplane.grpc.SessionService/PublishFromTicket',
            deephaven__core_dot_proto_dot_session__pb2.PublishRequest.SerializeToString,
            deephaven__core_dot_proto_dot_session__pb2.PublishResponse.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def ExportNotifications(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_stream(
            request,
            target,
            '/io.deephaven.proto.backplane.grpc.SessionService/ExportNotifications',
            deephaven__core_dot_proto_dot_session__pb2.ExportNotificationRequest.SerializeToString,
            deephaven__core_dot_proto_dot_session__pb2.ExportNotification.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def TerminationNotification(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/io.deephaven.proto.backplane.grpc.SessionService/TerminationNotification',
            deephaven__core_dot_proto_dot_session__pb2.TerminationNotificationRequest.SerializeToString,
            deephaven__core_dot_proto_dot_session__pb2.TerminationNotificationResponse.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)
