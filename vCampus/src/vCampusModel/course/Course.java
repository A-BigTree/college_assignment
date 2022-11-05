/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusModel.course
 * =====================================================
 * Title: Course.java
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

public class Course implements Serializable {
    private String cid;

    private String cname;

    private int classHours;

    private double credit;

    private String classTime;

    private String tname;

    private String tid;

    public Course() {

    }

    public Course(String cid, String cname, int classHours,
                  double credit, String classTime, String tname, String tid) {
        this.cid = cid;
        this.cname = cname;
        this.classHours = classHours;
        this.credit = credit;
        this.classTime = classTime;
        this.tname = tname;
        this.tid = tid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setClassHours(int classHours) {
        this.classHours = classHours;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }

    public double getCredit() {
        return credit;
    }

    public int getClassHours() {
        return classHours;
    }

    public String getClassTime() {
        return classTime;
    }

    public String getTname() {
        return tname;
    }

    public String getTid() {
        return tid;
    }

    public String toString() {
        return "cid" + cid + "\tcname" + cname + "\tname" + tname + "\ttid" + tid;
    }
}
