package org.examplesolid.application.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtility {

    public static List<String> stringToList(String string, String... delimiter) {
        if (string == null || string.isBlank() || string.isEmpty()) return new ArrayList<>();
        return Arrays.stream(string.split(delimiter.length >= 1 ? delimiter[0] : "-")).collect(Collectors.toList());
    }

    public static String listToString(List<String> listStrings, String... delimiter) {
        if (listStrings == null || listStrings.isEmpty()) return "";
        return String.join(delimiter.length >= 1 ? delimiter[0] : "-", listStrings);
    }

    public static String joinStrings(String baseString, String attachString, String... delimiter) {
        return baseString.concat(delimiter.length == 1 ? delimiter[0] : "-").concat(attachString);
    }
}
