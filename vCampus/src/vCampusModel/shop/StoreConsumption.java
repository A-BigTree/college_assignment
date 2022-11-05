/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusModel.shop
 * =====================================================
 * Title: StoreConsumption.java
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
import java.util.ArrayList;

public class StoreConsumption implements Serializable {
    private String uid;
    private ArrayList<Product> productList;

    public StoreConsumption() {

    }

    public StoreConsumption(String uid, ArrayList<Product> productList) {
        this.uid = uid;
        this.productList = productList;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public String getUid() {
        return uid;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public String toString() {
        StringBuilder list = new StringBuilder();
        for (Product product : productList) {
            list.append(product.toString()).append("\n");
        }

        return "uid:" + uid + "\n" + list.toString();
    }
}
