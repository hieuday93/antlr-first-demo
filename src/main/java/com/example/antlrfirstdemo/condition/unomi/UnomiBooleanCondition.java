package com.example.antlrfirstdemo.condition.unomi;

import com.example.antlrfirstdemo.condition.BooleanCondition;

import java.util.*;
import java.util.stream.Collectors;

public class UnomiBooleanCondition extends UnomiCondition {

    public static final String TYPE = "booleanCondition";

    private Operator operator;
    private List<UnomiCondition> subConditions;

    protected UnomiBooleanCondition(String type, Map<String, Object> parameterValues) {
        super(type, parameterValues);
    }


    @Override
    protected boolean isValidConstructorParameter(String type, Map<String, Object> parameterValues) {
        if(!TYPE.equals(type)) {
            return false;
        }
        if(parameterValues == null || parameterValues.isEmpty()) {
            return false;
        }
        if(!(parameterValues.get("operator") instanceof String)
                || Operator.ofCode((String) parameterValues.get("operator")).isEmpty()) {
            return false;
        }
        if(!(parameterValues.get("subConditions") instanceof Collection)
                || ((Collection) parameterValues.get("subConditions")).isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    protected void initCondition(String type, Map<String, Object> parameterValues) {
        operator = Operator.ofCode((String) parameterValues.get("operator")).get();
        subConditions = new ArrayList<>();
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Map<String, Object> getParameterValues() {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("operator", operator.getCode());
        params.put("subConditions", subConditions);
        return params;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public List<UnomiCondition> getSubConditions() {
        return subConditions;
    }

    public void setSubConditions(List<UnomiCondition> subConditions) {
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

        private static final Map<String, UnomiBooleanCondition.Operator> CODES = Arrays.stream(values())
                .collect(Collectors.toMap(UnomiBooleanCondition.Operator::getCode, o -> o));

        public static Optional<UnomiBooleanCondition.Operator> ofCode(String code) {
            return Optional.ofNullable(CODES.get(code));
        }
    }
}
