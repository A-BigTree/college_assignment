/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: syntax_Parser.expression.terminal
 * =====================================================
 * Title: EmptyString.java
 * Created: [2022/12/27 11:46] by Shuxin-Wang
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

public class EmptyString extends TerminalExpression {
    public EmptyString() {
        super("Empty string");
    }

    @Override
    public boolean isToken(Token token) {
        return false;
    }
}
