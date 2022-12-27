
import lexical_Analyzer.LexicalAnalyzer;
import lexical_Analyzer.Token;
import syntax_Parser.SyntaxParser;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("======================================================");
        LexicalAnalyzer.init("data/syntax.txt", "data/lexical_out.txt");
        LexicalAnalyzer.analysisLexical();
        System.out.println("======================================================");
        ArrayList<Token> tokens = LexicalAnalyzer.getTokens();
        SyntaxParser.init("data/syntax_out.txt");
        SyntaxParser.syntaxParser(tokens);
    }
}