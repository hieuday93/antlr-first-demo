package com.example.antlrfirstdemo.tour;

import com.example.antlrfirstdemo.cdp.ConditionBaseListener;
import com.example.antlrfirstdemo.cdp.ConditionParser;

public class ConditionListener extends ConditionBaseListener {

    private ConditionParser conditionParser;

    public ConditionListener(ConditionParser conditionParser) {
        this.conditionParser = conditionParser;
    }

    @Override
    public void enterSingleCondition(ConditionParser.SingleConditionContext ctx) {
        super.enterSingleCondition(ctx);
    }

    @Override
    public void exitSingleCondition(ConditionParser.SingleConditionContext ctx) {
        super.exitSingleCondition(ctx);
    }

    @Override
    public void enterRelationCondition(ConditionParser.RelationConditionContext ctx) {
        super.enterRelationCondition(ctx);
    }

    @Override
    public void exitRelationCondition(ConditionParser.RelationConditionContext ctx) {
        super.exitRelationCondition(ctx);
    }

    @Override
    public void enterGroupCondition(ConditionParser.GroupConditionContext ctx) {
        super.enterGroupCondition(ctx);
    }

    @Override
    public void exitGroupCondition(ConditionParser.GroupConditionContext ctx) {
        super.exitGroupCondition(ctx);
    }
}
