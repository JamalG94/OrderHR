package com.example.jamal.orderhr_noninstant.Activities.Booking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.R;

import org.json.JSONObject;

/**
 * Created by Robin on 4/3/2018.
 */

public class BookingMakeActivity extends AppCompatActivity{
    Booking bookingstruc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_makebooking);
        bookingstruc = new Booking();

        try{
            String jsonstring = extras.getString("jsonparser");
            bookingstruc.parsedata(new JSONObject(jsonstring));
        }catch(Exception e){}finally{
            TextView roomview = (TextView)findViewById(R.id.viewroomid);
            roomview.setText(roomview.getText() + bookingstruc.Room);

            TextView dateview = (TextView)findViewById(R.id.viewbookdate);
            dateview.setText(dateview.getText() + bookingstruc.DateOn.toString());

            TextView timeslots = (TextView)findViewById(R.id.viewTimeslot);
            timeslots.setText(timeslots.getText() + " From  :" + bookingstruc.timeslotfrom + " -> to -> " + bookingstruc.timeslotto);

            TextView times = (TextView)findViewById(R.id.viewTimes);
            times.setText(times.getText() + " From  :" + bookingstruc.TimeFrom + " -> to -> " + bookingstruc.TimeTo);
        }


    }




}
