/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer.character
 * =====================================================
 * Title: Underline.java
 * Created: [2022/12/26 13:42] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/26, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer.character;

public class Underline extends InputCharacter{
    public Underline() {
        super("underline");
    }

    @Override
    public boolean isCharacter(char c) {
        return c == '_';
    }
}
