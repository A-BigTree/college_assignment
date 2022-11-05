/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusServer.dao
 * =====================================================
 * Title: BaseDao.java
 * Created: [2022/8/17 15:00] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/17, created by Shuxin-Wang.
 * 2.
 */

package vCampusServer.dao;

import java.sql.*;

public class BaseDao {
    public Connection connection;

    public PreparedStatement preparedStatement;

    public ResultSet resultSet;

    /**
     * 连接数据库.
     */
    public void getConnect() {
        try {
            Class.forName("com.hxtt.sql.access.AccessDriver");
            connection = DriverManager.getConnection("jdbc:Access:///src\\data\\vCampusDataBase.mdb");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接数据库.
     *
     * @param filePath 数据库路径
     */
    public void getConnect(String filePath) {
        try {
            Class.forName("com.hxtt.sql.access.AccessDriver");
            connection = DriverManager.getConnection("jdbc:Access:///" + filePath);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 执行SQL查询语句
     *
     * @param sql SQL语句
     * @return ResultSet对象
     */
    public ResultSet selectSQL(String sql) {
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * 执行SQL增删改语句
     *
     * @param sql SQL语句
     * @return 增删改行号，错误返回0
     */
    public int upDataSQL(String sql) {
        int index = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            index = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    /**
     * 关闭相关数据库
     */
    public void disconnect() {
        try {
            preparedStatement.close();
            if (resultSet != null) {
                resultSet.close();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        BaseDao dao = new BaseDao();

        dao.getConnect();

        //查询SQL语句
        String sql = "SELECT* FROM users WHERE uid = '09020237'";
        ResultSet res = dao.selectSQL(sql);
        try {
            while (res.next()) {
                System.out.print("uid:" + res.getString("uid"));
                System.out.println("\tpassword:" + res.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //增删改SQL语句
        String id = "09020345";
        String sql2 = "INSERT INTO bank VALUES('" + id + "', '200.0')";
        int i = dao.upDataSQL(sql2);
        if (i != 0) {
            System.out.println("插入成功！");
        }
        dao.disconnect();
    }
}