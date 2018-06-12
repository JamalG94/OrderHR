package com.example.jamal.orderhr_noninstant.Utility.Schedule;

import android.app.Activity;
import android.util.Log;
import android.util.Pair;

import com.example.jamal.orderhr_noninstant.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jamal on 5/28/2018.
 */

public class WeekDate {

    private HashMap<Integer, Date> dateDays = new HashMap<>();
    private Calendar cal;

    public WeekDate(){
    }

    private void PutDate(int day, Date date){
        dateDays.put(day, date);
    }

    public Date DayToDate(int i){
        return dateDays.get(i);
    }


    //Used to retrieve the current week
    public int GetWeek(){
        cal = Calendar.getInstance();
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    public int GetYear(){
        int year = cal.get(Calendar.YEAR);
        Log.d(" ", "GetYear:" + year);
        return year;
    }

    //Used to compare
    public Boolean CompareYears(Date date){
        //We substract 1900 because the java date class substracts 1900 from year integer.
        return (cal.get(Calendar.YEAR)- 1900) == (date.getYear());
    }

    //
    public Calendar GetCalendarSetAtWeek(int week){
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal;
    }

    public void AddDatesToHashMap(Calendar cal, int daysToAdd){
        for(int i = 1; i <= daysToAdd; i++){
            this.PutDate(i, cal.getTime());
            cal.add(Calendar.DATE, 1);
        }
        PrintDates();
    }

    //This is an unused method ready for usage if we ever use Year as parameter for our bookings
    public void PassYear(){
        cal.add(Calendar.YEAR, 1);
    }

    //This is an unused method ready for usage if we ever use Year as parameter for our bookings
    public void YearBack(){
        cal.add(Calendar.YEAR, -1);

    }

    private void PrintDates(){
        for (Map.Entry<Integer, Date> entry : dateDays.entrySet()) {
            Log.d("Day", "PrintDates: " + entry.getKey().toString());
            Log.d("Date", "PrintDates: " + entry.getValue().toString());
        }
    }

}
