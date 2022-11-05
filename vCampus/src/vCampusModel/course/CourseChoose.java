/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusModel.course
 * =====================================================
 * Title: CourseChoose.java
 * Created: [2022/8/18 16:23] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/18, created by Shuxin-Wang.
 * 2.
 */

package vCampusModel.course;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseChoose implements Serializable {

    private String uid;

    private String uname;

    private ArrayList<Course> courseList;

    public CourseChoose() {

    }

    public CourseChoose(String uid, String uname, ArrayList<Course> courseList) {
        this.uid = uid;
        this.uname = uname;
        this.courseList = courseList;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUid() {
        return uid;
    }

    public String getUname() {
        return uname;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public String toString() {
        StringBuilder list = new StringBuilder();
        for (Course course : courseList) {
            list.append(course.toString()).append("\n");
        }
        return "uid:" + uid + "\tuname" + uname + "\n" + list.toString();
    }
}
