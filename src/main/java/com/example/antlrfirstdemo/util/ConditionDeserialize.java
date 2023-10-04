package com.example.antlrfirstdemo.util;

import com.example.antlrfirstdemo.condition.BooleanCondition;
import com.example.antlrfirstdemo.condition.Condition;
import com.example.antlrfirstdemo.condition.ProfilePropertyCondition;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConditionDeserialize extends StdDeserializer<Condition> {

    public ConditionDeserialize() {
        this(null);
    }

    protected ConditionDeserialize(Class<?> vc) {
        super(vc);
    }

    @Override
    public Condition deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec codec = jsonParser.getCodec();
        if(codec == null) {
            return null;
        }
        JsonNode treeNode = codec.readTree(jsonParser);
        String type = treeNode.get("type").asText();
        if(BooleanCondition.TYPE.equals(type)) {
            String operator = treeNode.get("parameterValues").get("operator").asText();
            BooleanCondition.Operator op = BooleanCondition.Operator.ofCode(operator).orElseThrow();
            List<Condition> subConditions = new ArrayList<>();
            treeNode.get("parameterValues").get("subConditions").elements().forEachRemaining(subCondition -> {
                subConditions.add(JsonUtil.toObject(subCondition, Condition.class));
            });
            return new BooleanCondition(op, subConditions);
        }
        if(ProfilePropertyCondition.TYPE.equals(type)) {
            return extractProfilePropertyCondition(treeNode);
        }
        return null;
    }

    private ProfilePropertyCondition extractProfilePropertyCondition(JsonNode treeNode) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();
        treeNode.get("parameterValues")
                .properties()
                .forEach(entry -> parameterValues.put(
                        entry.getKey(),
                        extractJsonNodeValue(entry.getValue()))
                );
        return new ProfilePropertyCondition(parameterValues);
    }

    private Object extractJsonNodeValue(JsonNode node) {
        if(node == null) {
            return null;
        }
        switch (node.getNodeType()) {
            case BOOLEAN:
                return node.asBoolean();
            case NUMBER:
                if(node.isInt() || node.isLong()) {
                    return node.asLong();
                }
                return node.asDouble();
            case ARRAY:
                List<Object> list = new ArrayList<>();
                node.elements().forEachRemaining(n -> list.add(extractJsonNodeValue(n)));
                return list;
            default:
                return node.asText();
        }
    }
}
