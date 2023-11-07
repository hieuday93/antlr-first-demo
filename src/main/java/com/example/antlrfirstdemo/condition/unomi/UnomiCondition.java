package com.example.antlrfirstdemo.condition.unomi;

import java.util.Map;

public abstract class UnomiCondition {

    protected UnomiCondition(String type, Map<String, Object> parameterValues) {
        if(type == null || parameterValues == null) {
            throw new IllegalArgumentException("type and parameterValues must not be null");
        }
        if(!isValidConstructorParameter(type, parameterValues)) {
            throw new IllegalArgumentException("Invalid constructor parameter");
        }
        initCondition(type, parameterValues);
    }

    protected abstract boolean isValidConstructorParameter(String type, Map<String, Object> parameterValues);

    protected abstract void initCondition(String type, Map<String, Object> parameterValues);

    public abstract String getType();

    public abstract Map<String, Object> getParameterValues();


}
