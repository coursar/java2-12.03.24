package org.example.framework.handler;

import lombok.RequiredArgsConstructor;
import org.example.server.Response;

import java.io.OutputStream;

@RequiredArgsConstructor
public class FrameworkResponse {
    private final Response response;

    public OutputStream getOut() {
        return this.response.getOut();
    }
}
