package org.example.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.security.principal.AnonymousPrincipal;
import org.example.server.Handler;
import org.example.server.Request;
import org.example.server.Response;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import java.net.Socket;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class X509AuthHandler implements Handler {
    public static final String X509_PRINCIPAL_ATTRIBUTE = "org.example.security.x509principal";
    private final Handler handler;

    @Override
    public void handle(Request request, Response response) throws Exception {
        Socket socket = request.getSocket();
        if (!(socket instanceof SSLSocket)) {
            throw new IllegalArgumentException("Not SSLSocket");
        }

        try {
            Principal principal = ((SSLSocket) socket).getSession().getPeerPrincipal();
            // TODO: debug/trace
            log.trace("principal: {}", principal);
            request.getAttributes().put(X509_PRINCIPAL_ATTRIBUTE, Optional.of(principal));
        } catch (SSLPeerUnverifiedException e) {
            request.getAttributes().put(X509_PRINCIPAL_ATTRIBUTE, Optional.empty());
            log.trace("anonymous");
        }
        // 1. null
        // 2. default value -> Anonymous
        // 3. Optional <- selected

        this.handler.handle(request, response);
    }
}
