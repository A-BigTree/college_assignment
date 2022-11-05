/**
 * ==================================================
 * Project: BaseDao.java
 * Package: vCampusServer.mlp
 * =====================================================
 * Title: StoreMlp.java
 * Created: [2022/8/22 14:56] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/22, created by Shuxin-Wang.
 * 2.
 */

package vCampusServer.mlp;

import vCampusModel.shop.Product;
import vCampusModel.shop.StoreConsumption;
import vCampusModel.user.User;
import vCampusModel.vo.SelectManage;
import vCampusServer.dao.BaseDao;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StoreMlp {

    private StoreConsumption sql2store(String uid, BaseDao dao, String sql) {
        ArrayList<Product> products = new ArrayList<Product>();

        ResultSet resultSet = dao.selectSQL(sql);

        try {
            while (resultSet.next()) {
                Product p = new Product(resultSet.getString("pid"),
                        resultSet.getString("pname"),
                        Double.parseDouble(resultSet.getString("price")),
                        Integer.parseInt(resultSet.getString("num")));
                products.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        dao.disconnect();

        return new StoreConsumption(uid, products);
    }

    public StoreConsumption visitAllProduct() {

        BaseDao dao = new BaseDao();
        dao.getConnect();
        String sql = "SELECT* FROM store";

        return this.sql2store("1", dao, sql);

    }

    public StoreConsumption searchProduct(SelectManage selectManage) {

        BaseDao dao = new BaseDao();
        dao.getConnect();
        String sql = "SELECT* FROM store WHERE " + selectManage.getKey() + " LIKE '%" + selectManage.getValue() + "%'";

        return this.sql2store("1", dao, sql);
    }

    public Boolean buyProduct(StoreConsumption buyList) {
        BaseDao dao = new BaseDao();
        dao.getConnect();

        for (Product product : buyList.getProductList()) {
            String sql = "SELECT* FROM store WHERE pid = '" + product.getPid() + "'";
            ResultSet resultSet = dao.selectSQL(sql);
            try {
                int num = 0;
                if(resultSet.next()){
                    num = Integer.parseInt(resultSet.getString("num"));
                }

                if (product.getNum() < num) {
                    String sql2 = "UPDATE store SET num = '" + (num - product.getNum()) + "' WHERE pid = '" + product.getPid() + "'";
                    dao.upDataSQL(sql2);
                    String sql3 = "INSERT INTO storeConsumption VALUES('" + buyList.getUid() + "', '" + product.getPid() + "', '" + product.getPname() + " ×" + product.getNum() + "', '" + product.getPrice() * product.getNum() + "')";
                    dao.upDataSQL(sql3);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    public StoreConsumption seeConsumption(User user) {
        BaseDao dao = new BaseDao();
        dao.getConnect();
        String sql = "SELECT* FROM storeConsumption WHERE uid = '" + user.getUid() + "'";
        ResultSet resultSet = dao.selectSQL(sql);

        ArrayList<Product> products = new ArrayList<Product>();
        try {
            while (resultSet.next()) {
                products.add(
                        new Product(resultSet.getString("pid"),
                                resultSet.getString("pname"),
                                Double.parseDouble(resultSet.getString("price")),
                                1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        dao.disconnect();

        return new StoreConsumption(user.getUid(), products);
    }

    public static void main(String[] args) {
        StoreMlp sm = new StoreMlp();
        //System.out.println(sm.visitAllProduct());
        //SelectManage s = new SelectManage("pname", "雪糕");
        //System.out.println(sm.searchProduct(s));
        /*
        Product p1 = new Product("00001", "雪糕1", 10.0, 1),
                p2 = new Product("00003", "薯片1", 4.0, 2),
                p3 = new Product("00004", "面包1", 2.0, 3);
        ArrayList<Product> l = new ArrayList<Product>();
        l.add(p1);
        l.add(p2);
        System.out.println(sm.buyProduct(new StoreConsumption("09020237", l)));*/
        System.out.println(sm.seeConsumption(new User("09020237", "1", 1)));
    }
}
