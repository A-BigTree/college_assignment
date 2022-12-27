/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer.character
 * =====================================================
 * Title: RightBracket.java
 * Created: [2022/12/27 11:36] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/27, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer.character;

public class RightBracket extends InputCharacter{
    public RightBracket() {
        super("Right bracket");
    }

    @Override
    public boolean isCharacter(char c) {
        return c == ')';
    }
}
