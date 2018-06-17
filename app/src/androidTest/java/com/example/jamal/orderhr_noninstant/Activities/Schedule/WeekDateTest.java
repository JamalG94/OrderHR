package com.example.jamal.orderhr_noninstant.Activities.Schedule;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Robin on 6/16/2018.
 */
@RunWith(AndroidJUnit4.class)
public class WeekDateTest {
    WeekDate targetclass;

    @Before
    public void setUp() throws Exception {
        targetclass = new WeekDate();
        targetclass.GetWeek();
    }
    @Test //C15.1
    public void AddDatesToHashMap1()throws Exception{
        Calendar inputcalender = Calendar.getInstance();
        inputcalender.set(2018,7,6,0,0,0);

        targetclass.AddDatesToHashMap(inputcalender,5);
        Date date5 = new Date(118,7,10);
        assertEquals(date5.getDay(),targetclass.DayToDate(5).getDay());
        assertEquals(date5.getYear(),targetclass.DayToDate(5).getYear());
    }
    @Test //C15.2
    public void AddDatesToHashMap2()throws Exception{
        Calendar inputcalender = Calendar.getInstance();
        inputcalender.set(2018,7,30,0,0,0);

        targetclass.AddDatesToHashMap(inputcalender,5);
        Date date5 = new Date(118,8,3);
        assertEquals(date5.getDay(),targetclass.DayToDate(5).getDay());
        assertEquals(date5.getYear(),targetclass.DayToDate(5).getYear());
    }
    @Test //C15.3
    public void AddDatesToHashMap3()throws Exception{
        Calendar inputcalender = Calendar.getInstance();
        inputcalender.set(2018,11,31,0,0,0);

        targetclass.AddDatesToHashMap(inputcalender,5);
        Date date5 = new Date(119,00,04);
        assertEquals(date5.getDay(),targetclass.DayToDate(5).getDay());
        assertEquals(date5.getYear(),targetclass.DayToDate(5).getYear());
    }
}