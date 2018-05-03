package com.example.jamal.orderhr_noninstant.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by jamal on 3/19/2018.
 */

public class ScheduleActivity extends AppCompatActivity implements IDataStructure {

    private List<Booking> selectedBookings = new ArrayList<>();
    private List<Booking> allBookings;
    private IO _IO;
    LinearLayout fragmentTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule);
        fragmentTable = findViewById(R.id.fragment_scheduleLinear);

        View.OnClickListener onSelectCell = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag() != null){
                Booking booking = (Booking)(v.getTag());
                MarkBookings(booking);
                }
            }
        };

        CreateTable(5, 10, "timeslot_", onSelectCell);
        setContentView(fragmentTable);

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
    public void IVisit(ObjectMapper objectMapper, String json) {
        try{
            allBookings = objectMapper.readValue(json, new TypeReference<List<Booking>>(){});
            Log.d("", "check");
        }
        catch(Exception e ){
            Log.d(e.toString(), e.toString());
        }
    }

    public void CreateTable(int daysAmount, int timeslots, String identifier, View.OnClickListener onSelectCell){
        //weight of every cell which means how much space it gets from the screen
        float weight = 1 / timeslots;



        //go through all the timeslots and foreach timeslot create *daysAmount of TextViews horizontally
        for (int j = 1; j <= timeslots; j++){
            String tablerow_id = identifier+j;
            TableRow timeslot = findViewById(getResources().getIdentifier(tablerow_id, "id", getPackageName()));

            for(int z = 1; z <= daysAmount; z++){
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(0,0,0,30);


                TextView dayTimeslot = new TextView(this);
                dayTimeslot.setText("timeslot"+j + "\n" + "day"+ z);
                dayTimeslot.setTextSize(12);
                dayTimeslot.setLayoutParams(layoutParams);


                //try to assign an id to each TextView based on the timeslot and day so we can later find it back.
                try {
                    String cellSpecific= Integer.toString(j)+ Integer.toString(z);
                    int DTID = Integer.valueOf(cellSpecific);
                    dayTimeslot.setId(DTID);
                }
                catch (Exception e){}

                dayTimeslot.setOnClickListener(onSelectCell);
                timeslot.addView(dayTimeslot);
            }
        }
    }

    private int DatetoColumn(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setCalendar(Calendar.getInstance());

        sdf.format(date);
        //Here you set to your timezone
        sdf.setTimeZone(TimeZone.getDefault());
        //Will print on your default Timezone
        System.out.println(sdf.format(sdf.getCalendar().getTime()));

        int day = sdf.getCalendar().get(Calendar.DAY_OF_WEEK);
        //We return -1 because the Calendar.DAY_OF_WEEK method starts at sunday, and my week schedule starts counting from monday
        return day -1;
    }

}
