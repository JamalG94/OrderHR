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

import com.example.jamal.orderhr_noninstant.Activities.Main.MainActivity;
import com.example.jamal.orderhr_noninstant.Activities.Schedule.ReservationProcess;
import com.example.jamal.orderhr_noninstant.Datastructures.AvailableSlot;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.API.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.example.jamal.orderhr_noninstant.Session.Session;
import com.example.jamal.orderhr_noninstant.Utility.Schedule.TimeSlotConverter;
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

public class BookingMakeByQRJsonActivity extends AppCompatActivity {
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
    String status_stringstatus;

    boolean initial_available = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makebooking);
        textviewroom = (TextView) findViewById(R.id.viewroomid);
        textiewdate = (TextView) findViewById(R.id.viewbookdate);
        textviewtime = (TextView) findViewById(R.id.viewTimes);
        textviewtimeslots = (TextView) findViewById(R.id.viewTimeslot);
        textviewweeknumber = (TextView) findViewById(R.id.textviewWeeknumber);
        texteditclass = (EditText) findViewById(R.id.editTextClass) ;
        texteditlesson = (EditText) findViewById(R.id.editTextLesson) ;
        checkboxavaliable = (CheckBox) findViewById(R.id.Available);
        status_stringstatus = "";

        //LOAD QR FROM EXTRA BUNDLE, THEN EXTRACT ALL REQUIRED DATA FROM IT AND THE IO CALLS
        Bundle extras = getIntent().getExtras();
        try{
            modelreceivedBooking = initiateBookingDataFromJson(extras.getString("jsonparser"));
        }
        catch (IndexOutOfBoundsException|JSONException|IOException e) {
            Intent test = new Intent(this,MainActivity.class);
            Log.d(e.getClass().toString(), e.getMessage());
            if(e.getClass() == IndexOutOfBoundsException.class){
                Toast.makeText(this,"Received timeslots invalid!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Server connection error!",Toast.LENGTH_LONG).show();
            }
            startActivity(test);
        }

        //This part handles the availability, it checks it in the first 2 rows, then updates all accordingly in the rest.
        AvailableSlot slots_to_book  = getAvaibilityCheckerModelClass();
        initial_available = ReservationProcess.CheckAvailability(ReservationProcess.CreateAvailabilityJson(slots_to_book),this);
        checkboxavaliable.setChecked(initial_available);
        if(!initial_available){
            checkboxavaliable.setError("Not initial_available");
            Toast.makeText(this, "These slots are not initial_available!",
                    Toast.LENGTH_LONG).show();
            status_stringstatus = "Not initial_available";
        }

        //Sets all the textviews to the data found in the model.
        SetInitialTexts(modelreceivedBooking);
    }

    //Sets the initial values of all the appropiate text views in this activity.
    private void SetInitialTexts(com.example.jamal.orderhr_noninstant.Datastructures.Booking databooking){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        textviewroom.setText(textviewroom.getText() + databooking.getRoom());
        textiewdate.setText(textiewdate.getText() + format.format(databooking.getDate()));
        textviewweeknumber.setText("Weeknumber : " + databooking.getWeeknummer());
        textviewtime.setText(textviewtime.getText() + databooking.getTimefrom() + " to " + databooking.getTimeto());
        textviewtimeslots.setText(textviewtimeslots.getText() + String.valueOf(databooking.getTimeslotfrom()) + " to " + String.valueOf(databooking.getTimeslotto()));
    }

    //TODO
    //Handler of the onClickButtonSave.
    public void onClickSaveBooking(View view){
        //If this slot WAS available, we can proceed, else it makes no sense to continue...
        if(initial_available){
            //Check if the required fields are filled in to complete the booking...
            if((! texteditclass.getText().toString().equals("")) && (!texteditlesson.getText().toString().equals(""))){
                modelreceivedBooking.setLesson(texteditlesson.getText().toString());

                //Check the CURRENT availablity, in case someone booked this slot in the meanwhile
                AvailableSlot slots_to_book  = getAvaibilityCheckerModelClass();
                if(ReservationProcess.CheckAvailability(ReservationProcess.CreateAvailabilityJson(slots_to_book),this)){
                    status_stringstatus = saveBooking(modelreceivedBooking);

                    //Check if the booking has actually been saved.
                    if(status_stringstatus.equals("Saved your booking")){
                        Intent returntomainactivity = new Intent(this, MainActivity.class);
                        startActivity(returntomainactivity);
                    }
                }else{
                    initial_available = false;
                    checkboxavaliable.setError("!");
                    checkboxavaliable.setChecked(initial_available);
                    status_stringstatus ="Failed! Timeslots no longer available!";
                }
            }
            else{
                status_stringstatus =  "Failed! Fill in Lesson and Class fields!";
                texteditclass.setError("!");
                texteditlesson.setError("!");
            }
        }
        else{
            status_stringstatus = "Failed! Try generating on another timeslot!";
        }

        //finally,display a toast with the return status!
        Toast.makeText(this, status_stringstatus, Toast.LENGTH_LONG).show();
    }

    public AvailableSlot getAvaibilityCheckerModelClass(){
        AvailableSlot slots_to_book = new AvailableSlot();
        slots_to_book.setDate(modelreceivedBooking.getDate());
        slots_to_book.setRoom(modelreceivedBooking.getRoom());
        slots_to_book.setTimeslotfrom(modelreceivedBooking.getTimeslotfrom());
        slots_to_book.setTimeslotto(modelreceivedBooking.getTimeslotto());
        return slots_to_book;
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
    public String saveBooking(Booking databooking){
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

    //TODO UNIT TESTABLE
    //Takes the json, parses it' s data into the model we are using for this booking!
    public static Booking initiateBookingDataFromJson(String json) throws IndexOutOfBoundsException,JSONException,IOException{
        ObjectMapper objectMapper = new ObjectMapper();

        JSONObject jsonobjectparser = new JSONObject(json);
        String gottenres = jsonobjectparser.getJSONObject("reservation").toString();

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        objectMapper.setDateFormat(format);

        Booking booking_data = objectMapper.readValue(gottenres, Booking.class);
        Pair<String, String> timesofslotstart = TimeSlotConverter.TimeSlotToTimeString(booking_data.getTimeslotfrom());
        Pair<String, String> timesofslotstop = TimeSlotConverter.TimeSlotToTimeString(booking_data.getTimeslotto());
        booking_data.setTimeto(timesofslotstop.second);
        booking_data.setTimefrom(timesofslotstart.first);
        Calendar timeconverter = new GregorianCalendar();
        int weeknum = timeconverter.get(Calendar.WEEK_OF_YEAR);
        timeconverter.setTime(booking_data.getDate());
        booking_data.setWeeknummer(weeknum);

        return booking_data;
    }
}
