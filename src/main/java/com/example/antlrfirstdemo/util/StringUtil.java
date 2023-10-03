package com.example.antlrfirstdemo.util;

import org.apache.commons.lang3.StringUtils;

public final class StringUtil {

    private StringUtil() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }

    public static String stripSurroundedDoubleQuotes(String input) {

        return StringUtils.unwrap(input, "\"");
    }

}
