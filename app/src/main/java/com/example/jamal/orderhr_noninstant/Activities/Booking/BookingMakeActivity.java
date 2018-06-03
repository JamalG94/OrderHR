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
import com.example.jamal.orderhr_noninstant.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Robin on 4/3/2018.
 */

public class BookingMakeActivity extends AppCompatActivity implements IDataStructure {
    Booking modelreceivedBooking;
    IO IOInstance;
    EditText texteditclass;
    EditText texteditlesson;
    TextView textviewroom;
    TextView textiewdate;
    TextView textviewtime;
    TextView textviewtimeslots;
    TextView textviewweeknumber;
    CheckBox checkboxavaliable;

    boolean available = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makebooking);

        Bundle extras = getIntent().getExtras();
        modelreceivedBooking = new Booking();

        try{
            IFillDataStructures(new ObjectMapper(), extras.getString("jsonparser"));
        }catch(NullPointerException e){
            Intent test = new Intent(this,MainActivity.class);
            Toast.makeText(this,"Something went wrong with reading this QR code, as none was received by the booking activity!",Toast.LENGTH_LONG).show();
            startActivity(test);
            Log.d(e.getClass().toString(), e.getMessage());
        }

        textviewroom = (TextView) findViewById(R.id.viewroomid);
        textiewdate = (TextView) findViewById(R.id.viewbookdate);
        textviewtime = (TextView) findViewById(R.id.viewTimes);
        textviewtimeslots = (TextView) findViewById(R.id.viewTimeslot);
        textviewweeknumber = (TextView) findViewById(R.id.textviewWeeknumber);
        texteditclass = (EditText) findViewById(R.id.editTextClass) ;
        texteditlesson = (EditText) findViewById(R.id.editTextLesson) ;
        checkboxavaliable = (CheckBox) findViewById(R.id.Available);

        available = CheckIfSlotsInRoomAvailable(modelreceivedBooking, IOInstance);
        checkboxavaliable.setChecked(available);
        if(!available){
            checkboxavaliable.setError("Not available");
            Toast.makeText(this, "These slots are not available!",
                    Toast.LENGTH_LONG).show();
        }
        SetInitialTexts(modelreceivedBooking);
    }

    //Does a call to the server to get the required data on the availability of these slots, then returns compares true if available and false if not.
    private boolean CheckIfSlotsInRoomAvailable(Booking databooking, IO IOInstance) {
        IOInstance = IO.GetInstance();
        String textreturnedfromserver = "";

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String json = "{ \"room\":\"" + databooking.getRoom() + "\", \"timeslotfrom\":" + String.valueOf(databooking.getTimeslotfrom()) + ", \"timeslotto\":" + String.valueOf(databooking.getTimeslotto()) + ", \"date\":\"" + format.format(databooking.getDate()) + "\" }";
        textreturnedfromserver = IOInstance.DoPostRequestToAPIServer(json, "http://markb.pythonanywhere.com/availableslot/",this);

        //check if what server returns is json data, if not, it is an error message and we handle it as such.
        try{
            JSONObject checekr = new JSONObject(textreturnedfromserver);
        }catch(JSONException e){
            try{
                JSONArray checke = new JSONArray(textreturnedfromserver);
            }catch(JSONException x){
                Toast.makeText(this,"Server returned unreadable data!", Toast.LENGTH_LONG).show();
                Log.d("Servererror", "returned server message: "+textreturnedfromserver);
            }
        }


        return (textreturnedfromserver.equals("[]"));
    }

    //Sets the initial values of all the appropiate text views in this activity.
    private void SetInitialTexts(Booking databooking){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        textviewroom.setText(textviewroom.getText() + databooking.getRoom());
        textiewdate.setText(textiewdate.getText() + format.format(databooking.getDate()));
        textviewweeknumber.setText("Weeknumber : " + databooking.getWeeknummer());
        textviewtime.setText(textviewtime.getText() + databooking.getTimefrom() + " to " + databooking.getTimeto());
        textviewtimeslots.setText(textviewtimeslots.getText() + String.valueOf(databooking.getTimeslotfrom()) + " to " + String.valueOf(databooking.getTimeslotto()));
    }

    //Handler of the onClickButtonSave.
    public void onClickSaveBooking(View view){
        if(available){
            if((! texteditclass.getText().toString().equals("")) && (!texteditlesson.getText().toString().equals(""))){
                modelreceivedBooking.setLesson(texteditlesson.getText().toString());
                String savingstatus = saveBooking(modelreceivedBooking,IOInstance);
                Toast.makeText(this,savingstatus, Toast.LENGTH_LONG).show();
                if(savingstatus.equals("Saved your booking")){
                    Intent returntomainactivity = new Intent(this, MainActivity.class);
                    startActivity(returntomainactivity);
                }
            }
            else{
                Toast.makeText(this, "Please fill in Lesson and Class fields",
                        Toast.LENGTH_LONG).show();
                texteditclass.setError("!");
                texteditlesson.setError("!");
            }
        }
        else{
            Toast.makeText(this, "Please try generating on another timeslot",
                    Toast.LENGTH_LONG).show();
        }
    }

    //Adds a new class to the class text view
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
    public String saveBooking(Booking databooking,IO IOInstance){
        String returnmessage = "Error";

            //PRESETUP FOR THE JSON
            Calendar timeconverter = new GregorianCalendar();
            timeconverter.setTime(databooking.getDate());
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);


            //THIS LINE BUILDS THE JASON
            String rawrjson = "{ \"timeslotfrom\":\""+databooking.getTimeslotfrom()+"\", \"timeslotto\":"+databooking.getTimeslotto()+", \"timefrom\":\""+databooking.getTimefrom()+"\", \"timeto\":\""+databooking.getTimeto()+"\", \"date\":\""+format.format(databooking.getDate())+"\", \"username\":\""+ Session.getUsername()+"\", \"lesson\":\""+databooking.getLesson()+"\", \"room\":\""+databooking.getRoom()+"\", \"weeknummer\":\""+
                    databooking.getWeeknummer()+"\"}";

            //THIS LINE CALLS THE METHODS THAT DO THE ACTUAL API CALL AND RETURNING
            IOInstance = IO.GetInstance();
            returnmessage = IOInstance.DoPostRequestToAPIServer(rawrjson,"http://markb.pythonanywhere.com/bookroom/",this);

        return returnmessage;
    }

    @Override   // This function handles the loading and filling of all the initial data.
    public void IFillDataStructures(ObjectMapper objectMapper, String json) {
        try {
            JSONObject jsonobjectparser = new JSONObject(json);
            String gottenres = jsonobjectparser.getJSONObject("reservation").toString();

            DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            objectMapper.setDateFormat(format);

            modelreceivedBooking = objectMapper.readValue(gottenres, modelreceivedBooking.getClass());
            Pair<String, String> timesofslotstart = GetData.CovertTimeslotToTime(modelreceivedBooking.getTimeslotfrom());
            Pair<String, String> timesofslotstop = GetData.CovertTimeslotToTime(modelreceivedBooking.getTimeslotto());
            modelreceivedBooking.setTimeto(timesofslotstop.second);
            modelreceivedBooking.setTimefrom(timesofslotstart.first);
            Calendar timeconverter = new GregorianCalendar();
            timeconverter.setTime(modelreceivedBooking.getDate());
            int weeknum = timeconverter.get(Calendar.WEEK_OF_YEAR);
            modelreceivedBooking.setWeeknummer(weeknum);
        } catch (IndexOutOfBoundsException|JSONException|IOException e) {
            Intent test = new Intent(this,MainActivity.class);
            Toast error;
            Log.d(e.getClass().toString(), e.getMessage());
            if(e.getClass() == IndexOutOfBoundsException.class){
                Toast.makeText(this,"Received timeslots invalid!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Server connection error!",Toast.LENGTH_LONG).show();
            }
            startActivity(test);
        }

    }
}
