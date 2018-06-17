package com.example.jamal.orderhr_noninstant.Activities.Schedule;

import android.app.Activity;
import android.util.Log;

import com.example.jamal.orderhr_noninstant.API.IO;
import com.example.jamal.orderhr_noninstant.Datastructures.BookingWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by jamal on 6/11/2018.
 */

public class BookingAPI {

    public static String GetBookingsAtRoom(String room, int currentWeek, Activity context){
        IO _IO = IO.GetInstance();
        String weekSchedule = _IO.DoPostRequestToAPIServer(String.format("{\"room\":\"%s\",  \"weeknummer\":\"%s\"}", room, currentWeek),
                "http://markb.pythonanywhere.com/bookingbyroom/", context);
        return weekSchedule;
    }

    //test case C12
    public static BookingWrapper[] JsonToBookingWrapper(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            //Set the objectmapper's date format in the try otherwise it doesn't register
            objectMapper.setDateFormat(format);
            return objectMapper.readValue(json, BookingWrapper[].class);
        } catch(IOException exception){
            Log.d("BookingWrapper", "JsonToBookingWrapper: " + exception.getMessage());
        }
        return new BookingWrapper[]{};
    }
}
