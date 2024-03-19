package org.example.server;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Request {
    private final String method; // TODO:
    private final String path; // TODO:
    private final ListMultimap<String, String> query; // TODO:
    private final Multimap<String, String> headers;
    private final byte[] body;
}
