/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer.character
 * =====================================================
 * Title: Letter.java
 * Created: [2022/12/26 13:33] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/26, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer.character;

public class Letter extends InputCharacter{
    public Letter() {
        super("letter");
    }

    @Override
    public boolean isCharacter(char c) {
        return ((int)c >= 65 && (int)c <= 90) || ((int)c >= 97 && (int)c <= 122);
    }
}
