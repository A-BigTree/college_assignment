/**
 * ==================================================
 * Project: vCampus
 * Package: vCampusModel.user
 * =====================================================
 * Title: User.java
 * Created: [2022/8/13 18:35] by Shuxin-Wang
 * =====================================================
 * Description: description here
 * =====================================================
 * Revised History:
 * 1. 2022/8/13, created by Shuxin-Wang.
 * 2.
 */

package vCampusModel.user;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Serializable {
    //登陆ID
    private String uid;
    //登陆密码
    private String password;
    //用户姓名
    private String userName;
    //年龄
    private int age;
    //性别，ture->男，false->女
    private boolean sex;

    private String collage;
    //邮箱
    private String email;
    //电话号码
    private String phoneNumber;
    //住址
    private String address;
    //用户身份
    private int identity;

    public User() {

    }

    public User(String uid, String password, int identity) {
        this.uid = uid;
        this.password = password;
        this.identity = identity;
    }

    public User(String uid, String password, String userName, int age, boolean sex, String collage,
                String email, String phoneNumber, String address, int identity) {
        this.uid = uid;
        this.password = password;
        this.userName = userName;
        this.age = age;
        this.sex = sex;
        this.collage = collage;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.identity = identity;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getUid() {
        return uid;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public boolean getSex() {
        return sex;
    }

    public int getIdentity() {
        return identity;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public String getCollage() {
        return collage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uid, user.uid) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, password);
    }

    @Override
    public String toString() {
        return "uid:" + uid + "\tuserName" + userName + "\tidentity:" + identity;
    }
}

