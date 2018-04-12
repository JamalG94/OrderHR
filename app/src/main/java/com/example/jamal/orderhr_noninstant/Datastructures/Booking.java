package com.example.jamal.orderhr_noninstant.Datastructures;

import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable{

    private int reservation_id,timeslot_to,timeslot_from;
    private String username;
    private String lesson;
    private String room;

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }

    private String time_from;
    private String time_to;
    private Date  date;
    private EnumParseStatus parseStatus = EnumParseStatus.ERROR;

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getTimeslot_to() {
        return timeslot_to;
    }

    public void setTimeslot_to(int timeslot_to) {
        this.timeslot_to = timeslot_to;
    }

    public int getTimeslot_from() {
        return timeslot_from;
    }

    public void setTimeslot_from(int timeslot_from) {
        this.timeslot_from = timeslot_from;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesoon) {
        this.lesson = lesoon;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EnumParseStatus getParseStatus() {
        return parseStatus;
    }

    public void setParseStatus(EnumParseStatus parseStatus) {
        this.parseStatus = parseStatus;
    }
}

