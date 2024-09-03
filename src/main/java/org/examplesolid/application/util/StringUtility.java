package org.examplesolid.application.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtility {


    public static List<String> stringToList(String string) {
        if (string == null || string.isBlank() || string.isEmpty()) return new ArrayList<>();
        return Arrays.stream(string.split("-")).collect(Collectors.toList());
    }

    public static List<String> stringToList(String string, String delimiter) {
        if (string == null || string.isBlank() || string.isEmpty()) return new ArrayList<>();
        return Arrays.stream(string.split(delimiter)).collect(Collectors.toList());
    }

    public static String listToString(List<String> listStrings) {
        if (listStrings == null || listStrings.isEmpty()) return "";
        return String.join("-", listStrings);
    }

    public static String listToString(List<String> listStrings, String delimiter) {
        if (listStrings == null || listStrings.isEmpty()) return "";
        return String.join(delimiter, listStrings);
    }

    public static String joinStrings(String baseString, String attachString) {
        return baseString.concat("-").concat(attachString);
    }

    public static String joinStrings(String baseString, String attachString, String delimiter) {
        return baseString.concat(delimiter).concat(attachString);
    }
}
