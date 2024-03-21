package org.example.learning.regex;

import org.springframework.util.AntPathMatcher;

import java.util.Map;

public class AntPathMatcherMain {
    public static void main(String[] args) {
        AntPathMatcher matcher = new AntPathMatcher();
        Map<String, String> map = matcher.extractUriTemplateVariables("/users/{userId}/repos/{repoId}", "/users/1/repos/java");
        System.out.println(map);
    }
}
