/**
 * ==================================================
 * Project: BaseDao.java
 * Package: vCampusServer.mlp
 * =====================================================
 * Title: StuMlp.java
 * Created: [2022/8/23 11:02] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/23, created by Shuxin-Wang.
 * 2.
 */

package vCampusServer.mlp;

import vCampusModel.course.Course;
import vCampusModel.course.CourseChoose;
import vCampusModel.user.User;
import vCampusServer.dao.BaseDao;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StuMlp {

    public Boolean stuAddCourse(CourseChoose courseChoose) {
        BaseDao dao = new BaseDao();
        dao.getConnect();

        for (Course course : courseChoose.getCourseList()) {
            String sql = "SELECT* FROM courseChoose WHERE uid = '" + courseChoose.getUid() + "' AND cid = '" +
                    course.getCid() + "'";
            ResultSet resultSet = dao.selectSQL(sql);
            try {
                if (!resultSet.next()) {
                    String sql2 = "INSERT INTO courseChoose VALUES('" + courseChoose.getUid() + "', '" +
                            courseChoose.getUname() + "', '" + course.getCid() + "', '" +
                            course.getCname() + "', '" + course.getClassHours() + "', '" +
                            course.getCredit() + "', '" + course.getClassTime() + "', '" +
                            course.getTid() + "', '" + course.getTname() + "')";
                    dao.upDataSQL(sql2);
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public CourseChoose stuSeeCourse(User user) {
        BaseDao dao = new BaseDao();
        dao.getConnect();

        ArrayList<Course> courses = new ArrayList<Course>();

        String sql = "SELECT* FROM courseChoose WHERE uid = '" + user.getUid() + "'";
        ResultSet resultSet = dao.selectSQL(sql);

        try {
            while (resultSet.next()) {
                Course course = new Course(
                        resultSet.getString("cid"),
                        resultSet.getString("cname"),
                        Integer.parseInt(resultSet.getString("classhours")),
                        Double.parseDouble(resultSet.getString("credit")),
                        resultSet.getString("classtime"),
                        resultSet.getString("tid"),
                        resultSet.getString("tname")
                );
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dao.disconnect();

        return new CourseChoose(user.getUid(), user.getUserName(), courses);
    }

    public static void main(String[] args) {
        StuMlp sm = new StuMlp();
        ArrayList<Course> courses = new ArrayList<Course>();
        Course course = new Course("3", "上机", 16, 6,
                "15:50-17:30", "老李", "999");
        courses.add(course);
        //System.out.println(sm.stuAddCourse(new CourseChoose("09020188", "老张", courses)));
        System.out.println(sm.stuSeeCourse(new User("09020237", "1", 1)));
    }

}
