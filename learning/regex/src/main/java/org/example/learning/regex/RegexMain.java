package org.example.learning.regex;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMain {
    public static void main(String[] args) {
        // supplied by user
        Pattern pattern = Pattern.compile("^/users/(?<userId>\\d+)/repos/(?<repoId>\\d+)$");
        // --------------------------------------------------------------------------------------
        String path = "/users/11/repos/22";

        Matcher matcher = pattern.matcher(path);
        if (!matcher.matches()) {
            System.out.println("fail");
            return;
        }

        int groupCount = matcher.groupCount();
        for (int i = 1; i <= groupCount; i++) {
            System.out.println(matcher.group(i));
        }

        Map<String, Integer> names = matcher.namedGroups();
        for (Map.Entry<String, Integer> nameEntry : names.entrySet()) {
            String group = nameEntry.getKey();
            String value = matcher.group(nameEntry.getValue());
            System.out.println(group);
            System.out.println(value);
        }
    }
}
