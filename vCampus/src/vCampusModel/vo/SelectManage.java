/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusModel.vo
 * =====================================================
 * Title: SelectManage.java
 * Created: [2022/8/18 16:52] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/18, created by Shuxin-Wang.
 * 2.
 */

package vCampusModel.vo;

import java.io.Serializable;

public class SelectManage implements Serializable {
    private String key;
    private String value;

    public SelectManage() {

    }

    public SelectManage(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return "key:" + key + "\tvalue:" + value;
    }
}
