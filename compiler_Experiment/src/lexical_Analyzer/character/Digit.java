/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer.character
 * =====================================================
 * Title: digit.java
 * Created: [2022/12/26 13:37] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/26, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer.character;

public class Digit extends InputCharacter{
    public Digit() {
        super("digit");
    }

    @Override
    public boolean isCharacter(char c) {
        return (int)c >= 48 && (int)c <= 57;
    }
}
