package org.example.server;

import lombok.Builder;
import lombok.Getter;

import java.io.OutputStream;

@Builder
@Getter
public class Response {
    private final OutputStream out;
}
