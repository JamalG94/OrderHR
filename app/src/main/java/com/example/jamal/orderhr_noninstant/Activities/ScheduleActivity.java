package com.example.jamal.orderhr_noninstant.Activities;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import android.widget.TextView;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;

import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jamal on 3/19/2018.
 */

public class ScheduleActivity extends TableBuilder implements IDataStructure {

    private List<Booking> selectedBookings = new ArrayList<>();
    private List<Booking> allBookings;
    LinearLayout fragmentTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schedule);
        View.OnClickListener onSelectCell = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag() != null){
                Booking booking = (Booking)(v.getTag());
                MarkBookings(booking);
                }
            }
        };

        this.setOnSelectCell(onSelectCell);
        super.CreateTable(5, 10, "timeslot_", true);


    }

    @Override
    protected void onStart(){
        super.onStart();

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        objectMapper.setDateFormat(simpleDateFormat);

//        IO io = IO.GetInstance("http://markb.pythonanywhere.com/reservation/");
//        String result = io.GetData(1);
//        IVisit(objectMapper, result);

        allBookings = new ArrayList<Booking>();
        Booking testBooking = new Booking();
        testBooking.setRoom("AB");
        testBooking.setUsername("Jamal");
        testBooking.setLesson("Trekken");
        testBooking.setTimeslot_to(4);
        testBooking.setTimeslotfrom(3);
        testBooking.setDate(new GregorianCalendar(2018, Calendar.JUNE, 5).getTime());

        allBookings.add(testBooking);
        for (Booking b: allBookings) {
            FillRows(b);
        }
    }

    private void onClickReserve(){
        JSONObject[] jsonObjects = new JSONObject[selectedBookings.size()];
        for(Booking b : selectedBookings){
            //JSONObject jsonObject = b.ObjecttoJson();
        }
        ParseReservations(jsonObjects);

    }

    private void ParseReservations(JSONObject[] jsonObjects){
        //TODO magic jsonobjects to database away
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

    private void FillRows(Booking booking){
        String cell_id;
        String lesson = booking.getLesson();
        String teacher = booking.getUsername();
        String room = booking.getRoom();
        int day = DatetoColumn(booking.getDate());
        int timeslotfrom = booking.getTimeslot_from();
        int timeslotto = booking.getTimeslot_to();

        TextView cell;

        for(int i = timeslotfrom; i <= timeslotto; i++ ){
            //Each cell has a specific id
            cell_id = Integer.toString(i) + Integer.toString(day);
            cell = findViewById(getResources().getIdentifier(cell_id, "id", getPackageName()));
            //Create a textview object to hold the lesson and teacher strings
            cell.setText(teacher + " " + lesson + " " + room);
            //Put the booking object in our specific cell;
            cell.setTag(booking);
        }
    }

    @Override
    public void IFillDataStructures(ObjectMapper objectMapper, String json) {
        try{
            allBookings = objectMapper.readValue(json, new TypeReference<List<Booking>>(){});
            Log.d("", "check");
        }
        catch(Exception e ){
            Log.d(e.toString(), e.toString());
        }
    }

}
