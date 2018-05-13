package com.example.jamal.orderhr_noninstant.Datastructures;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Robin on 4/5/2018.
 */

public class Defunct implements Serializable {
    private int defunctid;
    private String room,type,description;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDefunctid() {
        return defunctid;
    }

    public void setDefunctid(int defunctid) {
        this.defunctid = defunctid;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
