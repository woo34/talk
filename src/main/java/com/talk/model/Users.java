package com.talk.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USERS")
public class Users {

    @Id
    @Column(name = "USER_ID", nullable = false, length = 100)
    private String userId;

    @Column(name = "USER_NAME", nullable = false, length = 100)
    private String userName;

    @Column(name = "USER_PWD", nullable = false, length = 100)
    private String userPwd;

    @Column(name = "TYPE", nullable = false, length = 100)
    private String type;

    @Temporal(TemporalType.DATE)
    @Column(name = "INSERT_DTS")
    private Date insertDts;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_DTS")
    private Date updateDts;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_LOGIN")
    private Date lastLogin;

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getInsertDts() {
        return insertDts;
    }

    public void setInsertDts(Date insertDts) {
        this.insertDts = insertDts;
    }

    public Date getUpdateDts() {
        return updateDts;
    }

    public void setUpdateDts(Date updateDts) {
        this.updateDts = updateDts;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
