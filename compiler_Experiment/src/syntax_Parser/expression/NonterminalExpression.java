/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: syntax_Parser.expression
 * =====================================================
 * Title: NonterminalExpression.java
 * Created: [2022/12/27 11:02] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/27, created by Shuxin-Wang.
 * 2.
 */

package syntax_Parser.expression;

import lexical_Analyzer.Token;

import java.util.ArrayList;

public abstract class NonterminalExpression extends AbstractExpression {
    private ArrayList<AbstractExpression> grammar;
    private boolean isEmptyString = false;

    public NonterminalExpression(String name) {
        super(name);
    }

    public void setGrammar(ArrayList<AbstractExpression> grammar) {
        this.grammar = grammar;
    }

    public ArrayList<AbstractExpression> getGrammar() {
        return grammar;
    }

    public void setEmptyString(boolean emptyString) {
        isEmptyString = emptyString;
    }

    public boolean isEmptyString() {
        return isEmptyString;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" → ");
        for (AbstractExpression expression : grammar) {
            builder.append(expression.getName()).append(" ");
        }
        return builder.toString();
    }

    @Override
    public boolean isToken(Token token) {
        return false;
    }
}
