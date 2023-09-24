package com.example.antlrfirstdemo;

import com.example.antlrfirstdemo.tour.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        CharStream charStream = getCharStream("t.data");
        DataLexer lexer = new DataLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DataParser parser = new DataParser(tokens);
        ParseTree file = parser.file();
        System.out.println(file.toStringTree(parser));
    }

    private static void rowTest() throws IOException {
        CharStream charStream = getCharStream("t.rows");
        RowsLexer lexer = new RowsLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        int col = 3;
        RowsParser parser = new RowsParser(tokens, col);
        parser.setBuildParseTree(false);
        parser.file();
    }

    private static void javaTest() throws IOException {
        CharStream charStream = getCharStream("Demo.java");
        JavaLexer lexer = new JavaLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();

        ParseTreeWalker walker = new ParseTreeWalker();
        ExtractInterfaceListener extractor = new ExtractInterfaceListener(parser);
        walker.walk(extractor, tree);
    }

    private static void labeledExprTest() throws IOException {
        CharStream charStream = getCharStream("t.expr");
        LabeledExprLexer lexer = new LabeledExprLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LabeledExprParser parser = new LabeledExprParser(tokens);
        ParseTree tree = parser.prog();
//        System.out.println(tree.toStringTree(parser));
        EvalVisitor eval = new EvalVisitor();
        eval.visit(tree);
    }

    private static void libExprTest() throws IOException {
        CharStream charStream = getCharStream("t.expr");
        LibExprLexer lexer = new LibExprLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LibExprParser parser = new LibExprParser(tokens);
        ParseTree tree = parser.prog();
        System.out.println(tree.toStringTree(parser));
    }

    private static void exprTest() throws IOException {
        CharStream charStream = getCharStream("t.expr");
//        ANTLRInputStream input = new ANTLRInputStream(is);
        ExprLexer lexer = new ExprLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);
        ParseTree tree = parser.prog();
        System.out.println(tree.toStringTree(parser));
    }

    private static CharStream getCharStream(String fileName) throws IOException {
        InputStream is = Main.class.getClassLoader().getResourceAsStream(fileName);
        if(is == null) {
            System.err.println("Could not find file " + fileName);
            System.exit(1);
        }
        return CharStreams.fromStream(is);
    }

}