package com.example.antlrfirstdemo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public final class JsonUtil {

    private JsonUtil() {
        throw new IllegalStateException("cannot initiate an util class");
    }

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
    }

    public static String toJSonString(Object input) {
        try {
            return MAPPER.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static <T> T toObject(File file, Class<T> clazz) {
        try {
            return MAPPER.readValue(file, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toObject(JsonNode node, Class<T> clazz) {
        try {
            return MAPPER.treeToValue(node, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
