/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: syntax_Parser.expression
 * =====================================================
 * Title: AbstractExpression.java
 * Created: [2022/12/27 10:56] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/27, created by Shuxin-Wang.
 * 2.
 */

package syntax_Parser.expression;

import lexical_Analyzer.Token;

public abstract class AbstractExpression {
    private final String name;

    public AbstractExpression(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean isToken(Token token);
}
