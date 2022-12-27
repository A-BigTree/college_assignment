/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: syntax_Parser
 * =====================================================
 * Title: ForecastTable.java
 * Created: [2022/12/27 12:01] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/27, created by Shuxin-Wang.
 * 2.
 */

package syntax_Parser;

import lexical_Analyzer.Token;
import syntax_Parser.expression.*;
import syntax_Parser.expression.nonterminal.*;
import syntax_Parser.expression.terminal.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ForecastTable {
    //文法表
    public static ArrayList<NonterminalExpression> grammars = new ArrayList<>();
    //输入终结符记录表
    private static final ArrayList<TerminalExpression> terminals = new ArrayList<>();
    //预测分析表
    private static final HashMap<String, HashMap<TerminalExpression, Integer>> forecastTable = new HashMap<>();

    //初始化终结符
    private static void initTerminals() {
        terminals.add(new Id()); //0
        terminals.add(new Plus()); //1
        terminals.add(new Multiple()); //2
        terminals.add(new LeftBracket()); //3
        terminals.add(new RightBracket()); //4
        terminals.add(new Terminator()); //5
    }

    private static void initGrammars() {
        //初始化非终结符
        NonterminalExpression E = new ExpressionE(),
                E1 = new ExpressionE1(),
                T = new ExpressionT(),
                T1 = new ExpressionT1(),
                F = new ExpressionF();
        //文法0：
        NonterminalExpression grammar0 = new ExpressionE();
        ArrayList<AbstractExpression> g0 = new ArrayList<>();
        g0.add(T);
        g0.add(E1);
        grammar0.setGrammar(g0);
        grammars.add(grammar0);
        //文法1：
        NonterminalExpression grammar1 = new ExpressionE1();
        ArrayList<AbstractExpression> g1 = new ArrayList<>();
        g1.add(terminals.get(1));
        g1.add(T);
        g1.add(E1);
        grammar1.setGrammar(g1);
        grammars.add(grammar1);
        //文法2：
        NonterminalExpression grammar2 = new ExpressionE1();
        ArrayList<AbstractExpression> g2 = new ArrayList<>();
        g2.add(new EmptyString());
        grammar2.setGrammar(g2);
        grammar2.setEmptyString(true);
        grammars.add(grammar2);
        //文法3：
        NonterminalExpression grammar3 = new ExpressionT();
        ArrayList<AbstractExpression> g3 = new ArrayList<>();
        g3.add(F);
        g3.add(T1);
        grammar3.setGrammar(g3);
        grammars.add(grammar3);
        //文法4：
        NonterminalExpression grammar4 = new ExpressionT1();
        ArrayList<AbstractExpression> g4 = new ArrayList<>();
        g4.add(terminals.get(2));
        g4.add(F);
        g4.add(T1);
        grammar4.setGrammar(g4);
        grammars.add(grammar4);
        //文法5：
        NonterminalExpression grammar5 = new ExpressionT1();
        ArrayList<AbstractExpression> g5 = new ArrayList<>();
        g5.add(new EmptyString());
        grammar5.setGrammar(g5);
        grammar5.setEmptyString(true);
        grammars.add(grammar5);
        //文法6：
        NonterminalExpression grammar6 = new ExpressionF();
        ArrayList<AbstractExpression> g6 = new ArrayList<>();
        g6.add(terminals.get(3));
        g6.add(E);
        g6.add(terminals.get(4));
        grammar6.setGrammar(g6);
        grammars.add(grammar6);
        //文法7：
        NonterminalExpression grammar7 = new ExpressionF();
        ArrayList<AbstractExpression> g7 = new ArrayList<>();
        g7.add(terminals.get(0));
        grammar7.setGrammar(g7);
        grammars.add(grammar7);
    }

    private static void initForecastTable(){
        //非终结符E：
        HashMap<TerminalExpression, Integer> exE = new HashMap<>();
        exE.put(terminals.get(0), 0);
        exE.put(terminals.get(1), -1);
        exE.put(terminals.get(2), -1);
        exE.put(terminals.get(3), 0);
        exE.put(terminals.get(4), -1);
        exE.put(terminals.get(5), -1);
        forecastTable.put("E",exE);
        //非终结符E‘：
        HashMap<TerminalExpression, Integer> exE1 = new HashMap<>();
        exE1.put(terminals.get(0), -1);
        exE1.put(terminals.get(1), 1);
        exE1.put(terminals.get(2), -1);
        exE1.put(terminals.get(3), -1);
        exE1.put(terminals.get(4), 2);
        exE1.put(terminals.get(5), 2);
        forecastTable.put("E1",exE1);
        //非终结符T：
        HashMap<TerminalExpression, Integer> exT = new HashMap<>();
        exT.put(terminals.get(0), 3);
        exT.put(terminals.get(1), -1);
        exT.put(terminals.get(2), -1);
        exT.put(terminals.get(3), 3);
        exT.put(terminals.get(4), -1);
        exT.put(terminals.get(5), -1);
        forecastTable.put("T", exT);
        //非终结符T'：
        HashMap<TerminalExpression, Integer> exT1 = new HashMap<>();
        exT1.put(terminals.get(0), -1);
        exT1.put(terminals.get(1), 5);
        exT1.put(terminals.get(2), 4);
        exT1.put(terminals.get(3), -1);
        exT1.put(terminals.get(4), 5);
        exT1.put(terminals.get(5), 5);
        forecastTable.put("T1", exT1);
        //非终结符F：
        HashMap<TerminalExpression, Integer> exF = new HashMap<>();
        exF.put(terminals.get(0), 7);
        exF.put(terminals.get(1), -1);
        exF.put(terminals.get(2), -1);
        exF.put(terminals.get(3), 6);
        exF.put(terminals.get(4), -1);
        exF.put(terminals.get(5), -1);
        forecastTable.put("F", exF);
    }

    public static void init(){
        initTerminals();
        initGrammars();
        initForecastTable();
    }

    public static int forecastAnalysis(AbstractExpression expression, Token token) throws Exception {
        //匹配动作
        if (expression.isToken(token)){
            return -2;
        }
        //寻找归约
        TerminalExpression terminalExpression = null;
        for(TerminalExpression terminal: terminals){
            terminalExpression = terminal;
            if(terminalExpression.isToken(token)){
                break;
            }else{
                terminalExpression = null;
            }
        }
        if(terminalExpression == null){
            throw new Exception("No Input Token:" + token.toString());
        }
        return forecastTable.get(expression.getName()).get(terminalExpression);
    }
}
