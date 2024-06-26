package com.talk.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CHAT_ROOM")
public class ChatRoom {

    @Id
    @Column(name = "ROOM_ID", nullable = false, length = 10)
    private Integer roodId;

    @Column(name = "ROOM_NAME", nullable = false, length = 100)
    private String roomName;

    @Temporal(TemporalType.DATE)
    @Column(name = "INSERT_DTS")
    private Date insertDts;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Date getInsertDts() {
        return insertDts;
    }

    public void setInsertDts(Date insertDts) {
        insertDts = insertDts;
    }

    public int getRoodId() {
        return roodId;
    }

    public void setRoodId(int roodId) {
        this.roodId = roodId;
    }
}
