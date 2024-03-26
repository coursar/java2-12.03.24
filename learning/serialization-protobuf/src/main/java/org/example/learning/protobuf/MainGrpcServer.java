package org.example.learning.protobuf;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import org.example.learning.protobuf.grpc.Message;
import org.example.learning.protobuf.grpc.MessageServiceGrpc;

import java.io.IOException;

public class MainGrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        final Server server = Grpc.newServerBuilderForPort(8080, InsecureServerCredentials.create())
                .addService(new MessageSvcImpl())
                .build()
                .start();
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        server.awaitTermination();
    }

    public static class MessageSvcImpl extends MessageServiceGrpc.MessageServiceImplBase {
        @Override
        public void rpc(final Message request, final StreamObserver<Message> responseObserver) {
            final Message response = request.toBuilder().setContent("resp").build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
