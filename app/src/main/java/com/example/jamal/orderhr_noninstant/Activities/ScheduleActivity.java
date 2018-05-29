package com.example.jamal.orderhr_noninstant.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.BuildingRadioButton;
import com.example.jamal.orderhr_noninstant.ClassroomSpinner;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.Datastructures.BookingWrapper;
import com.example.jamal.orderhr_noninstant.Datastructures.TimeDay;
import com.example.jamal.orderhr_noninstant.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.example.jamal.orderhr_noninstant.ScheduleUtility;
import com.example.jamal.orderhr_noninstant.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by jamal on 3/19/2018.
 */

public class ScheduleActivity extends TableBuilder{

    EditText editText;//Reserve

    private List<TimeDay> selectedBookings = new ArrayList<>(); //Reserve
    private List<String> reservedSlots;
    private BookingWrapper[] bookingWrapper;
    private ObjectMapper objectMapper;//Both

    private String currentRoom;//Both
    private int currentWeek;//Schedule

    private IO _IO;//Both
    private ClassroomSpinner classroomSpinner;
    private BuildingRadioButton buildingRadioButton;//Schedule


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reservedSlots = new ArrayList<>();

        setContentView(R.layout.activity_schedule);

        editText = findViewById(R.id.lesson);


        this.setOnSelectCell(onSelectCell);
        super.CreateTable(5, 10, "timeslot_", true);

        _IO = IO.GetInstance();
        classroomSpinner = new ClassroomSpinner(this);
        classroomSpinner.FillSpinner();

        objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        objectMapper.setDateFormat(simpleDateFormat);

        buildingRadioButton = new BuildingRadioButton(this);
        buildingRadioButton.AssignOnClickRadioButton();

        // testBooking.setDate(new GregorianCalendar(2018, Calendar.JUNE, 5).getTime());
    }

    //This function is used for the reserve button and transforms each booking object into a jsonobject.
    public void ClickReserve(View view){
        Collections.sort(selectedBookings, new CustomComparator());

        Booking booking = this.CreateBookings(selectedBookings);
        try {
            ParseReservations(objectMapper.writeValueAsString(booking));
        }
        catch (Exception e){}

    }

    public Booking CreateBookings(List<TimeDay> timeDayList){
        Booking createdBooking = new Booking();

        ScheduleUtility scheduleUtility = new ScheduleUtility();

        if(timeDayList.size() > 0){
            TimeDay first = timeDayList.get(0);
            TimeDay last = timeDayList.get(timeDayList.size() - 1);

            createdBooking.setTimeslotfrom(first.getTimeslot());
            createdBooking.setTimeslotto(last.getTimeslot());
            createdBooking.setRoom(currentRoom);

            createdBooking.setDate(new Date());
            createdBooking.setUsername(Session.getUsername());
            createdBooking.setLesson(editText.getText().toString());
            createdBooking.setWeeknummer(1);
            createdBooking.setTimefrom(scheduleUtility.TimeSlotToTimeString(first.getTimeslot()));
            createdBooking.setTimeto(scheduleUtility.TimeSlotToTimeString(last.getTimeslot()));
        }
        
        return createdBooking;
    }

    //This function posts all bookings to the api
    private void ParseReservations(String jsonObject){
        _IO = IO.GetInstance();
        _IO.DoPostRequestToAPIServer(jsonObject, "http://markb.pythonanywhere.com/bookroom/", this);
    }

    private View.OnClickListener onSelectCell = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getTag() != null){
                try {
                    TimeDay booking = (TimeDay) (v.getTag());
                    MarkTimedays(booking);
                }
                catch (Exception e){
                    Log.d(e.toString(), e.toString());
                }

            }
        }
    };

    public class CustomComparator implements Comparator<TimeDay> {
        @Override
        public int compare(TimeDay o1, TimeDay o2) {
            return Integer.compare(o1.getTimeslot(),(o2.getTimeslot()));
        }
    }

    //this is the function that each cell uses and we give to the onclick of each cell
    private void MarkTimedays(TimeDay timeDay){
        if(selectedBookings.contains(timeDay)){
            selectedBookings.remove(timeDay);
        }
        else {
            selectedBookings.add(timeDay);
        }
    }

    //we created the base table though the means of the superclass's method tablecreator, with this method we fill the cells with bookingobjects
    private void FillRows(BookingWrapper booking){
        String cell_id;
        String lesson = booking.getFields().getLesson();
        String teacher = booking.getFields().getUsername();
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
            cell.setTag(new TimeDay(day, i));

            reservedSlots.add(cell_id);
        }
    }

    private void ClearRows(){
        TextView cell;
        for (String cell_id: reservedSlots
             ) {
            cell = findViewById(getResources().getIdentifier(cell_id, "id", getPackageName()));
            cell.setText("");
        }
    }



    public void JsonToBookingWrapper(ObjectMapper objectMapper, String json) {
        try{
            bookingWrapper = objectMapper.readValue(json, BookingWrapper[].class);
        }
        catch(Exception e ){
            Log.d(e.toString(), e.toString());
        }
    }


    public void OnItemSelectedInSpinner(String room){
        _IO = IO.GetInstance();
        JsonToBookingWrapper(objectMapper, _IO.DoPostRequestToAPIServer(String.format("{\"room\":\"%s\",  \"weeknummer\":\"1\"}", room), "http://markb.pythonanywhere.com/bookingbyroom/" , this));
        currentRoom = room;

        ClearRows();
        for (BookingWrapper b: bookingWrapper) {
            FillRows(b);
        }
    }

    public ClassroomSpinner getClassroomSpinner() {
        return classroomSpinner;
    }
}
