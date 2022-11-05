/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusModel.bank
 * =====================================================
 * Title: BankManage.java
 * Created: [2022/8/18 16:46] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/18, created by Shuxin-Wang.
 * 2.
 */

package vCampusModel.bank;

import java.io.Serializable;

public class BankManage implements Serializable {
    private String uid;
    private int operation;
    private double amount;
    private String remarks;

    public BankManage() {

    }

    public BankManage(String uid, int operation, double amount, String remarks) {
        this.uid = uid;
        this.operation = operation;
        this.amount = amount;
        this.remarks = remarks;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUid() {
        return uid;
    }

    public double getAmount() {
        return amount;
    }

    public int getOperation() {
        return operation;
    }

    public String getRemarks() {
        return remarks;
    }

    public String toString() {
        return "uid:" + uid + "\toperation:" + operation + "\tamount:" + amount + "\tremarks:" + remarks;
    }
}
