/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusModel.bank
 * =====================================================
 * Title: BankRecords.java
 * Created: [2022/8/18 16:47] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/18, created by Shuxin-Wang.
 * 2.
 */

package vCampusModel.bank;

import java.io.Serializable;
import java.util.ArrayList;

public class BankRecords implements Serializable {
    private String uid;
    private ArrayList<BankManage> bankManageList;

    public BankRecords() {

    }

    public BankRecords(String uid, ArrayList<BankManage> bankManageList) {
        this.uid = uid;
        this.bankManageList = bankManageList;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setBankManageList(ArrayList<BankManage> bankManageList) {
        this.bankManageList = bankManageList;
    }

    public String getUid() {
        return uid;
    }

    public ArrayList<BankManage> getBankManageList() {
        return bankManageList;
    }

    public String toString() {
        StringBuilder list = new StringBuilder();
        for (BankManage bankManage : bankManageList) {
            list.append(bankManage.toString()).append("\n");
        }

        return "uid:" + uid + "\n" + list.toString();
    }
}
