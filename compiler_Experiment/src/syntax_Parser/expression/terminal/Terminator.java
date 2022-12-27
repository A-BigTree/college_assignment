/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: syntax_Parser.expression.terminal
 * =====================================================
 * Title: Terminator.java
 * Created: [2022/12/27 11:42] by Shuxin-Wang
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

public class Terminator extends TerminalExpression {
    public Terminator() {
        super("Terminator $");
    }

    @Override
    public boolean isToken(Token token) {
        return token.getToken().equals("$");
    }
}
