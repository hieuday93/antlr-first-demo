package com.example.antlrfirstdemo.condition.unomi;

import java.util.Map;

public abstract class UnomiProfilePropertyCondition extends UnomiCondition {

    public static final String TYPE = "profilePropertyCondition";
    protected Map<String, Object> parameterValues;

    protected UnomiProfilePropertyCondition(String type, Map<String, Object> parameterValues) {
        super(type, parameterValues);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Map<String, Object> getParameterValues() {
        return parameterValues;
    }
}
