/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer.character
 * =====================================================
 * Title: LeftBracket.java
 * Created: [2022/12/27 11:35] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/27, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer.character;

public class LeftBracket extends InputCharacter{
    public LeftBracket() {
        super("Left bracket");
    }

    @Override
    public boolean isCharacter(char c) {
        return c == '(';
    }
}
