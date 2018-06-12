package com.example.jamal.orderhr_noninstant.Utility.Schedule;

import android.util.Log;
import android.util.Pair;

import java.util.HashMap;

/**
 * Created by jamal on 6/12/2018.
 */

public class TimeSlotConverter {

    private static final  HashMap<Integer,Pair<String,String>> timeSlots = new HashMap<>();

    static{
        timeSlots.put(1,new Pair<String, String>("08:30","09:20"));
        timeSlots.put(2,new Pair<String, String>("09:20","10:10"));
        timeSlots.put(3,new Pair<String, String>("10:30","11:20"));
        timeSlots.put(4,new Pair<String, String>("11:20","12:10"));
        timeSlots.put(5,new Pair<String, String>("12:10","13:00"));
        timeSlots.put(6,new Pair<String, String>("13:00","13:50"));
        timeSlots.put(7,new Pair<String, String>("13:50","14:40"));
        timeSlots.put(8,new Pair<String, String>("15:00","15:50"));
        timeSlots.put(9,new Pair<String, String>("15:50","16:40"));
        timeSlots.put(10,new Pair<String, String>("17:00","17:50"));
        timeSlots.put(11,new Pair<String, String>("17:50","18:40"));
        timeSlots.put(12,new Pair<String, String>("18:40","19:30"));
        timeSlots.put(13,new Pair<String, String>("19:30","20:20"));
        timeSlots.put(14,new Pair<String, String>("20:20","21:10"));
        timeSlots.put(15,new Pair<String, String>("21:10","22:00"));
    }


    public static Pair<String, String> TimeSlotToTimeString(int i){
        try{
            return timeSlots.get(i);
        }
        catch(IndexOutOfBoundsException exception)
        {
            Log.d("TimeSlotToString", "TimeSlotToTimeString: Tried getting a key out of bounds" );
        }
        return new Pair<>(" ", " ");
    }
}
