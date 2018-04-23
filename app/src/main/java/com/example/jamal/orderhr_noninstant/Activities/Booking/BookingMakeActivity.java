package com.example.jamal.orderhr_noninstant.Activities.Booking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.Activities.IDataStructure;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

/**
 * Created by Robin on 4/3/2018.
 */

public class BookingMakeActivity extends AppCompatActivity implements IDataStructure{
    Booking receivedBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_makebooking);
        receivedBooking = new Booking();


        TextView roomview = (TextView)findViewById(R.id.viewroomid);
        IVisit(new ObjectMapper(),extras.getString("jsonparser"));
        roomview.setText(roomview.getText() + receivedBooking.getRoom());
        TextView dateview = (TextView) findViewById(R.id.viewbookdate);
        dateview.setText(dateview.getText() + receivedBooking.getDate().toString());
    }

        @Override
        public void IVisit(ObjectMapper objectMapper, String json) {
            //json = JSON met een json array.
            //new list = json.returnjsons in json array
            //for each in list
            //    DO objectMapper.readValue(json,receivedBooking.getClass());
//df





            try{
                //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                JSONObject test = new JSONObject(json);
                String gottenres = test.getJSONObject("reservation").toString();
                receivedBooking = objectMapper.readValue(gottenres,receivedBooking.getClass());
            }
            catch(Exception e ){
                Log.d(e.toString(), e.toString());
            }
        }
}
