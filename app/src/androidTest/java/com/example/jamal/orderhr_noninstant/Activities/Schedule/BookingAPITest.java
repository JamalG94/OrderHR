package com.example.jamal.orderhr_noninstant.Activities.Schedule;

import android.support.test.runner.AndroidJUnit4;

import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.Datastructures.BookingWrapper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Created by Robin on 6/16/2018.
 */

@RunWith(AndroidJUnit4.class)
public class BookingAPITest {


    @Test   //C12.1 perfectly parsable json
    public void jsonToBookingWrapper1() throws Exception {
        final String jsonsetup = "[{\"model\": \"web.schedule\", \"pk\": 216, \"fields\": {\"timeslotfrom\": 5, \"timeslotto\": 5, \"timefrom\": \"12:10:00\", \"timeto\": \"13:00:00\", \"targetclass\": \"17-06-2018\", \"username\": \"COSTG\", \"lesson\": \"WOW2\", \"room\": \"H.3.306\", \"weeknummer\": 24, \"studentgroup\": \"INF3C\"}}]";
        Booking expectbooking1 = new Booking();
        expectbooking1.setTimeslotfrom(5);
        expectbooking1.setTimeslotto(5);
        expectbooking1.setTimefrom("12:10:00");
        expectbooking1.setTimeto("13:00:00");
        expectbooking1.setRoom("H.3.306");
        expectbooking1.setStudentgroup("INF3C");
        expectbooking1.setWeeknummer(24);
        expectbooking1.setUsername("COSTG");
        expectbooking1.setLesson("WOW2");
        Date datetoinput = new Date(118,5,17);
        expectbooking1.setDate(datetoinput);
        BookingWrapper expectedwrapper1 = new BookingWrapper();
        expectedwrapper1.setFields(expectbooking1);
        expectedwrapper1.setPk(216);

        BookingWrapper[] test = BookingAPI.JsonToBookingWrapper(jsonsetup);

        assertEquals(expectedwrapper1.getFields().getDate(),test[0].getFields().getDate());
        assertEquals(expectedwrapper1.getFields().getLesson(),test[0].getFields().getLesson());
        assertEquals(expectedwrapper1.getFields().getRoom(),test[0].getFields().getRoom());
        assertEquals(expectedwrapper1.getFields().getStudentgroup(),test[0].getFields().getStudentgroup());
        assertEquals(expectedwrapper1.getFields().getTimefrom(),test[0].getFields().getTimefrom());
        assertEquals(expectedwrapper1.getFields().getTimeto(),test[0].getFields().getTimeto());
        assertEquals(expectedwrapper1.getFields().getTimeslotfrom(),test[0].getFields().getTimeslotfrom());
        assertEquals(expectedwrapper1.getFields().getTimeslotto(),test[0].getFields().getTimeslotto());
        assertEquals(expectedwrapper1.getPk(),test[0].getPk());
    }
    @Test   //C12.2 wrong targetclass format in almost perfect json
    public void jsonToBookingWrapper2() throws Exception {
        final String jsonsetup = "[{\"sdfs\": \"web.schedule\", \"sdfd\": 216, \"sdfd\": {\"asfs\": 5, \"fsfs\": 5, \"fsfs\": \"12:10:00\", \"timeto\": \"13:00:00\", \"targetclass\": \"06-17-2018\", \"username\": \"COSTG\", \"lesson\": \"WOW2\", \"room\": \"H.3.306\", \"weeknummer\": 24, \"studentgroup\": \"INF3C\"}}]";


        BookingWrapper[] test = BookingAPI.JsonToBookingWrapper(jsonsetup);
        assertEquals(0,test.length);

    }
    @Test   //C12.3 wrong targetclass format in almost perfect json
    public void jsonToBookingWrapper3() throws Exception {

        BookingWrapper[] test = BookingAPI.JsonToBookingWrapper("hello world!");
        assertEquals(0,test.length);

    }
}