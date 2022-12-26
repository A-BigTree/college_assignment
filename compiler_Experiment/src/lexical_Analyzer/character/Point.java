/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer.character
 * =====================================================
 * Title: Point.java
 * Created: [2022/12/26 14:28] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/26, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer.character;

public class Point extends InputCharacter{
    public Point() {
        super("point");
    }

    @Override
    public boolean isCharacter(char c) {
        return c == '.';
    }
}
