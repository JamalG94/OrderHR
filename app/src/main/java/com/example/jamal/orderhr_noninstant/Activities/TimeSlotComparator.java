package com.example.jamal.orderhr_noninstant.Activities;

import com.example.jamal.orderhr_noninstant.Datastructures.TimeDay;

import java.util.Comparator;

/**
 * Created by jamal on 6/6/2018.
 */


//TODO TEST
public class TimeSlotComparator implements Comparator<TimeDay> {
    @Override
    public int compare(TimeDay o1, TimeDay o2) {
        return Integer.compare(o1.getTimeslot(),(o2.getTimeslot()));
    }
}
