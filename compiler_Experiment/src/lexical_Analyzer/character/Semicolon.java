/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer.character
 * =====================================================
 * Title: Semicolon.java
 * Created: [2022/12/26 13:57] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/26, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer.character;

public class Semicolon extends InputCharacter{
    public Semicolon() {
        super("semicolon");
    }

    @Override
    public boolean isCharacter(char c) {
        return c == ';';
    }
}
