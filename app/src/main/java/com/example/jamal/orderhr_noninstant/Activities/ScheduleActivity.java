package com.example.jamal.orderhr_noninstant.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.IO;
import com.example.jamal.orderhr_noninstant.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jamal on 3/19/2018.
 */

public class ScheduleActivity extends AppCompatActivity {

    private List<Booking> selectedBookings = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

    }

    @Override
    protected void onStart(){
        super.onStart();

        Booking booking = IO.Getbookingbyid(1);
        FillRows(booking);
    }

    private void onClickReserve(){
        JSONObject[] jsonObjects = new JSONObject[selectedBookings.size()];
        for(Booking b : selectedBookings){
            JSONObject jsonObject = b.ObjecttoJson();
        }
        ParseReservations(jsonObjects);

    }

    private void ParseReservations(JSONObject[] jsonObjects){
        //TODO magic jsonobjects to database away
    }

    private void MarkBookings(Booking booking){
        if(selectedBookings.contains(booking)){
            selectedBookings.remove(booking);
        }
        else {
            selectedBookings.add(booking);
        }
    }

    private int DatetoColumn(Date date){
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        int dayOfWeek = calender.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    private void FillRows(Booking booking){
        String tablerow_id;
        String lesson = booking.Lesson;
        String teacher = booking.Username;
        //int day = DatetoColumn(booking.DateOn);
        int timeslotfrom = 3;
        int timeslotto = 4;

        TableRow tablerow;
        TextView timeslot_lesson;

        //Create a listener that does something unique with each cell
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Booking booking = (Booking)(v.getTag());
                Log.d(booking.Lesson, booking.Lesson);
                MarkBookings(booking);
            }
        };

        for(int i = timeslotfrom; i <= timeslotto; i++ ){
            //Create a textview object to hold the lesson and teacher strings
            timeslot_lesson = new TextView(this);
            timeslot_lesson.setText(teacher + lesson);
            //Put the booking object in our specific cell;
            timeslot_lesson.setTag(booking);
            timeslot_lesson.setOnClickListener(listener);

            //Find the appropiate timeslot of the reservation
            tablerow_id = "timeslot_" + i;
            tablerow = findViewById(getResources().getIdentifier(tablerow_id, "id", getPackageName()));
            tablerow.addView(timeslot_lesson);

            //Create a new TableRow.LayoutParams so we can set the reservation at the right day through help of DatetoColumn
            TableRow.LayoutParams tbr = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            tbr.column = 3;//day;
            timeslot_lesson.setLayoutParams(tbr);
        }
    }

}
