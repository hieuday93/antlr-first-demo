package com.example.antlrfirstdemo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {

    private JsonUtil() {
        throw new IllegalStateException("cannot initiate an util class");
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJSonString(Object input) {
        try {
            return MAPPER.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

}
