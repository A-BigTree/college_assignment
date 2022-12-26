/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer
 * =====================================================
 * Title: Token.java
 * Created: [2022/12/26 12:55] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/26, created by Shuxin-Wang.
 * 2.
 */

package lexical_Analyzer;

public class Token {
    private AcceptState state;
    private final StringBuilder token;

    public Token() {
        token = new StringBuilder();
    }

    public void setState(AcceptState state) {
        this.state = state;
    }

    public AcceptState getState() {
        return state;
    }

    public void addChar(char c) {
        token.append(c);
    }

    @Override
    public String toString() {
        return token.toString() + "\t:" + state.getAcceptName();
    }
}
