
import lexical_Analyzer.LexicalAnalyzer;

public class Main {
    public static void main(String[] args) {
        LexicalAnalyzer.init("data/lexical.txt", "data/lexical_out.txt");
        LexicalAnalyzer.analysisLexical();
    }
}