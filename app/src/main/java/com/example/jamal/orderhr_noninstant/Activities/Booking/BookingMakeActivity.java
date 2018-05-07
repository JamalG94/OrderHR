package com.example.jamal.orderhr_noninstant.Activities.Booking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.Activities.IDataStructure;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.GetData;
import com.example.jamal.orderhr_noninstant.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

/**
 * Created by Robin on 4/3/2018.
 */

public class BookingMakeActivity extends AppCompatActivity implements IDataStructure {
    Booking receivedBooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_makebooking);

        Bundle extras = getIntent().getExtras();
        receivedBooking = new Booking();
        IFillDataStructures(new ObjectMapper(), extras.getString("jsonparser"));

        TextView roomview = (TextView) findViewById(R.id.viewroomid);
        TextView dateview = (TextView) findViewById(R.id.viewbookdate);
        TextView timesview = (TextView) findViewById(R.id.viewTimes);
        TextView timeslotview = (TextView) findViewById(R.id.viewTimeslot);


        String test = GetData.checkbooking("{ \"room\":\"WN4.101\", \"timeslotfrom\":2, \"timeslotto\":3, \"date\":\"12-03-2018\" }");
        if(test.equals("\"False\"")){
            Toast.makeText(this, "These slots are not available!",
                    Toast.LENGTH_LONG).show();
            CheckBox cbAval = (CheckBox) findViewById(R.id.Available);
            cbAval.setChecked(false);
        }

        roomview.setText(roomview.getText() + receivedBooking.getRoom());
        dateview.setText(dateview.getText() + receivedBooking.getDate().toString());

        timesview.setText(timesview.getText() + receivedBooking.getTime_from() + " to " + receivedBooking.getTime_to());
        timeslotview.setText(timeslotview.getText() + String.valueOf(receivedBooking.getTimeslot_from()) + " to " + String.valueOf(receivedBooking.getTimeslot_to()));

    }

    @Override
    public void IFillDataStructures(ObjectMapper objectMapper, String json) {
        try {
            //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JSONObject test = new JSONObject(json);
            String gottenres = test.getJSONObject("reservation").toString();
            receivedBooking = objectMapper.readValue(gottenres, receivedBooking.getClass());
        } catch (Exception e) {
            Log.d(e.toString(), e.toString());
        }
    }

    public void onClickSaveBooking(View view){
        boolean status = saveBooking();
        if(!status){
            Toast.makeText(this, "Something went wrong with saving the data! (is all data correct and do you have connection?)",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onClickAddClass(View View){

        TextView addclass = (TextView) findViewById(R.id.editTextClass);
        if(addclass.getText() != ""){
            LinearLayout viewclasses = (LinearLayout) findViewById(R.id.viewClasses);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tv=new TextView(this);
            tv.setLayoutParams(lparams);
            tv.setText(addclass.getText());
            addclass.setText("");
            viewclasses.addView(tv);
        }

    }
    //Checks the database if there are similiar bookings to the received booking and returns true if not.
    public boolean checkIfAvailable() {
        return true;
    }

    //Saves the instance and filed in data to the database. Returns true if succes, false if not.
    public boolean saveBooking(){
        return false   ;
    }
}
