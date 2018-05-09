package com.example.jamal.orderhr_noninstant.Activities.Booking;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.Activities.IDataStructure;
import com.example.jamal.orderhr_noninstant.Activities.MainActivity;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.GetData;
import com.example.jamal.orderhr_noninstant.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Robin on 4/3/2018.
 */

public class BookingMakeActivity extends AppCompatActivity implements IDataStructure {
    Booking receivedBooking;
    IO IOInstance;
    EditText classedit;
    EditText lessonedit;
    boolean avaliable= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_makebooking);

        Bundle extras = getIntent().getExtras();
        receivedBooking = new Booking();
        IFillDataStructures(new ObjectMapper(), extras.getString("jsonparser"));

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        TextView roomview = (TextView) findViewById(R.id.viewroomid);
        TextView dateview = (TextView) findViewById(R.id.viewbookdate);
        TextView timesview = (TextView) findViewById(R.id.viewTimes);
        TextView timeslotview = (TextView) findViewById(R.id.viewTimeslot);
        classedit = (EditText) findViewById(R.id.editTextClass) ;
        lessonedit = (EditText) findViewById(R.id.editTextLesson) ;

        IOInstance =  IO.GetInstance("");
        String test = IOInstance.DoPostRequestToAPIServer("{ \"room\":\""+receivedBooking.getRoom()+"\", \"timeslotfrom\":"+ String.valueOf(receivedBooking.getTimeslotfrom()) + ", \"timeslotto\":"+String.valueOf(receivedBooking.getTimeslotto())+", \"date\":\""+format.format(receivedBooking.getDate())+"\" }","http://markb.pythonanywhere.com/availableslot/");
        CheckBox cbAval = (CheckBox) findViewById(R.id.Available);
        if( test.equals("[]"))
        {
            avaliable = true;
            cbAval.setChecked(true);
        }
        else{
            Toast.makeText(this, "These slots are not available!",
                Toast.LENGTH_LONG).show();
            cbAval.setError("NOT AVAILABLE");
            cbAval.setChecked(false);
        }




        roomview.setText(roomview.getText() + receivedBooking.getRoom());
        dateview.setText(dateview.getText() + format.format(receivedBooking.getDate()));

        timesview.setText(timesview.getText() + receivedBooking.getTimefrom() + " to " + receivedBooking.getTimeto());
        timeslotview.setText(timeslotview.getText() + String.valueOf(receivedBooking.getTimeslotfrom()) + " to " + String.valueOf(receivedBooking.getTimeslotto()));

    }

    @Override
    public void IFillDataStructures(ObjectMapper objectMapper, String json) {
        try {
            JSONObject test = new JSONObject(json);
            String gottenres = test.getJSONObject("reservation").toString();
            receivedBooking = objectMapper.readValue(gottenres, receivedBooking.getClass());
            Pair<String, String> timesofslotstart = GetData.CovertTimeslotToTime(receivedBooking.getTimeslotfrom());
            Pair<String, String> timesofslotstop = GetData.CovertTimeslotToTime(receivedBooking.getTimeslotto());
            receivedBooking.setTimeto(timesofslotstop.second);
            receivedBooking.setTimefrom(timesofslotstart.first);
            Calendar timeconverter = new GregorianCalendar();
            timeconverter.setTime(receivedBooking.getDate());
            int weeknum = timeconverter.get(Calendar.WEEK_OF_YEAR);
            receivedBooking.setWeeknummer(weeknum);
        } catch (Exception e) {
            Log.d(e.toString(), e.toString());
        }
    }

    //Handler of the onClickButtonSave.
    public void onClickSaveBooking(View view){
        if(avaliable){
            if((! classedit.getText().toString().equals("")) && (!lessonedit.getText().toString().equals(""))){
                String status = saveBooking();
                if(status.equals("Error")){
                    Toast.makeText(this, "Something went wrong with saving the data! (is all data correct and do you have connection?)",
                            Toast.LENGTH_LONG).show();
                }else{
                    Intent dostuff = new Intent(this, MainActivity.class);
                    startActivity(dostuff);
                }
            }
            else{
                Toast.makeText(this, "Please fill in Lesson and Class fields",
                        Toast.LENGTH_LONG).show();
                classedit.setError("!");
                lessonedit.setError("!");
            }
        }
        else{
            Toast.makeText(this, "Please try generating on another timeslot",
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

    //Saves the instance and filed in data to the database. Returns the return text.
    public String saveBooking(){
        String returnmessage = "Error";

        try{
            //PRESETUP FOR THE JSON
            Calendar timeconverter = new GregorianCalendar();
            timeconverter.setTime(receivedBooking.getDate());
            DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);


            //THIS LINE BUILDS THE JASON
            String rawrjson = "{ \"timeslotfrom\":\""+receivedBooking.getTimeslotfrom()+"\", \"timeslotto\":"+receivedBooking.getTimeslotto()+", \"timefrom\":\""+receivedBooking.getTimefrom()+"\", \"timeto\":\""+receivedBooking.getTimeto()+"\", \"date\":\""+format.format(receivedBooking.getDate())+"\", \"username\":\""+"RONALDO"+"\", \"lesson\":\""+lessonedit.getText()+"\", \"room\":\""+receivedBooking.getRoom()+"\", \"weeknummer\":\""+
                    receivedBooking.getWeeknummer()+"\"}";

            //THIS LINE CALLS THE METHODS THAT DO THE ACTUAL API STUFF
            returnmessage = IOInstance.DoPostRequestToAPIServer(rawrjson,"http://markb.pythonanywhere.com/bookroom/");
        }catch (Exception ex ){
            System.out.println(ex);

        }



        return returnmessage;
    }
}
