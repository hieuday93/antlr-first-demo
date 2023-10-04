package com.example.antlrfirstdemo.condition;

import java.util.Map;

public interface Condition {

    String getType();
    Map<String, Object> getParameterValues();
    String toHumanReadableStatement();

}
