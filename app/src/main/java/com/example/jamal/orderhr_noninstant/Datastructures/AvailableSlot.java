package com.example.jamal.orderhr_noninstant.Datastructures;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jamal on 6/5/2018.
 */

public class AvailableSlot implements Serializable {
    private String room;
    private int timeslotfrom;
    private int timeslotto;
    private Date date;

    public void setRoom(String room) {
        this.room = room;
    }

    public void setTimeslotfrom(int timeslotfrom) {
        this.timeslotfrom = timeslotfrom;
    }

    public void setTimeslotto(int timeslotto) {
        this.timeslotto = timeslotto;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getRoom() {
        return room;
    }

    public int getTimeslotfrom() {
        return timeslotfrom;
    }

    public int getTimeslotto() {
        return timeslotto;
    }

    public Date getDate() {
        return date;
    }
}
