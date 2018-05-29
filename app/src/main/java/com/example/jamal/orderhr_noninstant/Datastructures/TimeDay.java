package com.example.jamal.orderhr_noninstant.Datastructures;

/**
 * Created by jamal on 5/24/2018.
 */

public class TimeDay {

    private int day;
    private int timeslot;

    public TimeDay(int day, int timeslot){
        this.day = day;
        this.timeslot = timeslot;
    }

    public int getDay() {
        return day;
    }

    public int getTimeslot() {
        return timeslot;
    }

}
