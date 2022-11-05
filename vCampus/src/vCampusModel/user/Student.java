/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusModel.user
 * =====================================================
 * Title: Student.java
 * Created: [2022/8/13 18:37] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/13, created by Shuxin-Wang.
 * 2.
 */

package vCampusModel.user;

public class Student extends User {
    private String major;
    private String grade;
    private String sClass;

    public Student() {

    }

    public Student(String uid, String password, String userName, int age, boolean sex, String collage,
                   String email, String phoneNumber, String address, String major,
                   String grade, String sClass) {
        super(uid, password, userName, age, sex, collage, email, phoneNumber, address, 1);
        this.major = major;
        this.grade = grade;
        this.sClass = sClass;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getMajor() {
        return major;
    }

    public String getGrade() {
        return grade;
    }

    public String getsClass() {
        return sClass;
    }

    public String toString() {
        return super.toString() + "\tmajor:" + major + "\tgrade:" + grade + "\tclass:" + grade;
    }
}
