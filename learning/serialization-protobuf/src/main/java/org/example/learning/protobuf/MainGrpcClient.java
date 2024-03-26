package org.example.learning.protobuf;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import org.example.learning.protobuf.grpc.Message;
import org.example.learning.protobuf.grpc.MessageServiceGrpc;

public class MainGrpcClient {
    public static void main(String[] args) {
        final ManagedChannel channel = Grpc.newChannelBuilder("localhost:8080", InsecureChannelCredentials.create()).build();
        final MessageServiceGrpc.MessageServiceBlockingStub stub = MessageServiceGrpc.newBlockingStub(channel);
        final Message request = Message.newBuilder().setId(1L).setContent("request").build();
        final Message response = stub.rpc(request);
        System.out.println("response = " + response);
    }
}
