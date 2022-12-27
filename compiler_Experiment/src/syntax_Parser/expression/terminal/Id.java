/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: syntax_Parser.expression.terminal
 * =====================================================
 * Title: Id.java
 * Created: [2022/12/27 11:15] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/27, created by Shuxin-Wang.
 * 2.
 */

package syntax_Parser.expression.terminal;

import lexical_Analyzer.Token;
import syntax_Parser.expression.TerminalExpression;

public class Id extends TerminalExpression {
    public Id() {
        super("Id");
    }

    @Override
    public boolean isToken(Token token) {
        return token.getState().getAcceptName().equals(this.getName()) ||
                token.getState().getAcceptName().equals("Integer") ||
                token.getState().getAcceptName().equals("Float");
    }
}
