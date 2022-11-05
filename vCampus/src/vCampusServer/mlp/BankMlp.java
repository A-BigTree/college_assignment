/**
 * ==================================================
 * Project: BaseDao.java
 * Package: vCampusServer.mlp
 * =====================================================
 * Title: BankMlp.java
 * Created: [2022/8/23 8:54] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/23, created by Shuxin-Wang.
 * 2.
 */

package vCampusServer.mlp;

import vCampusModel.bank.BankManage;
import vCampusModel.bank.BankRecords;
import vCampusModel.user.User;
import vCampusServer.dao.BaseDao;

import java.sql.ResultSet;
import java.util.ArrayList;

public class BankMlp {

    public Double seeMoney(User user) {
        BaseDao dao = new BaseDao();
        dao.getConnect();
        double res = 0.;
        String sql = "SELECT* FROM bank Where uid = '" + user.getUid() + "'";
        try {
            ResultSet resultSet = dao.selectSQL(sql);
            if (resultSet.next()) {
                res = Double.parseDouble(resultSet.getString("balance"));
            } else {//不存在用户银行账户，新建账户
                String sql2 = "INSERT INTO bank VALUES('" + user.getUid() + "', '" + res + "')";
                dao.upDataSQL(sql2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.disconnect();
        return res;
    }

    public Double operateMoney(BankManage bankManage) {
        BaseDao dao = new BaseDao();
        dao.getConnect();

        String sql = "SELECT* FROM bank WHERE uid = '" + bankManage.getUid() + "'";
        double res = 0.;

        try {
            ResultSet resultSet = dao.selectSQL(sql);
            //银行数据存在该账户
            if (resultSet.next()) {
                double balance = Double.parseDouble(resultSet.getString("balance")) +
                        bankManage.getOperation() * bankManage.getAmount();
                //正常操作
                if (balance > 0) {
                    String sql2 = "UPDATE bank SET balance = '" + balance + "' WHERE uid = '" + bankManage.getUid() + "'";
                    dao.upDataSQL(sql2);
                    res = balance;
                    String sql3 = "INSERT INTO bankRecords VALUES('" + bankManage.getUid() + "', '" +
                            bankManage.getOperation() + "', '" + bankManage.getAmount() + "', '" +
                            bankManage.getRemarks() + "')";
                    dao.upDataSQL(sql3);
                } else {//支出操作，支出金额大于账户金额，备注操作失败
                    String sql2 = "INSERT INTO bankRecords VALUES('" + bankManage.getUid() + "', '" +
                            bankManage.getOperation() + "', '" + bankManage.getAmount() +
                            "', '支出金额大于账户余额，操作失败!')";
                    dao.upDataSQL(sql2);
                    res = Double.parseDouble(resultSet.getString("balance"));
                }
            } else {//银行不存在该账户，新建账户
                double balance = bankManage.getAmount() * bankManage.getOperation();
                //支入操作
                if (balance > 0) {
                    String sql2 = "INSERT INTO bank VALUES('" + bankManage.getUid() + "', '" + balance + "')";
                    dao.upDataSQL(sql2);
                    res = balance;
                    String sql3 = "INSERT INTO bankRecords VALUES('" + bankManage.getUid() + "', '" +
                            bankManage.getOperation() + "', '" + bankManage.getAmount() + "', '" +
                            bankManage.getRemarks() + "')";
                    dao.upDataSQL(sql3);
                } else {//支出操作，操作失败
                    String sql2 = "INSERT INTO bank VALUES('" + bankManage.getUid() + "', '" + res + "')";
                    dao.upDataSQL(sql2);
                    String sql3 = "INSERT INTO bankRecords VALUES('" + bankManage.getUid() + "', '" +
                            bankManage.getOperation() + "', '" + bankManage.getAmount() +
                            "', '支出金额大于账户余额，操作失败!')";
                    dao.upDataSQL(sql3);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.disconnect();

        return res;
    }

    public BankRecords seeMyBankRecord(User user) {
        ArrayList<BankManage> manages = new ArrayList<BankManage>();
        BaseDao dao = new BaseDao();
        dao.getConnect();
        String sql = "SELECT* FROM bankRecords WHERE uid = '" + user.getUid() + "'";
        try {
            ResultSet resultSet = dao.selectSQL(sql);
            while (resultSet.next()) {
                BankManage bankManage = new BankManage(resultSet.getString("uid"),
                        Integer.parseInt(resultSet.getString("operation")),
                        Double.parseDouble(resultSet.getString("amount")),
                        resultSet.getString("remarks"));
                manages.add(bankManage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dao.disconnect();
        return new BankRecords(user.getUid(), manages);
    }

    public static void main(String[] args) {
        BankMlp bm = new BankMlp();

        // System.out.println(bm.seeMoney(new User("09020237", "1", 1)));
        // System.out.println(bm.operateMoney(new BankManage("09020237", -1, 200.0, "出去van")));
        System.out.println(bm.seeMyBankRecord(new User("09020236", "1", 1)));
    }
}
