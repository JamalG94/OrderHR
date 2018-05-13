package com.example.jamal.orderhr_noninstant.Activities.Booking;

import android.content.Intent;
import android.os.Bundle;
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
    TextView roomview;
    TextView dateview;
    TextView timesview;
    TextView timeslotview;
    CheckBox cbAval;

    boolean available = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makebooking);

        Bundle extras = getIntent().getExtras();
        receivedBooking = new Booking();
        IFillDataStructures(new ObjectMapper(), extras.getString("jsonparser"));


        AssignTextViewVariables();

        available = CheckIfSlotsInRoomAvailable(receivedBooking);
        cbAval.setChecked(available);
        if(!available){
            cbAval.setError("Not available");
            Toast.makeText(this, "These slots are not available!",
                    Toast.LENGTH_LONG).show();
        }

        SetInitialTexts(receivedBooking,roomview,dateview,timesview,timeslotview);
    }

    //Binds each local variables with their appropiate views.
    private void AssignTextViewVariables(){
        roomview = (TextView) findViewById(R.id.viewroomid);
        dateview = (TextView) findViewById(R.id.viewbookdate);
        timesview = (TextView) findViewById(R.id.viewTimes);
        timeslotview = (TextView) findViewById(R.id.viewTimeslot);
        classedit = (EditText) findViewById(R.id.editTextClass) ;
        lessonedit = (EditText) findViewById(R.id.editTextLesson) ;
        cbAval = (CheckBox) findViewById(R.id.Available);
    }

    //Does a call to the server to get the required data on the availability of these slots, then returns compares true if available and false if not.
    private boolean CheckIfSlotsInRoomAvailable(Booking databooking){
        IOInstance =  IO.GetInstance("");

        DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        String textreturnedfromserver = IOInstance.DoPostRequestToAPIServer("{ \"room\":\""+databooking.getRoom()+"\", \"timeslotfrom\":"+ String.valueOf(databooking.getTimeslotfrom()) + ", \"timeslotto\":"+String.valueOf(databooking.getTimeslotto())+", \"date\":\""+format.format(databooking.getDate())+"\" }","http://markb.pythonanywhere.com/availableslot/");

        return ( textreturnedfromserver.equals("[]"));
    }

    private void SetInitialTexts(Booking databooking,TextView targetroomview,TextView targetdateview,TextView targettimesview,TextView targettimeslotview){
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        targetroomview.setText(targetroomview.getText() + databooking.getRoom());
        targetdateview.setText(targetdateview.getText() + format.format(databooking.getDate()));

        targettimesview.setText(targettimesview.getText() + databooking.getTimefrom() + " to " + databooking.getTimeto());
        targettimeslotview.setText(targettimeslotview.getText() + String.valueOf(databooking.getTimeslotfrom()) + " to " + String.valueOf(databooking.getTimeslotto()));
    }

    //Handler of the onClickButtonSave.
    public void onClickSaveBooking(View view){
        if(available){
            if((! classedit.getText().toString().equals("")) && (!lessonedit.getText().toString().equals(""))){
                receivedBooking.setLesson(lessonedit.getText().toString());
                String savingstatus = saveBooking(receivedBooking);
                if(savingstatus.equals("Error")){
                    Toast.makeText(this, "Something went wrong with saving the data! (is all data correct and do you have connection?)",
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Booking saved Succefully!",
                            Toast.LENGTH_LONG).show();
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

    //Saves the instance and filed in data to the database. Returns the return text.
    public String saveBooking(Booking databooking ){
        String returnmessage = "Error";

        try{
            //PRESETUP FOR THE JSON
            Calendar timeconverter = new GregorianCalendar();
            timeconverter.setTime(databooking.getDate());
            DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);


            //THIS LINE BUILDS THE JASON
            String rawrjson = "{ \"timeslotfrom\":\""+databooking.getTimeslotfrom()+"\", \"timeslotto\":"+databooking.getTimeslotto()+", \"timefrom\":\""+databooking.getTimefrom()+"\", \"timeto\":\""+databooking.getTimeto()+"\", \"date\":\""+format.format(databooking.getDate())+"\", \"username\":\""+"RONALDO"+"\", \"lesson\":\""+databooking.getLesson()+"\", \"room\":\""+databooking.getRoom()+"\", \"weeknummer\":\""+
                    databooking.getWeeknummer()+"\"}";

            //THIS LINE CALLS THE METHODS THAT DO THE ACTUAL API STUFF
            IOInstance = IO.GetInstance("");
            returnmessage = IOInstance.DoPostRequestToAPIServer(rawrjson,"http://markb.pythonanywhere.com/bookroom/");
        }catch (Exception ex ){
            System.out.println(ex);

        }
        return returnmessage;
    }

    @Override
    public void IFillDataStructures(ObjectMapper objectMapper, String json) {
        try {
            JSONObject test = new JSONObject(json);
            String gottenres = test.getJSONObject("reservation").toString();

            DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            objectMapper.setDateFormat(format);

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
}
