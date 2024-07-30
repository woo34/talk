package com.talk.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MESSAGES")
public class Messages {

    @Id
    @Column(name = "MSG_ID", nullable = false, length = 10)
    private Integer msgId;

    @Column(name = "ROOM_ID", nullable = false, length = 10)
    private Integer roomId;

    @Column(name = "USER_ID", nullable = false, length = 100)
    private String userId;

    @Column(name = "CONTEXT", nullable = false, length = 200)
    private String context;

    @Column(name = "SESSION", nullable = false, length = 200)
    private String session;

    @Temporal(TemporalType.DATE)
    @Column(name = "INSERT_DTS")
    private Date insertDts;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Date getInsertDts() {
        return insertDts;
    }

    public void setInsertDts(Date insertDts) {
        this.insertDts = insertDts;
    }

}
