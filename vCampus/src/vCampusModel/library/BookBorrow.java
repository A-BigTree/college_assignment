/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusModel.library
 * =====================================================
 * Title: BookBorrow.java
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
import java.util.ArrayList;

public class BookBorrow implements Serializable {
    private String uid;

    private ArrayList<Book> bookList;

    public BookBorrow() {

    }

    public BookBorrow(String uid, ArrayList<Book> bookList) {
        this.uid = uid;
        this.bookList = bookList;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public String getUid() {
        return uid;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public String toString() {
        StringBuilder list = new StringBuilder();
        for (Book book : bookList) {
            list.append(book.toString()).append("\n");
        }

        return "uid:" + uid + "\n" + list.toString();
    }
}
