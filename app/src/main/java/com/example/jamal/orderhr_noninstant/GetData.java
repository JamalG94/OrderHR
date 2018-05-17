package com.example.jamal.orderhr_noninstant;

import android.util.Pair;

import java.util.HashMap;

/**
 * Created by Robin on 5/3/2018.
 */

public final class GetData {
    static public String CheckBooking(String Json){
        String temp = "";
//        String returnf = "";
        IO instance = IO.GetInstance();
        try{
            temp = instance.execute("http://markb.pythonanywhere.com/availableslot/",Json).get();
            //temp = instance.doInBackground("http://markb.pythonanywhere.com/availableslot/",Json);
        }catch(Exception e){}finally{
//            returnf = temp;
        }

        return temp;
    }


    static public Pair<String,String> CovertTimeslotToTime(int TimeslotToConvert){
        HashMap<Integer,Pair<String,String>> HardCodedTimeSlots = new HashMap<>();
        HardCodedTimeSlots.put(1,new Pair<String, String>("08:30","09:20"));
        HardCodedTimeSlots.put(2,new Pair<String, String>("09:20","10:10"));
        HardCodedTimeSlots.put(3,new Pair<String, String>("10:30","11:20"));
        HardCodedTimeSlots.put(4,new Pair<String, String>("11:20","12:10"));
        HardCodedTimeSlots.put(5,new Pair<String, String>("12:10","13:00"));
        HardCodedTimeSlots.put(6,new Pair<String, String>("13:00","13:50"));
        HardCodedTimeSlots.put(7,new Pair<String, String>("13:50","14:40"));
        HardCodedTimeSlots.put(8,new Pair<String, String>("15:00","15:50"));
        HardCodedTimeSlots.put(9,new Pair<String, String>("15:50","16:40"));
        HardCodedTimeSlots.put(10,new Pair<String, String>("17:00","17:50"));
        HardCodedTimeSlots.put(11,new Pair<String, String>("17:50","18:40"));
        HardCodedTimeSlots.put(12,new Pair<String, String>("18:40","19:30"));
        HardCodedTimeSlots.put(13,new Pair<String, String>("19:30","20:20"));
        HardCodedTimeSlots.put(14,new Pair<String, String>("20:20","21:10"));
        HardCodedTimeSlots.put(15,new Pair<String, String>("21:10","22:00"));

        return HardCodedTimeSlots.get(TimeslotToConvert);

    }
}
