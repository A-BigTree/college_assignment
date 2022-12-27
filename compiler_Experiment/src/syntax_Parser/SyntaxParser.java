/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: syntax_Parser.expression
 * =====================================================
 * Title: SyntaxParser.java
 * Created: [2022/12/27 10:55] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/27, created by Shuxin-Wang.
 * 2.
 */

package syntax_Parser;

import lexical_Analyzer.AcceptState;
import lexical_Analyzer.Token;
import syntax_Parser.expression.AbstractExpression;
import syntax_Parser.expression.NonterminalExpression;
import syntax_Parser.expression.nonterminal.ExpressionE;
import syntax_Parser.expression.terminal.Terminator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class SyntaxParser {
    private static FileWriter fileWriter;

    public static void init(String outFile){
        ForecastTable.init();
        try {
            fileWriter = new FileWriter(outFile, false);
            fileWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            fileWriter = new FileWriter(outFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void syntaxParser(ArrayList<Token> tokens){
        System.out.println("\nSyntax Parser:\n");
        //token预处理
        Token terminator = new Token();
        terminator.addChar('$');
        terminator.setState(AcceptState.SEPARATOR);
        tokens.add(terminator);
        System.out.println(tokens);
        //栈预处理
        Stack<AbstractExpression> stack = new Stack<>();
        stack.push(new Terminator());
        stack.push(new ExpressionE());
        //token串指针
        int forward = 0;
        //开始归约
        while(forward < tokens.size()){
            AbstractExpression expression = stack.peek();
            int temp;
            try{
                temp = ForecastTable.forecastAnalysis(expression, tokens.get(forward));
            } catch (Exception e) {
                System.out.println("Error: " + forward + ":");
                for(int i = forward; i < tokens.size() - 1; i++){
                    System.out.print(tokens.get(i).getToken() + " ");
                }
                System.out.print('\n');
                e.printStackTrace();
                return;
            }
            if(temp == -1){//出现错误
                System.out.println("Error: " + forward + ":");
                for(int i = forward; i < tokens.size() - 1; i++){
                    System.out.print(tokens.get(i).getToken() + " ");
                }
                System.out.print('\n');
                return;
            }else if (temp == -2){//匹配动作
                try {
                    fileWriter.write("Match:" + tokens.get(forward).toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Match:" + tokens.get(forward).toString());
                stack.pop();
                forward++;
            }else {//输出动作
                NonterminalExpression grammar = ForecastTable.grammars.get(temp);
                System.out.println("Output:" + grammar.toString());
                try {
                    fileWriter.write("Output:" + grammar + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(grammar.isEmptyString()){//空串输出
                    stack.pop();
                }else{
                    stack.pop();
                    for(int i = grammar.getGrammar().size() - 1; i >= 0; i--){
                        stack.push(grammar.getGrammar().get(i));
                    }
                }
            }
        }
        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
