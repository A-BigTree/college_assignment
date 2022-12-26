/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer.character
 * =====================================================
 * Title: division.java
 * Created: [2022/12/26 13:48] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/26, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer.character;

public class Division extends InputCharacter{
    public Division() {
        super("division");
    }

    @Override
    public boolean isCharacter(char c) {
        return c == '/';
    }
}
