package com.example.antlrfirstdemo.condition;

import com.example.antlrfirstdemo.util.ConditionDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Map;

@JsonDeserialize(using = ConditionDeserialize.class)
public interface Condition {

    String getType();
    Map<String, Object> getParameterValues();
    String toHumanReadableStatement();

}
