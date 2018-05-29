package com.example.jamal.orderhr_noninstant;

import java.util.HashMap;

/**
 * Created by jamal on 5/28/2018.
 */

public class ScheduleUtility {

    private HashMap<Integer, String> hashMap = new HashMap<>();

    public ScheduleUtility(){
        hashMap.put(1, "8:30");
        hashMap.put(2, "9:20");
        hashMap.put(3, "10:30");
        hashMap.put(4, "11:20");
        hashMap.put(5, "12:10");
        hashMap.put(6, "13:00");
        hashMap.put(7, "13:50");
        hashMap.put(8, "15:00");
        hashMap.put(9, "15:50");
        hashMap.put(10, "17:00");

    }


    public String TimeSlotToTimeString(int i){
        return hashMap.get(i);
    }

    //public
}
