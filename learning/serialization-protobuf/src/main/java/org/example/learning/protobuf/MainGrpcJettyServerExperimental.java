package org.example.learning.protobuf;

import io.grpc.servlet.jakarta.GrpcServlet;
import io.grpc.stub.StreamObserver;
import jakarta.servlet.Servlet;
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.learning.protobuf.grpc.Message;
import org.example.learning.protobuf.grpc.MessageServiceGrpc;

import java.util.List;

public class MainGrpcJettyServerExperimental {
    public static void main(String[] args) throws Exception {
        final Server server = new Server();

        HttpConfiguration httpConfig = new HttpConfiguration();
        HTTP2CServerConnectionFactory h2c = new HTTP2CServerConnectionFactory(httpConfig);

        ServerConnector connector = new ServerConnector(server, h2c);
        connector.setPort(8080);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        // experimental
        final Servlet grpcServlet = new GrpcServlet(List.of(new MessageSvcImpl()));
        ServletHolder servletHolder = new ServletHolder(grpcServlet);
        servletHolder.setAsyncSupported(true);

        context.addServlet(servletHolder, "/MessageService/Rpc");
        server.setHandler(context);

        server.start();
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
