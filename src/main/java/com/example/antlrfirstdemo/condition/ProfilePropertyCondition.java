package com.example.antlrfirstdemo.condition;

import java.util.Map;

public class ProfilePropertyCondition implements Condition {

    public static final String TYPE = "profilePropertyCondition";
    protected Map<String, Object> parameterValues;

    public ProfilePropertyCondition() {
    }

    public ProfilePropertyCondition(Map<String, Object> parameterValues) {
        this.parameterValues = parameterValues;
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
