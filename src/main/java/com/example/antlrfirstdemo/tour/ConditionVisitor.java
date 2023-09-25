package com.example.antlrfirstdemo.tour;

import com.example.antlrfirstdemo.cdp.ConditionBaseVisitor;
import com.example.antlrfirstdemo.cdp.ConditionParser;
import com.example.antlrfirstdemo.condition.Condition;

public class ConditionVisitor extends ConditionBaseVisitor<Condition> {

    @Override
    public Condition visitGroupCondition(ConditionParser.GroupConditionContext ctx) {
        return super.visitGroupCondition(ctx);
    }

    @Override
    public Condition visitRelationCondition(ConditionParser.RelationConditionContext ctx) {
        return super.visitRelationCondition(ctx);
    }

    @Override
    public Condition visitSingleCondition(ConditionParser.SingleConditionContext ctx) {
        return super.visitSingleCondition(ctx);
    }
}
