package com.example.jamal.orderhr_noninstant.Activities.Booking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.Activities.IDataStructure;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

/**
 * Created by Robin on 4/3/2018.
 */

public class BookingMakeActivity extends AppCompatActivity implements IDataStructure{
    Booking receivedBooking;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        Bundle extras = getIntent().getExtras();
//        setContentView(R.layout.activity_makebooking);
//        receivedBooking = new Booking();
//
//        try{
//            String jsonstring = extras.getString("jsonparser");
//            receivedBooking.parsedata(new JSONObject(jsonstring));
//        }catch(Exception e){}finally{
//            TextView roomview = (TextView)findViewById(R.id.viewroomid);
//            roomview.setText(roomview.getText() + receivedBooking.Room);
//        }
//
//        IVisit(new ObjectMapper(),extras.getString("jsonparser"));
//    }

        @Override
        public void IVisit(ObjectMapper objectMapper, String json) {

            try{
                receivedBooking = objectMapper.readValue(json,receivedBooking.getClass());
            }
            catch(Exception e ){
                Log.d(e.toString(), e.toString());
            }
        }
}
