package com.example.jamal.orderhr_noninstant.Datastructures;

import java.io.Serializable;
import java.util.Date;


public class Booking implements Serializable{
    private String room;
    private String lesson;
    private String username;
    private Date date;
    private int timeslot_to;
    private int timeslot_from;
    private int reservation_id;
    private String time_from;
    private String time_to;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public void setTimeslotfrom(int timeslotfrom) {
        this.timeslot_from = timeslotfrom;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservationid) {
        this.reservation_id = reservation_id;
    }

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String timefrom) {
        this.time_from = timefrom;
    }

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }
}









