/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer.character
 * =====================================================
 * Title: Space.java
 * Created: [2022/12/26 15:55] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/26, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer.character;

public class Space extends InputCharacter{
    public Space() {
        super("space");
    }

    @Override
    public boolean isCharacter(char c) {
        return c == ' ';
    }
}
