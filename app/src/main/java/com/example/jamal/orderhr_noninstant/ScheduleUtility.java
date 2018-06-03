package com.example.jamal.orderhr_noninstant;

import android.util.Log;
import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jamal on 5/28/2018.
 */

public class ScheduleUtility {

    private HashMap<Integer, String> timeSlots = new HashMap<>();
    private HashMap<Integer, Date> dateDays = new HashMap<>();

    public ScheduleUtility(){
        timeSlots.put(1, "8:30");
        timeSlots.put(2, "9:20");
        timeSlots.put(3, "10:30");
        timeSlots.put(4, "11:20");
        timeSlots.put(5, "12:10");
        timeSlots.put(6, "13:00");
        timeSlots.put(7, "13:50");
        timeSlots.put(8, "15:00");
        timeSlots.put(9, "15:50");
        timeSlots.put(10, "17:00");
    }


    public String TimeSlotToTimeString(int i){
        return timeSlots.get(i);
    }

    public void PutDate(int day, Date date){
        dateDays.put(day, date);
    }

    public Date DayToDate(int i){
        return dateDays.get(i);
    }

    public static int GetWeek(){
        Calendar cal = Calendar.getInstance();
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    public static Calendar GetCalendarSetAtWeek(int week){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal;
    }

    public void AddDatesToHashMap(Calendar cal, int daysToAdd){
        for(int i = 1; i <= daysToAdd; i++){
            this.PutDate(i, cal.getTime());
            cal.add(Calendar.DATE, 1);

        }
    }

    public void PrintDates(){
        for (Map.Entry<Integer, Date> entry : dateDays.entrySet()) {
            Log.d("Day", "PrintDates: " + entry.getKey().toString());
            Log.d("Date", "PrintDates: " + entry.getValue().toString());
            // ...
        }
    }
}
