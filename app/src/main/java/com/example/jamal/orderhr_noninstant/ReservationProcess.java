package com.example.jamal.orderhr_noninstant;

import android.app.Activity;
import android.util.Log;

import com.example.jamal.orderhr_noninstant.Datastructures.AvailableSlot;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jamal on 5/28/2018.
 */

public class ReservationProcess {
    //Creates the booking object from previously gained values
    public static Booking CreateBooking(int timeslotFrom, int timeslotTo, String TimeslotFromStr, String TimeslotToStr,
                                  String room, Date date, String lesson, int week){

        Booking createdBooking = new Booking();

        //Time
        createdBooking.setTimeslotfrom(timeslotFrom);
        createdBooking.setTimeslotto(timeslotTo);
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

    public static AvailableSlot CreateAvailableSlot(int timeslotFrom, int timeslotTo, String room, Date date) {
        AvailableSlot availableSlot = new AvailableSlot();
        availableSlot.setDate(date);
        availableSlot.setRoom(room);
        availableSlot.setTimeslotfrom(timeslotFrom);
        availableSlot.setTimeslotto(timeslotTo);

        return availableSlot;
    }

    public static boolean CheckAvailability(String json, Activity context){
        IO io = IO.GetInstance();

        try {
            return io.DoPostRequestToAPIServer(json, "http://markb.pythonanywhere.com/availableslot/", context).equals("[]");
        }
        catch(Exception e){
            Log.d("CheckAvailability", "CheckAvailability: " + e.getMessage());
        }
        return false;
    }

    public static String CreateAvailabilityJson(AvailableSlot availableSlot){
        ObjectMapper obM = new ObjectMapper();
        obM.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        try {
            return obM.writeValueAsString(availableSlot);
        }
        catch (JsonProcessingException e){
            Log.d("CheckAvbJson", "CreateAvailabilityJson: Failed to parse to json ");
            return "";
        }
    }


    //TODO TEST
    //Creates the json string from the booking object
    public static String CreateBookingJson(Booking booking){
        ObjectMapper obM = new ObjectMapper();
        obM.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        try{
            return obM.writeValueAsString(booking);
        }
        catch (Exception e){
            Log.d("CreateBookingJson", e.toString());
            return "";
        }
    }

    //This function posts all bookings to the api
    public static void ParseReservations(String json, Activity context){
        IO io = IO.GetInstance();
        io.DoPostRequestToAPIServer(json, "http://markb.pythonanywhere.com/bookroom/", context);
    }


}
