package com.example.antlrfirstdemo.tour;

import com.example.antlrfirstdemo.cdp.ConditionBaseVisitor;
import com.example.antlrfirstdemo.cdp.ConditionParser;
import com.example.antlrfirstdemo.condition.BooleanCondition;
import com.example.antlrfirstdemo.condition.Condition;
import com.example.antlrfirstdemo.condition.ProfilePropertyCondition;
import com.example.antlrfirstdemo.util.StringUtil;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConditionVisitor extends ConditionBaseVisitor<Condition> {

    public static final String PROPERTY_NAME = "propertyName";
    public static final String COMPARISON_OPERATOR = "comparisonOperator";
    public static final String PROPERTY_VALUE = "propertyValue";
    public static final String PROPERTY_VALUE_DATE = "propertyValueDate";
    public static final String PROPERTY_VALUES_DATE = "propertyValuesDate";
    private Condition mainCondition;

    @Override
    public Condition visitGroupCondition(ConditionParser.GroupConditionContext ctx) {
        Condition condition = visit(ctx.condition());
        if(this.mainCondition == null) {
            this.mainCondition = condition;
        }
        return condition;
    }

    @Override
    public Condition visitRelationCondition(ConditionParser.RelationConditionContext ctx) {
        BooleanCondition booleanCondition;
        if(ctx.op.getType() == ConditionParser.AND) {
            booleanCondition = new BooleanCondition(BooleanCondition.Operator.AND);
        } else {
            booleanCondition = new BooleanCondition(BooleanCondition.Operator.OR);
        }
        if(this.mainCondition == null) {
            this.mainCondition = booleanCondition;
        }
        Condition subCondition1 = visit(ctx.condition(0));
        Condition subCondition2 = visit(ctx.condition(1));
        booleanCondition.addSubConditions(subCondition1, subCondition2);
        return booleanCondition;
    }

    @Override
    public Condition visitSingleCondition(ConditionParser.SingleConditionContext ctx) {
        Condition condition = visit(ctx.propertyCondition());
        if(this.mainCondition == null) {
            this.mainCondition = condition;
        }
        return condition;
    }

    @Override
    public Condition visitPropertyConditionEquals(ConditionParser.PropertyConditionEqualsContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "equals");

        extractAnyTypeValue(parameterValues, ctx.anyTypeValue());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionNotEquals(ConditionParser.PropertyConditionNotEqualsContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "notEquals");

        extractAnyTypeValue(parameterValues, ctx.anyTypeValue());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionGreaterThan(ConditionParser.PropertyConditionGreaterThanContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "greaterThan");

        extractNonPureTextValue(parameterValues, ctx.nonPureTextValue());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionGreaterThanOrEqualTo(ConditionParser.PropertyConditionGreaterThanOrEqualToContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "greaterThanOrEqualTo");

        extractNonPureTextValue(parameterValues, ctx.nonPureTextValue());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionLessThan(ConditionParser.PropertyConditionLessThanContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "lessThan");

        extractNonPureTextValue(parameterValues, ctx.nonPureTextValue());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionLessThanOrEqualTo(ConditionParser.PropertyConditionLessThanOrEqualToContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "lessThanOrEqualTo");

        extractNonPureTextValue(parameterValues, ctx.nonPureTextValue());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionBetween(ConditionParser.PropertyConditionBetweenContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "between");

        extractAnyTypeRange(parameterValues, ctx.anyTypeRange());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionExists(ConditionParser.PropertyConditionExistsContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "exists");

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionMissing(ConditionParser.PropertyConditionMissingContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "missing");

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionContains(ConditionParser.PropertyConditionContainsContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "contains");
        parameterValues.put(PROPERTY_VALUE, ctx.STRING().getText());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionNotContains(ConditionParser.PropertyConditionNotContainsContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "notContains");
        parameterValues.put(PROPERTY_VALUE, ctx.STRING().getText());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionStartsWith(ConditionParser.PropertyConditionStartsWithContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "startsWith");
        parameterValues.put(PROPERTY_VALUE, ctx.STRING().getText());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionEndsWith(ConditionParser.PropertyConditionEndsWithContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "endsWith");
        parameterValues.put(PROPERTY_VALUE, ctx.STRING().getText());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionMatchesRegex(ConditionParser.PropertyConditionMatchesRegexContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "matchesRegex");
        parameterValues.put(PROPERTY_VALUE, ctx.STRING().getText());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionIn(ConditionParser.PropertyConditionInContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "in");

        extractAnyTypeValues(parameterValues, ctx.anyTypeValues());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionNotIn(ConditionParser.PropertyConditionNotInContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "notIn");

        extractAnyTypeValues(parameterValues, ctx.anyTypeValues());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionAll(ConditionParser.PropertyConditionAllContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "all");

        extractAnyTypeValues(parameterValues, ctx.anyTypeValues());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionInContains(ConditionParser.PropertyConditionInContainsContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "inContains");

        extractStringList(parameterValues, ctx.stringList());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionHasSomeOf(ConditionParser.PropertyConditionHasSomeOfContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "hasSomeOf");

        extractAnyTypeValues(parameterValues, ctx.anyTypeValues());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionHasNoneOf(ConditionParser.PropertyConditionHasNoneOfContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "hasNoneOf");

        extractAnyTypeValues(parameterValues, ctx.anyTypeValues());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionIsDay(ConditionParser.PropertyConditionIsDayContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "isDay");
        parameterValues.put(PROPERTY_VALUE_DATE, ctx.anyDateValue().getText());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionIsNotDay(ConditionParser.PropertyConditionIsNotDayContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put(PROPERTY_NAME, ctx.propertyPath().getText());
        parameterValues.put(COMPARISON_OPERATOR, "isNotDay");
        parameterValues.put(PROPERTY_VALUE_DATE, ctx.anyDateValue().getText());

        return new ProfilePropertyCondition(parameterValues);
    }

    public Condition getMainCondition() {
        return mainCondition;
    }

    public void setMainCondition(Condition mainCondition) {
        this.mainCondition = mainCondition;
    }

    private void extractAnyTypeValue(Map<String, Object> parameterValues, ConditionParser.AnyTypeValueContext anyTypeValueContext) {
        if(anyTypeValueContext.DECIMAL() != null) {
            extractDECIMAL(parameterValues, anyTypeValueContext.DECIMAL());
        } else if (anyTypeValueContext.integer() != null) {
            extractInteger(parameterValues, anyTypeValueContext.integer());
        } else if (anyTypeValueContext.DATE() != null) {
            extractDATE(parameterValues, anyTypeValueContext.DATE());
        } else if (anyTypeValueContext.STRING() != null) {
            extractSTRING(parameterValues, anyTypeValueContext.STRING());
        } else {
            String text = StringUtil.stripSurroundedDoubleQuotes(anyTypeValueContext.getText());
            parameterValues.put(PROPERTY_VALUE, text);
        }
    }

    private void extractNonPureTextValue(Map<String, Object> parameterValues, ConditionParser.NonPureTextValueContext nonPureTextValueContext) {
        if(nonPureTextValueContext.DECIMAL() != null) {
            extractDECIMAL(parameterValues, nonPureTextValueContext.DECIMAL());
        } else if (nonPureTextValueContext.integer() != null) {
            extractInteger(parameterValues, nonPureTextValueContext.integer());
        } else if (nonPureTextValueContext.DATE() != null) {
            extractDATE(parameterValues, nonPureTextValueContext.DATE());
        } else {
            String text = StringUtil.stripSurroundedDoubleQuotes(nonPureTextValueContext.getText());
            parameterValues.put(PROPERTY_VALUE, text);
        }
    }

    private void extractAnyTypeRange(Map<String, Object> parameterValues, ConditionParser.AnyTypeRangeContext anyTypeRangeContext) {
        if(anyTypeRangeContext.decimalRange() != null) {
            extractDecimalRange(parameterValues, anyTypeRangeContext.decimalRange());
        } else if (anyTypeRangeContext.integerRange() != null) {
            extractIntegerRange(parameterValues, anyTypeRangeContext.integerRange());
        } else if (anyTypeRangeContext.dateRange() != null) {
            extractDateRange(parameterValues, anyTypeRangeContext.dateRange());
        } else {
            throw new RuntimeException("Cannot parse anyTypeRange: " + anyTypeRangeContext.getText());
        }
    }

    private void extractAnyTypeValues(Map<String, Object> parameterValues, ConditionParser.AnyTypeValuesContext anyTypeValuesContext) {
        if(anyTypeValuesContext.DECIMALLIST() != null) {
            extractDECIMALLIST(parameterValues, anyTypeValuesContext.DECIMALLIST());
        } else if (anyTypeValuesContext.integerList() != null) {
            extractIntegerList(parameterValues, anyTypeValuesContext.integerList());
        } else if (anyTypeValuesContext.DATELIST() != null) {
            extractDATELIST(parameterValues, anyTypeValuesContext.DATELIST());
        } else if (anyTypeValuesContext.stringList() != null) {
            extractStringList(parameterValues, anyTypeValuesContext.stringList());
        } else {
            parameterValues.put("propertyValues", anyTypeValuesContext.getText());
        }
    }

    private static void extractDATELIST(Map<String, Object> parameterValues, TerminalNode dATELISTNode) {
        parameterValues.put(PROPERTY_VALUES_DATE, dATELISTNode.getText());
    }

    private static void extractIntegerList(Map<String, Object> parameterValues, ConditionParser.IntegerListContext integerListContext) {
        parameterValues.put("propertyValuesInteger", integerListContext.getText());
    }

    private static void extractDECIMALLIST(Map<String, Object> parameterValues, TerminalNode dECIMALLISTNode) {
        parameterValues.put("propertyValuesDouble", dECIMALLISTNode.getText());
    }

    private static void extractSTRING(Map<String, Object> parameterValues, TerminalNode sTRINGNode) {
        String text = StringUtil.stripSurroundedDoubleQuotes(sTRINGNode.getText());
        parameterValues.put(PROPERTY_VALUE, text);
    }

    private void extractDATE(Map<String, Object> parameterValues, TerminalNode dATENode) {
        String text = StringUtil.stripSurroundedDoubleQuotes(dATENode.getText());
        parameterValues.put(PROPERTY_VALUE_DATE, text);
    }

    private void extractInteger(Map<String, Object> parameterValues, ConditionParser.IntegerContext iNTNode) {
        parameterValues.put("propertyValueInteger", Integer.valueOf(iNTNode.getText()));
    }

    private void extractDECIMAL(Map<String, Object> parameterValues, TerminalNode dECIMALNode) {
        parameterValues.put("propertyValueDouble", Double.valueOf(dECIMALNode.getText()));
    }

    private static void extractDateRange(Map<String, Object> parameterValues, ConditionParser.DateRangeContext dateRangeContext) {
        String first = dateRangeContext.getText();
        String second = dateRangeContext.getText();
        parameterValues.put(PROPERTY_VALUES_DATE, List.of(first, second));
    }

    private static void extractIntegerRange(Map<String, Object> parameterValues, ConditionParser.IntegerRangeContext intRangeContext) {
        Integer first = Integer.valueOf(intRangeContext.integer(0).getText());
        Integer second = Integer.valueOf(intRangeContext.integer(1).getText());
        parameterValues.put("propertyValuesInteger", List.of(first, second));
    }

    private static void extractDecimalRange(Map<String, Object> parameterValues, ConditionParser.DecimalRangeContext decimalRangeContext) {
        Double first = Double.valueOf(decimalRangeContext.DECIMAL(0).getText());
        Double second = Double.valueOf(decimalRangeContext.DECIMAL(1).getText());
        parameterValues.put("propertyValuesDouble", List.of(first, second));
    }

    private void extractStringList(Map<String, Object> parameterValues, ConditionParser.StringListContext stringListContext) {
        List<String> strings = stringListContext.STRING().stream()
                .map(s -> StringUtil.stripSurroundedDoubleQuotes(s.getText()))
                .collect(Collectors.toList());
        parameterValues.put("propertyValues", strings);
    }
}
