# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc
import warnings

from deephaven_core.proto import application_pb2 as deephaven__core_dot_proto_dot_application__pb2

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
        + f' but the generated code in deephaven_core/proto/application_pb2_grpc.py depends on'
        + f' grpcio>={GRPC_GENERATED_VERSION}.'
        + f' Please upgrade your grpc module to grpcio>={GRPC_GENERATED_VERSION}'
        + f' or downgrade your generated code using grpcio-tools<={GRPC_VERSION}.'
        + f' This warning will become an error in {EXPECTED_ERROR_RELEASE},'
        + f' scheduled for release on {SCHEDULED_RELEASE_DATE}.',
        RuntimeWarning
    )


class ApplicationServiceStub(object):
    """
    Allows clients to list fields that are accessible to them.
    """

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.ListFields = channel.unary_stream(
                '/io.deephaven.proto.backplane.grpc.ApplicationService/ListFields',
                request_serializer=deephaven__core_dot_proto_dot_application__pb2.ListFieldsRequest.SerializeToString,
                response_deserializer=deephaven__core_dot_proto_dot_application__pb2.FieldsChangeUpdate.FromString,
                _registered_method=True)


class ApplicationServiceServicer(object):
    """
    Allows clients to list fields that are accessible to them.
    """

    def ListFields(self, request, context):
        """
        Request the list of the fields exposed via the worker.

        - The first received message contains all fields that are currently available
        on the worker. None of these fields will be RemovedFields.
        - Subsequent messages modify the existing state. Fields are identified by
        their ticket and may be replaced or removed.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_ApplicationServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'ListFields': grpc.unary_stream_rpc_method_handler(
                    servicer.ListFields,
                    request_deserializer=deephaven__core_dot_proto_dot_application__pb2.ListFieldsRequest.FromString,
                    response_serializer=deephaven__core_dot_proto_dot_application__pb2.FieldsChangeUpdate.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'io.deephaven.proto.backplane.grpc.ApplicationService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class ApplicationService(object):
    """
    Allows clients to list fields that are accessible to them.
    """

    @staticmethod
    def ListFields(request,
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
            '/io.deephaven.proto.backplane.grpc.ApplicationService/ListFields',
            deephaven__core_dot_proto_dot_application__pb2.ListFieldsRequest.SerializeToString,
            deephaven__core_dot_proto_dot_application__pb2.FieldsChangeUpdate.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)
