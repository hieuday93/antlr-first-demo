package com.example.antlrfirstdemo.tour;

import com.example.antlrfirstdemo.cdp.ConditionBaseVisitor;
import com.example.antlrfirstdemo.cdp.ConditionParser;
import com.example.antlrfirstdemo.condition.BooleanCondition;
import com.example.antlrfirstdemo.condition.Condition;
import com.example.antlrfirstdemo.condition.ProfilePropertyCondition;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConditionVisitor extends ConditionBaseVisitor<Condition> {

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

        parameterValues.put("propertyName", ctx.propertyPath().getText());
        parameterValues.put("comparisonOperator", "equals");

        extractAnyTypeValue(parameterValues, ctx.anyTypeValue());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionNotEquals(ConditionParser.PropertyConditionNotEqualsContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put("propertyName", ctx.propertyPath().getText());
        parameterValues.put("comparisonOperator", "notEquals");

        extractAnyTypeValue(parameterValues, ctx.anyTypeValue());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionGreaterThan(ConditionParser.PropertyConditionGreaterThanContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put("propertyName", ctx.propertyPath().getText());
        parameterValues.put("comparisonOperator", "greaterThan");

        extractNonPureTextValue(parameterValues, ctx.nonPureTextValue());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionGreaterThanOrEqualTo(ConditionParser.PropertyConditionGreaterThanOrEqualToContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put("propertyName", ctx.propertyPath().getText());
        parameterValues.put("comparisonOperator", "greaterThanOrEqualTo");

        extractNonPureTextValue(parameterValues, ctx.nonPureTextValue());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionLessThan(ConditionParser.PropertyConditionLessThanContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put("propertyName", ctx.propertyPath().getText());
        parameterValues.put("comparisonOperator", "lessThan");

        extractNonPureTextValue(parameterValues, ctx.nonPureTextValue());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionLessThanOrEqualTo(ConditionParser.PropertyConditionLessThanOrEqualToContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put("propertyName", ctx.propertyPath().getText());
        parameterValues.put("comparisonOperator", "lessThanOrEqualTo");

        extractNonPureTextValue(parameterValues, ctx.nonPureTextValue());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionBetween(ConditionParser.PropertyConditionBetweenContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put("propertyName", ctx.propertyPath().getText());
        parameterValues.put("comparisonOperator", "between");

        extractAnyTypeRange(parameterValues, ctx.anyTypeRange());

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionExists(ConditionParser.PropertyConditionExistsContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put("propertyName", ctx.propertyPath().getText());
        parameterValues.put("comparisonOperator", "exists");

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionMissing(ConditionParser.PropertyConditionMissingContext ctx) {
        Map<String, Object> parameterValues = new LinkedHashMap<>();

        parameterValues.put("propertyName", ctx.propertyPath().getText());
        parameterValues.put("comparisonOperator", "missing");

        return new ProfilePropertyCondition(parameterValues);
    }

    @Override
    public Condition visitPropertyConditionContains(ConditionParser.PropertyConditionContainsContext ctx) {
        return super.visitPropertyConditionContains(ctx);
    }

    @Override
    public Condition visitPropertyConditionNotContains(ConditionParser.PropertyConditionNotContainsContext ctx) {
        return super.visitPropertyConditionNotContains(ctx);
    }

    @Override
    public Condition visitPropertyConditionStartsWith(ConditionParser.PropertyConditionStartsWithContext ctx) {
        return super.visitPropertyConditionStartsWith(ctx);
    }

    @Override
    public Condition visitPropertyConditionEndsWith(ConditionParser.PropertyConditionEndsWithContext ctx) {
        return super.visitPropertyConditionEndsWith(ctx);
    }

    @Override
    public Condition visitPropertyConditionMatchesRegex(ConditionParser.PropertyConditionMatchesRegexContext ctx) {
        return super.visitPropertyConditionMatchesRegex(ctx);
    }

    @Override
    public Condition visitPropertyConditionIn(ConditionParser.PropertyConditionInContext ctx) {
        return super.visitPropertyConditionIn(ctx);
    }

    @Override
    public Condition visitPropertyConditionNotIn(ConditionParser.PropertyConditionNotInContext ctx) {
        return super.visitPropertyConditionNotIn(ctx);
    }

    @Override
    public Condition visitPropertyConditionAll(ConditionParser.PropertyConditionAllContext ctx) {
        return super.visitPropertyConditionAll(ctx);
    }

    @Override
    public Condition visitPropertyConditionInContains(ConditionParser.PropertyConditionInContainsContext ctx) {
        return super.visitPropertyConditionInContains(ctx);
    }

    @Override
    public Condition visitPropertyConditionHasSomeOf(ConditionParser.PropertyConditionHasSomeOfContext ctx) {
        return super.visitPropertyConditionHasSomeOf(ctx);
    }

    @Override
    public Condition visitPropertyConditionHasNoneOf(ConditionParser.PropertyConditionHasNoneOfContext ctx) {
        return super.visitPropertyConditionHasNoneOf(ctx);
    }

    @Override
    public Condition visitPropertyConditionIsDay(ConditionParser.PropertyConditionIsDayContext ctx) {
        return super.visitPropertyConditionIsDay(ctx);
    }

    @Override
    public Condition visitPropertyConditionIsNotDay(ConditionParser.PropertyConditionIsNotDayContext ctx) {
        return super.visitPropertyConditionIsNotDay(ctx);
    }

    public Condition getMainCondition() {
        return mainCondition;
    }

    public void setMainCondition(Condition mainCondition) {
        this.mainCondition = mainCondition;
    }

    private static void extractAnyTypeValue(Map<String, Object> parameterValues, ConditionParser.AnyTypeValueContext anyTypeValueContext) {
        if(anyTypeValueContext.DECIMAL() != null) {
            parameterValues.put("propertyValueDouble", Double.valueOf(anyTypeValueContext.DECIMAL().getText()));
        } else if (anyTypeValueContext.INT() != null) {
            parameterValues.put("propertyValueInteger", Integer.valueOf(anyTypeValueContext.INT().getText()));
        } else if (anyTypeValueContext.DATETIME() != null) {
            parameterValues.put("propertyValueDate", anyTypeValueContext.DATETIME().getText());
        } else if (anyTypeValueContext.DATE() != null) {
            parameterValues.put("propertyValueDate", anyTypeValueContext.DATE().getText());
        } else if (anyTypeValueContext.STRING() != null) {
            parameterValues.put("propertyValue", anyTypeValueContext.STRING().getText());
        } else {
            parameterValues.put("propertyValue", anyTypeValueContext.getText());
        }
    }

    private static void extractNonPureTextValue(Map<String, Object> parameterValues, ConditionParser.NonPureTextValueContext nonPureTextValueContext) {
        if(nonPureTextValueContext.DECIMAL() != null) {
            parameterValues.put("propertyValueDouble", Double.valueOf(nonPureTextValueContext.DECIMAL().getText()));
        } else if (nonPureTextValueContext.INT() != null) {
            parameterValues.put("propertyValueInteger", Integer.valueOf(nonPureTextValueContext.INT().getText()));
        } else if (nonPureTextValueContext.DATETIME() != null) {
            parameterValues.put("propertyValueDate", nonPureTextValueContext.DATETIME().getText());
        } else if (nonPureTextValueContext.DATE() != null) {
            parameterValues.put("propertyValueDate", nonPureTextValueContext.DATE().getText());
        } else {
            parameterValues.put("propertyValue", nonPureTextValueContext.getText());
        }
    }

    private static void extractAnyTypeRange(Map<String, Object> parameterValues, ConditionParser.AnyTypeRangeContext anyTypeRangeContext) {
        if(anyTypeRangeContext.decimalRange() != null) {
            Double first = Double.valueOf(anyTypeRangeContext.decimalRange().DECIMAL(0).getText());
            Double second = Double.valueOf(anyTypeRangeContext.decimalRange().DECIMAL(1).getText());
            parameterValues.put("propertyValuesDouble", List.of(first, second));
        } else if (anyTypeRangeContext.intRange() != null) {
            Integer first = Integer.valueOf(anyTypeRangeContext.intRange().INT(0).getText());
            Integer second = Integer.valueOf(anyTypeRangeContext.intRange().INT(1).getText());
            parameterValues.put("propertyValuesInteger", List.of(first, second));
        } else if (anyTypeRangeContext.dateTimeRange() != null) {
            String first = anyTypeRangeContext.dateTimeRange().getText();
            String second = anyTypeRangeContext.dateTimeRange().getText();
            parameterValues.put("propertyValuesDate", List.of(first, second));
        } else if (anyTypeRangeContext.dateRange() != null) {
            String first = anyTypeRangeContext.dateTimeRange().getText();
            String second = anyTypeRangeContext.dateTimeRange().getText();
            parameterValues.put("propertyValuesDate", List.of(first, second));
        } else {
            throw new RuntimeException("Cannot parse anyTypeRange: " + anyTypeRangeContext.getText());
        }
    }
}
