package com.example.jamal.orderhr_noninstant.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.BuildingRadioButton;
import com.example.jamal.orderhr_noninstant.ClassroomSpinner;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.Datastructures.BookingWrapper;
import com.example.jamal.orderhr_noninstant.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.example.jamal.orderhr_noninstant.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamal on 3/19/2018.
 */

public class ScheduleActivity extends TableBuilder implements IDataStructure {

    private List<Booking> selectedBookings = new ArrayList<>();
    private List<Booking> allBookings;
    private BookingWrapper[] bookingWrapper;
    private ObjectMapper objectMapper;

    private IO _IO;
    private ClassroomSpinner classroomSpinner;
    private BuildingRadioButton buildingRadioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schedule);
        View.OnClickListener onSelectCell = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag() != null){
                Booking booking = (Booking) (v.getTag());
                MarkBookings(booking);
                }
            }
        };

        this.setOnSelectCell(onSelectCell);
        Log.d("Test1", Session.getUsername());
        super.CreateTable(5, 15, "timeslot_", true);


    }

    @Override
    protected void onStart(){
        super.onStart();

        classroomSpinner = new ClassroomSpinner(this);
        classroomSpinner.FillSpinner();

        buildingRadioButton = new BuildingRadioButton(this);

        objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        objectMapper.setDateFormat(simpleDateFormat);

        _IO = IO.GetInstance();
        IFillDataStructures(objectMapper, _IO.DoPostRequestToAPIServer("{\"room\":\"H.3.403\",  \"weeknummer\":\"2\"}", "http://markb.pythonanywhere.com/bookingbyroom/" , this));

        for (BookingWrapper b: bookingWrapper) {
            FillRows(b);
        }

        // testBooking.setDate(new GregorianCalendar(2018, Calendar.JUNE, 5).getTime());
    }

    public void ClickReserve(View view){
        ArrayList<String> jsonObjects = new ArrayList<String>();
        for(Booking b : selectedBookings){
            try{
                jsonObjects.add(objectMapper.writeValueAsString(b));
            }
            catch (Exception e){
                Log.d("BookingToString", e.toString());
            }
        }
        ParseReservations(jsonObjects);

    }

    private void ParseReservations(ArrayList<String> jsonObjects){
        for (String booking: jsonObjects)
        {
            _IO = IO.GetInstance();
            _IO.DoPostRequestToAPIServer(booking, "http://markb.pythonanywhere.com/bookroom/", this);
        }
    }

    private void MarkBookings(Booking booking){
        Log.d("SelectBookingCell", booking.getLesson());
        if(selectedBookings.contains(booking)){
            selectedBookings.remove(booking);
        }
        else {
            selectedBookings.add(booking);
        }
    }

    private void FillRows(BookingWrapper booking){
        String cell_id;
        String lesson = booking.getFields().getLesson();
        String teacher = booking.getFields().getUsername();
        String room = booking.getFields().getRoom();
        int day = DatetoColumn(booking.getFields().getDate());
        int timeslotfrom = booking.getFields().getTimeslotfrom();
        int timeslotto = booking.getFields().getTimeslotto();

        TextView cell;

        for(int i = timeslotfrom; i <= timeslotto; i++ ){
            //Each cell has a specific id
            cell_id = Integer.toString(i) + Integer.toString(day);
            cell = findViewById(getResources().getIdentifier(cell_id, "id", getPackageName()));
            //Create a textview object to hold the lesson and teacher strings
            cell.setText(lesson + "\n" + teacher);
            //Put the booking object in our specific cell;
            cell.setTag(booking.getFields());
        }
    }

    @Override
    public void IFillDataStructures(ObjectMapper objectMapper, String json) {
        //new TypeReference<List<Booking>>(){}
        try{
            bookingWrapper = objectMapper.readValue(json, BookingWrapper[].class);
            Log.d("", "check");
        }
        catch(Exception e ){
            Log.d(e.toString(), e.toString());
        }
    }

    public void OnItemSelectedInSpinner(String room){
        _IO = IO.GetInstance();
        IFillDataStructures(objectMapper, _IO.DoPostRequestToAPIServer(String.format("{\"room\":\"%s\",  \"weeknummer\":\"1\"}", room), "http://markb.pythonanywhere.com/bookingbyroom/" , this));
        for (BookingWrapper b: bookingWrapper) {
            FillRows(b);
        }
    }

    public ClassroomSpinner getClassroomSpinner() {
        return classroomSpinner;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String chosenBuilding = "H";

        // Check which radio button was clicked
        switch(view.getId()) {

            case R.id.radio_pirates:
                if (checked){
                    chosenBuilding = "H";
                    break;
                }
            case R.id.radio_ninjas:
                if (checked){
                    chosenBuilding = "WN";
                    break;
                }
            case R.id.radio_vikings:
                if(checked){
                    chosenBuilding = "WD";
                }
        }

        this.classroomSpinner.IFillDataStructures(new ObjectMapper(), String.format("{\"building\":\"%s\"}", chosenBuilding));
    }


}
