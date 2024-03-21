package org.example.framework.handler;

import org.example.server.Handler;
import org.example.server.Request;
import org.example.server.Response;

import java.util.HashMap;

public interface FrameworkHandler extends Handler {
    void handle(FrameworkRequest request, FrameworkResponse response) throws Exception;

    default void handle(Request request, Response response) throws Exception {
        FrameworkRequest frameworkRequest = new FrameworkRequest(request, new HashMap<>());
        FrameworkResponse frameworkResponse = new FrameworkResponse(response);
        handle(frameworkRequest, frameworkResponse);
    }
}
