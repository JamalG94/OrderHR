package com.example.jamal.orderhr_noninstant.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jamal on 3/19/2018.
 */

public class ScheduleActivity extends AppCompatActivity implements IDataStructure {

    private List<Booking> selectedBookings = new ArrayList<>();
    private List<Booking> allBookings = new ArrayList<>();
    private IO _IO;
    TableLayout fragmentTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule);
        fragmentTable = findViewById(R.id.fragment_schedule);
        CreateTable(5, 10, "timeslot_");
        setContentView(fragmentTable);


        //setContentView(R.layout.activity_schedule);
        //_IO = IO.GetInstance("http://markb.pythonanywhere.com/reservation");
    }

    @Override
    protected void onStart(){
        super.onStart();

//        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
//        objectMapper.setDateFormat(simpleDateFormat);
//
//        this.IVisit(objectMapper, _IO.GetData(1));
//        for (Booking b: allBookings) {
//            FillRows(b);
//        }
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
        if(selectedBookings.contains(booking)){
            selectedBookings.remove(booking);
        }
        else {
            selectedBookings.add(booking);
        }
    }

    private int DatetoColumn(Date date){
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        int dayOfWeek = calender.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    private void FillRows(Booking booking){
        String tablerow_id;
        final String lesson = booking.getLesson();
        String teacher = booking.getUsername();
        //int day = DatetoColumn(booking.DateOn);
        int timeslotfrom = 3;
        int timeslotto = 4;

        TableRow tablerow;
        TextView timeslot_lesson;

        //Create a listener that does something unique with each cell
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Booking booking = (Booking)(v.getTag());
                Log.d("CellClick", lesson);
                MarkBookings(booking);
            }
        };

        for(int i = timeslotfrom; i <= timeslotto; i++ ){
            //Create a textview object to hold the lesson and teacher strings
            timeslot_lesson = new TextView(this);
            timeslot_lesson.setText(teacher + lesson);
            //Put the booking object in our specific cell;
            timeslot_lesson.setTag(booking);
            timeslot_lesson.setOnClickListener(listener);

            //Find the appropiate timeslot of the reservation
            tablerow_id = "timeslot_" + i;
            tablerow = findViewById(getResources().getIdentifier(tablerow_id, "id", getPackageName()));
            tablerow.addView(timeslot_lesson);

            //Create a new TableRow.LayoutParams so we can set the reservation at the right day through help of DatetoColumn
            TableRow.LayoutParams tbr = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            tbr.column = 3;
            timeslot_lesson.setLayoutParams(tbr);
        }
    }

    @Override
    public void IVisit(ObjectMapper objectMapper, String json) {
        try{
            List<Booking> list = objectMapper.readValue(json, new TypeReference<List<Booking>>(){});
            Log.d("", "check");
        }
        catch(Exception e ){
            Log.d(e.toString(), e.toString());
        }
    }

    public void CreateTable(int daysAmount, int timeslots, String identifier){
        View.OnClickListener onSelectCell = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Booking booking = (Booking)(v.getTag());
                MarkBookings(booking);
            }
        };

        float weight = 1 / daysAmount;

        for (int j = 1; j <= timeslots; j++){
            String tablerow_id = identifier+j;
            TableRow timeslot = findViewById(getResources().getIdentifier(tablerow_id, "id", getPackageName()));

            for(int z = 1; z <= daysAmount; z++){
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, weight);
                layoutParams.setMargins(0,0,10,30);
                TextView dayTimeslot = new TextView(this);
                dayTimeslot.setText("timeslot"+j + "\n" + "day"+ z);
                dayTimeslot.setLayoutParams(layoutParams);

                try {
                    int DTID = Integer.valueOf("timeslot"+j +"day"+z);
                    dayTimeslot.setId(DTID);
                }
                catch (Exception e){}

                dayTimeslot.setOnClickListener(onSelectCell);
                timeslot.addView(dayTimeslot);
            }
        }
    }
}
