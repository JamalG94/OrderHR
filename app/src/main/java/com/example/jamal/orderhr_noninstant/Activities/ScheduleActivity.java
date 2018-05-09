package com.example.jamal.orderhr_noninstant.Activities;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import android.widget.TextView;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;

import com.example.jamal.orderhr_noninstant.Datastructures.BookingWrapper;
import com.example.jamal.orderhr_noninstant.GetData;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

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
    LinearLayout fragmentTable;
    private ObjectMapper objectMapper;


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
        super.CreateTable(5, 10, "timeslot_", true);


    }

    @Override
    protected void onStart(){
        super.onStart();

        objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        objectMapper.setDateFormat(simpleDateFormat);

        String test = GetData.RequestBookingByID("{\"id\":\"1\"}");

        String test1 =GetData.RequestBookingByRoom("{\"room\":\"H.3.403\",  \"weeknummer\":\"2\"}");

//        IO io = IO.GetInstance("http://markb.pythonanywhere.com/reservation/");
//        String result = io.GetData(1);
        IFillDataStructures(objectMapper, test1);


//        testBooking.setDate(new GregorianCalendar(2018, Calendar.JUNE, 5).getTime());

        //allBookings.add(testBooking);
        for (BookingWrapper b: bookingWrapper) {
            FillRows(b);
        }
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
            cell.setText(teacher + " " + lesson + " " + room);
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

}
