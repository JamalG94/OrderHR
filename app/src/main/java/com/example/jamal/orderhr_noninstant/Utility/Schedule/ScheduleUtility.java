package com.example.jamal.orderhr_noninstant.Utility.Schedule;

import android.app.Activity;
import android.util.Log;
import android.util.Pair;

import com.example.jamal.orderhr_noninstant.R;

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
    }

    public void FillTimeSlots(Activity context){
        timeSlots.put(1,  context.getResources().getString(R.string.timeslot_1));
        timeSlots.put(2,  context.getResources().getString(R.string.timeslot_2));
        timeSlots.put(3,  context.getResources().getString(R.string.timeslot_3));
        timeSlots.put(4,  context.getResources().getString(R.string.timeslot_4));
        timeSlots.put(5,  context.getResources().getString(R.string.timeslot_5));
        timeSlots.put(6,  context.getResources().getString(R.string.timeslot_6));
        timeSlots.put(7,  context.getResources().getString(R.string.timeslot_7));
        timeSlots.put(8,  context.getResources().getString(R.string.timeslot_8));
        timeSlots.put(9,  context.getResources().getString(R.string.timeslot_9));
        timeSlots.put(10, context.getResources().getString(R.string.timeslot_10));
        timeSlots.put(11, context.getResources().getString(R.string.timeslot_11));
        timeSlots.put(12, context.getResources().getString(R.string.timeslot_12));
        timeSlots.put(13, context.getResources().getString(R.string.timeslot_13));
        timeSlots.put(14, context.getResources().getString(R.string.timeslot_14));
        timeSlots.put(15, context.getResources().getString(R.string.timeslot_15));
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

    //This is an unused method ready for usage if we ever use Year as parameter for our bookings
    public static Calendar PassYear(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        cal.set(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal;
    }

    //This is an unused method ready for usage if we ever use Year as parameter for our bookings
    public static Calendar YearBack(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal;
    }

}
