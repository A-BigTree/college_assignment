/**
 * ==================================================
 * Project: compiler_Experiment
 * Package: lexical_Analyzer
 * =====================================================
 * Title: AcceptState.java
 * Created: [2022/12/26 12:44] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/12/26, created by Shuxin-Wang.
 * 2.
 */


package lexical_Analyzer;

public enum AcceptState {
    ID("Id"),
    INTEGER("Integer"),
    FLOAT("Float"),
    OPERATOR("Operator"),
    SEPARATOR("Separator");
    private final String acceptName;

    private AcceptState(String acceptName){
        this.acceptName = acceptName;
    }

    public String getAcceptName() {
        return acceptName;
    }
}
