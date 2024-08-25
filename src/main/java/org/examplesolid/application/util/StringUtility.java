package org.examplesolid.application.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.examplesolid.application.util.Constants.SEPARATOR_CONCAT_FACT;

public class StringUtility {

    public static List<String> stringToList(String funFactsInString) {
        if (funFactsInString == null || funFactsInString.isBlank() || funFactsInString.isEmpty()) return Collections.emptyList();
        return Arrays.stream(funFactsInString.split(SEPARATOR_CONCAT_FACT)).toList();
    }

    public static String listToString(List<String> funFactsInList) {
        if (funFactsInList == null || funFactsInList.isEmpty()) return "";
        return String.join(SEPARATOR_CONCAT_FACT, funFactsInList);
    }

    public static String joinWithSeparator(String baseString, String attachString) {
        return baseString.concat(SEPARATOR_CONCAT_FACT).concat(attachString);
    }
}
