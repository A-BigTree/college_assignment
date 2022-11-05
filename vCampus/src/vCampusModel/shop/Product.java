/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusModel.shop
 * =====================================================
 * Title: Product.java
 * Created: [2022/8/18 16:41] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/18, created by Shuxin-Wang.
 * 2.
 */

package vCampusModel.shop;

import java.io.Serializable;

public class Product implements Serializable {
    private String pid;
    private String pname;
    private double price;
    private int num;

    public Product() {

    }

    public Product(String pid, String panme, double price, int num) {
        this.pid = pid;
        this.pname = panme;
        this.price = price;
        this.num = num;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getPrice() {
        return price;
    }

    public int getNum() {
        return num;
    }

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public String toString() {
        return "pid:" + pid + "\tpname:" + pname + "\tprice:" + price + "\tnum:" + num;
    }
}
