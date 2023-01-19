/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: syntax_Parser.expression
 * =====================================================
 * Title: TerminalExpression.java
 * Created: [2022/12/27 11:01] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/27, created by Shuxin-Wang.
 * 2.
 */

package syntax_Parser.expression;


public abstract class TerminalExpression extends AbstractExpression {

    public TerminalExpression(String name) {
        super(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractExpression expression = (AbstractExpression) o;
        return this.getName().equals(expression.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

}
