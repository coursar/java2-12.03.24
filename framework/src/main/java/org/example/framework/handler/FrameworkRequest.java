package org.example.framework.handler;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import lombok.RequiredArgsConstructor;
import org.example.server.Request;

import java.net.Socket;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class FrameworkRequest {
    // delegate
    private final Request request; // copy from request all methods
    // IDEA -> alt + insert -> delegate methods
    private final Map<String, String> pathParams;

    public Socket getSocket() {
        return request.getSocket();
    }

    public String getMethod() {
        return this.request.getMethod();
    }

    public String getPath() {
        return this.request.getPath();
    }

    public ListMultimap<String, String> getQuery() {
        return this.request.getQuery();
    }

    public Multimap<String, String> getHeaders() {
        return this.request.getHeaders();
    }

    public byte[] getBody() {
        return this.request.getBody();
    }

    void addPathParams(Map<String, String> params) {
        this.pathParams.putAll(params);
    }

    public Optional<String> getPathParam(String name) {
        // TODO:
        //  old api: null
        //  ~: default value ""
        //  -> new api: Optional
        return Optional.ofNullable(this.pathParams.get(name));
    }

    public Map<String, Object> getAttributes() {
        return this.request.getAttributes();
    }
}
