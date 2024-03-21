package org.example.framework.handler;

import org.example.server.Request;
import org.example.server.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PathWithParamsHandler implements FrameworkHandler {

    public static final String VARIABLE_PATTERN = "\\{([^/]+)\\}";
    public static final String VARIABLE_PATTERN_REPLACEMENT = "(?<$1>[^/]+)";

    public static final String VARIABLE_PATTERN_FINAL = "^%s$";

    private final RegexPathWithParamsHandler delegate;

    public PathWithParamsHandler(Map<String, FrameworkHandler> routes) {
        Map<Pattern, FrameworkHandler> replaced = new HashMap<>(1 + (int)(routes.size() / 0.75f), 0.75f);
        for (Map.Entry<String, FrameworkHandler> entry : routes.entrySet()) {
            // bad code
            Pattern key = Pattern.compile(VARIABLE_PATTERN_FINAL.formatted(entry.getKey().replaceAll(VARIABLE_PATTERN, VARIABLE_PATTERN_REPLACEMENT)));
            FrameworkHandler value = entry.getValue();
            replaced.put(key, value);
        }
        this.delegate = new RegexPathWithParamsHandler(replaced);
    }

    @Override
    public void handle(FrameworkRequest request, FrameworkResponse response) throws Exception {
        this.delegate.handle(request, response);
    }
}
