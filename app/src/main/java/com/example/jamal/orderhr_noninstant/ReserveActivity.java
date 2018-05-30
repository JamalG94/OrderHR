package com.example.jamal.orderhr_noninstant;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;

/**
 * Created by jamal on 5/28/2018.
 */

public class ReserveActivity extends Activity {
    //Data parsing necessities
    private IO _IO;
    private ObjectMapper objectMapper;

    //Android Views
    private CalendarView simpleCalendarView;
    private EditText editText;

    //Booking data
    private String currentRoom;
    private int TimeslotFrom;
    private int TimeslotTo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reserve);
        objectMapper = new ObjectMapper();

        //Set the date to the current time so the user can plan for the future
        simpleCalendarView = findViewById(R.id.simpleCalendarView);
        simpleCalendarView.setDate(new Date().getTime());

        //Get all extras passed from ScheduleActivity
        Bundle bundle = getIntent().getExtras();
        currentRoom = bundle.getString("currentRoom");
        TimeslotFrom = bundle.getInt("TimeslotFrom");
        TimeslotTo = bundle.getInt("TimeslotTo");

        _IO = IO.GetInstance();
        editText = findViewById(R.id.lesson);
    }

    public void OnClickBooking(View view){
        ParseReservations(CreateBookingJson(CreateBooking()));
    }


    //Creates the booking object from previously gained values
    private Booking CreateBooking(){
        Booking createdBooking = new Booking();
        ScheduleUtility scheduleUtility = new ScheduleUtility();

        //Time
        createdBooking.setTimeslotfrom(TimeslotFrom);
        createdBooking.setTimeslotto(TimeslotTo);
        createdBooking.setTimefrom(scheduleUtility.TimeSlotToTimeString(TimeslotFrom));
        createdBooking.setTimeto(scheduleUtility.TimeSlotToTimeString(TimeslotTo));
        //User,Room,Lesson
        createdBooking.setUsername(Session.getUsername());
        createdBooking.setRoom(currentRoom);
        createdBooking.setLesson(editText.getText().toString());
        //Date
        createdBooking.setDate(new Date(simpleCalendarView.getDate()));
        createdBooking.setWeeknummer(1);

        return createdBooking;
    }


    //Creates the json string from the booking object
    private String CreateBookingJson(Booking booking){
        try{
            return objectMapper.writeValueAsString(booking);
        }
        catch (Exception e){
            Log.d("CreateBookingJson", e.toString());
            return "";
        }
    }

    //This function posts all bookings to the api
    private void ParseReservations(String jsonObject){
        _IO = IO.GetInstance();
        _IO.DoPostRequestToAPIServer(jsonObject, "http://markb.pythonanywhere.com/bookroom/", this);
    }

}
