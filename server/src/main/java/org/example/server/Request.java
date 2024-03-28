package org.example.server;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import lombok.Builder;
import lombok.Getter;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
public class Request {
    private final Socket socket;
    private final String method; // TODO:
    private final String path; // TODO:
    private final ListMultimap<String, String> query; // TODO:
    private final Multimap<String, String> headers;
    private final byte[] body;
    private final Map<String, Object> attributes = new HashMap<>(256);
}
