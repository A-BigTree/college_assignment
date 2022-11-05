/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusModel.library
 * =====================================================
 * Title: Book.java
 * Created: [2022/8/18 16:34] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/18, created by Shuxin-Wang.
 * 2.
 */

package vCampusModel.library;

import java.io.Serializable;

public class Book implements Serializable {
    private String bid;

    private String bname;

    private double price;

    private String time;

    private String author;

    public Book() {

    }

    public Book(String bid, String bname, double price,
                String time, String author) {
        this.bid = bid;
        this.bname = bname;
        this.price = price;
        this.time = time;
        this.author = author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public String getBid() {
        return bid;
    }

    public String getBname() {
        return bname;
    }

    public String getTime() {
        return time;
    }

    public String toString() {
        return "bid:" + bid + "\tbname:" + bname;
    }
}
