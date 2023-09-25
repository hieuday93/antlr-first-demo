package com.example.antlrfirstdemo.condition;

import java.util.*;
import java.util.stream.Collectors;

public class BooleanCondition implements Condition {

    public BooleanCondition() {
    }

    public BooleanCondition(Operator operator, List<Condition> subConditions) {
        this.operator = operator;
        this.subConditions = subConditions;
    }

    public enum Operator {
        AND("and"), OR("or");

        private final String code;

        Operator(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        private static final Map<String, Operator> CODES = Arrays.stream(values())
                .collect(Collectors.toMap(Operator::getCode, o -> o));

        public static Optional<Operator> ofCode(String code) {
            return Optional.ofNullable(CODES.get(code));
        }
    }

    public static final String TYPE = "booleanCondition";

    private Operator operator;
    private List<Condition> subConditions;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Map<String, Object> getParameterValues() {
        Map<String, Object> parameterValues = new LinkedHashMap<>();
        parameterValues.put("operator", operator.getCode());
        List<Map<String, Object>> subConditionsParams = subConditions.stream()
                .map(Condition::getParameterValues)
                .collect(Collectors.toList());
        parameterValues.put("subConditions", subConditionsParams);
        return parameterValues;
    }

}
