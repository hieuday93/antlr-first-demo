package com.example.antlrfirstdemo.tour;

import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends LabeledExprBaseVisitor<Integer> {

    private Map<String, Integer> memory = new HashMap<>();

    @Override
    public Integer visitAssign(LabeledExprParser.AssignContext ctx) {
        // stat : ID '=' expr NEWLINE
        String id = ctx.ID().getText();
        int value = visit(ctx.expr());
        memory.put(id, value);
        return value;
    }

    @Override
    public Integer visitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
        // stat:   expr NEWLINE
        Integer value = visit(ctx.expr());
        System.out.println(value);
        return 0;
    }

    @Override
    public Integer visitInt(LabeledExprParser.IntContext ctx) {
        // INT : [0-9]+;
        return Integer.parseInt(ctx.INT().getText());
    }

    @Override
    public Integer visitId(LabeledExprParser.IdContext ctx) {
        // ID : [a-zA-Z]+;
        String id = ctx.ID().getText();
        if(memory.containsKey(id)) {
            return memory.get(id);
        }
        return 0;
    }

    @Override
    public Integer visitMulDiv(LabeledExprParser.MulDivContext ctx) {
        // expr:   expr op=('*'|'/') expr
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if(ctx.op.getType() == LabeledExprParser.MUL) {
            return left * right;
        }
        return left / right;
    }

    @Override
    public Integer visitAddSub(LabeledExprParser.AddSubContext ctx) {
        // expr:   expr op=('+'|'-') expr
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if(ctx.op.getType() == LabeledExprParser.ADD) {
            return left + right;
        }
        return left - right;
    }

    @Override
    public Integer visitParens(LabeledExprParser.ParensContext ctx) {
        // expr:   '(' expr ')'
        return visit(ctx.expr());
    }

    @Override
    public Integer visitClear(LabeledExprParser.ClearContext ctx) {
        memory.clear();
        return 0;
    }
}
