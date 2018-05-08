package com.example.jamal.orderhr_noninstant.Datastructures;

import java.io.Serializable;
import java.util.Date;


public class Booking implements Serializable{

    private String room;
    private String lesson;
    private String username;
    private String timefrom;
    private String timeto;
    private Date date;
    private int timeslotto;
    private int timeslotfrom;
    private int weeknummer;

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

    public String getTimefrom() {
        return timefrom;
    }

    public void setTimefrom(String timefrom) {
        this.timefrom = timefrom;
    }

    public String getTimeto() {
        return timeto;
    }

    public void setTimeto(String timeto) {
        this.timeto = timeto;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTimeslotto() {
        return timeslotto;
    }

    public void setTimeslotto(int timeslotto) {
        this.timeslotto = timeslotto;
    }

    public int getTimeslotfrom() {
        return timeslotfrom;
    }

    public void setTimeslotfrom(int timeslotfrom) {
        this.timeslotfrom = timeslotfrom;
    }

    public int getWeeknummer() {
        return weeknummer;
    }

    public void setWeeknummer(int weeknummer) {
        this.weeknummer = weeknummer;
    }




}









