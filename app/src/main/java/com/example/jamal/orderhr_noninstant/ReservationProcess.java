package com.example.jamal.orderhr_noninstant;

import android.app.Activity;
import android.util.Log;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jamal on 5/28/2018.
 */

public class ReservationProcess {
    //Creates the booking object from previously gained values
    public static Booking CreateBooking(int TimeslotFrom, int TimeslotTo, String TimeslotFromStr, String TimeslotToStr,
                                  String room, Date date, String lesson, int week){

        Booking createdBooking = new Booking();

        //Time
        createdBooking.setTimeslotfrom(TimeslotFrom);
        createdBooking.setTimeslotto(TimeslotTo);
        createdBooking.setTimefrom(TimeslotFromStr);
        createdBooking.setTimeto(TimeslotToStr);
        //User,Room,Lesson
        createdBooking.setUsername(Session.getUsername());
        createdBooking.setRoom(room);
        createdBooking.setLesson(lesson);
        //Date
        createdBooking.setDate(date);
        createdBooking.setWeeknummer(week);

        return createdBooking;
    }


    //TODO TEST
    //Creates the json string from the booking object
    public static String CreateBookingJson(Booking booking, ObjectMapper objectMapper){
        objectMapper.setDateFormat(new SimpleDateFormat("dd-mm-yyyy"));
        try{
            return objectMapper.writeValueAsString(booking);
        }
        catch (Exception e){
            Log.d("CreateBookingJson", e.toString());
            return "";
        }
    }

    //This function posts all bookings to the api
    public static void ParseReservations(String jsonObject, Activity context){
        IO io = IO.GetInstance();
        io.DoPostRequestToAPIServer(jsonObject, "http://markb.pythonanywhere.com/bookroom/", context);
    }

}
