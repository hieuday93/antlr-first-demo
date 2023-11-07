package com.example.antlrfirstdemo.condition;

import com.example.antlrfirstdemo.util.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProfilePropertyCondition implements Condition {

    public static final String TYPE = "profilePropertyCondition";
    private Map<String, Object> parameterValues;
    private String type = TYPE;

    public ProfilePropertyCondition() {
    }

    public ProfilePropertyCondition(Map<String, Object> parameterValues) {
        this.parameterValues = parameterValues;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return parameterValues;
    }

    @Override
    public String toHumanReadableStatement() {
        if(parameterValues == null || parameterValues.isEmpty()
                || !parameterValues.containsKey("propertyName")
                || !parameterValues.containsKey("comparisonOperator")) {
            return "";
        }
        String propertyName = (String) parameterValues.get("propertyName");
        String comparisonOperator = (String) parameterValues.get("comparisonOperator");
        boolean isBetween = "between".equals(comparisonOperator);
        String value = "";
        if(parameterValues.containsKey("propertyValueDouble")) {
            value = objectToString(parameterValues.get("propertyValueDouble"));
        } else if (parameterValues.containsKey("propertyValueInteger")) {
            value = objectToString(parameterValues.get("propertyValueInteger"));
        } else if (parameterValues.containsKey("propertyValueDate")) {
            value = objectToString(parameterValues.get("propertyValueDate"));
        } else if(parameterValues.containsKey("propertyValue")) {
            value = objectToString(parameterValues.get("propertyValue"));
        } else if(parameterValues.containsKey("propertyValuesDouble")) {
            value = listToString(parameterValues.get("propertyValuesDouble"), isBetween);
        } else if (parameterValues.containsKey("propertyValuesInteger")) {
            value = listToString(parameterValues.get("propertyValuesInteger"), isBetween);
        } else if (parameterValues.containsKey("propertyValuesDate")) {
            value = listToString(parameterValues.get("propertyValuesDate"), isBetween);
        } else if(parameterValues.containsKey("propertyValues")) {
            value = listToString(parameterValues.get("propertyValues"), isBetween);
        }
        value = value.isBlank() ? "" : " " + value;
        return propertyName + " " + comparisonOperator + value;

    }

    private String objectToString(Object value) {
        if(value instanceof Long) {
            return Long.toString((long) value);
        } else if (value instanceof Double) {
            return Double.toString((double) value);
        } else if(value instanceof String) {
            String ret = (String) value;
            return StringUtil.wrap(ret, "\"");
        }
        throw new RuntimeException("Cannot convert " + value + " to valid string");
    }

    private String listToString(Object value, boolean isBetween) {
        if(value instanceof List) {
            List<?> list = (List<?>) value;
            if(isBetween && list.size() == 2) {
                String first = this.objectToString(list.get(0));
                String second = this.objectToString(list.get(1));
                return StringUtil.join(" ", first, "AND", second);
            } else {
                return list.stream().map(this::objectToString).collect(Collectors.joining(", ", "[", "]"));
            }
        }
        throw new RuntimeException("Cannot convert " + value + " to valid list");
    }

}
