package com.example.antlrfirstdemo.tour;

import org.antlr.v4.runtime.TokenStream;

public class ExtractInterfaceListener extends JavaBaseListener {

    private JavaParser parser;

    public ExtractInterfaceListener(JavaParser parser) {
        this.parser = parser;
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        System.out.println("interface I" + ctx.Identifier().getText() + " {");
    }

    @Override
    public void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        System.out.println("}");
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        TokenStream tokens = parser.getTokenStream();
        String type = "void";
        if(ctx.type() != null) {
            type = tokens.getText(ctx.type());
        }
        String args = tokens.getText(ctx.formalParameters());
        System.out.println("\t" + type + " " + ctx.Identifier() + args + ";");
    }

    @Override
    public void enterImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
        ctx.children.forEach(pt -> {
            String textToPrint;
            if("import".equals(pt.getText()) || "static".equals(pt.getText())) {
                textToPrint = pt.getText() + " ";
            } else if (";".equals(pt.getText())) {
                textToPrint = pt.getText() + "\n";
            } else {
                textToPrint = pt.getText();
            }
            System.out.print(textToPrint);
        });
    }
}
