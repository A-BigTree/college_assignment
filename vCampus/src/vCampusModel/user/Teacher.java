/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusModel.user
 * =====================================================
 * Title: Teacher.java
 * Created: [2022/8/13 18:38] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/13, created by Shuxin-Wang.
 * 2.
 */

package vCampusModel.user;

public class Teacher extends User {
    private String position;

    public Teacher() {

    }

    public Teacher(String uid, String password, String userName, int age, String collage, boolean sex,
                   String email, String phoneNumber, String address, String position) {
        super(uid, password, userName, age, sex, collage, email, phoneNumber, address, 2);
        this.position = position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public String toString() {
        return super.toString() + "\tposition:" + position;
    }
}
