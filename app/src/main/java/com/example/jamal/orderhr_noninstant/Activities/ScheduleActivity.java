package com.example.jamal.orderhr_noninstant.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.BuildingRadioButton;
import com.example.jamal.orderhr_noninstant.ClassroomSpinner;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.Datastructures.BookingWrapper;
import com.example.jamal.orderhr_noninstant.Datastructures.TimeDay;
import com.example.jamal.orderhr_noninstant.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.example.jamal.orderhr_noninstant.ReservationProcess;
import com.example.jamal.orderhr_noninstant.ScheduleUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jamal on 3/19/2018.
 */

//TODO IMPLEMENT FILLROWS INTERFACE
public class ScheduleActivity extends TableBuilder{

    private ArrayList<TimeDay> selectedBookings = new ArrayList<>();
    private List<String> reservedSlots = new ArrayList<>();

    private IO _IO;
    private BookingWrapper[] bookingWrapper;
    private ObjectMapper objectMapper;

    private String currentRoom;
    private int currentWeek;
    private Date selectedDate;

    private ClassroomSpinner classroomSpinner;
    private BuildingRadioButton buildingRadioButton;
    private EditText lesson;
    private ScheduleUtility scheduleUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        this.setOnSelectCell(onSelectCell);
        super.CreateTable(5, 15, "timeslot_", true);

        _IO = IO.GetInstance();
        classroomSpinner = new ClassroomSpinner(this);
        classroomSpinner.FillSpinner();

        objectMapper = new ObjectMapper();
        DateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        objectMapper.setDateFormat(simpleDateFormat);

        lesson = findViewById(R.id.lesson);

        buildingRadioButton = new BuildingRadioButton(this);
        buildingRadioButton.AssignOnClickRadioButton();

        scheduleUtility = new ScheduleUtility();
        currentWeek = ScheduleUtility.GetWeek();
        scheduleUtility.AddDatesToHashMap(ScheduleUtility.GetCalendarSetAtWeek(currentWeek), 5);
    }

    //TODO ORDER THESE METHODS BY THERE USE

    //This function is used for the reserve button and transforms each booking object into a jsonobject.
    public void ClickReserve(View view){

        //TODO TRY THIS
        Collections.sort(selectedBookings, new TimeSlotComparator());
        int timeslotFrom = selectedBookings.get(0).getTimeslot();
        int timeslotTo = selectedBookings.get(selectedBookings.size() - 1).getTimeslot();
        Booking b = ReservationProcess.CreateBooking(timeslotFrom, timeslotTo, scheduleUtility.TimeSlotToTimeString(timeslotFrom), scheduleUtility.TimeSlotToTimeString( timeslotTo),
                currentRoom, selectedDate, lesson.getText().toString(),  currentWeek);

        ReservationProcess.ParseReservations(ReservationProcess.CreateBookingJson(b, objectMapper), this);
    }

    private View.OnClickListener onSelectCell = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() != null) {
                TimeDay booking = (TimeDay) (v.getTag());
                if (SameDayCheck(scheduleUtility.DayToDate(booking.getDay()))) {
                    MarkTimedays(booking);
                }
            }
        }
    };

    //TODO TEST
    private Boolean SameDayCheck(Date date){
        if(selectedDate == null) {
            selectedDate = date;
            return true;
        }
        else if(selectedDate.getTime() == date.getTime())
        {
            return true;
        }
        Toast.makeText(this, "You've chosen a different day, choose a timeslot in the same day", Toast.LENGTH_SHORT).show();
        return false;
    }

    //TODO TEST
    private class TimeSlotComparator implements Comparator<TimeDay> {
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
            if(cell != null){
                //Create a textview object to hold the lesson and teacher strings
                cell.setText(lesson + "\n" + teacher);
                //Put the booking object in our specific cell;
                cell.setTag(new TimeDay(day, i));

                reservedSlots.add(cell_id);
            }
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

    //TODO TEST
    public void JsonToBookingWrapper(ObjectMapper objectMapper, String json){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            objectMapper.setDateFormat(format);
            bookingWrapper = objectMapper.readValue(json, BookingWrapper[].class);
            throw new IOException();
        } catch(IOException exception){
            Log.d("BookingWrapper", "JsonToBookingWrapper: " + exception.getMessage());
        }
    }

    public void ClassRoomSelected(String room) {
        //TODO REFRACTOR JSONBOOKINGWRAPPER INTO THIS METHOD
        _IO = IO.GetInstance();
        JsonToBookingWrapper(objectMapper, _IO.DoPostRequestToAPIServer(String.format("{\"room\":\"%s\",  \"weeknummer\":\"%s\"}", room, currentWeek),
                "http://markb.pythonanywhere.com/bookingbyroom/", this));

        currentRoom = room;
        ClearRows();

        if (bookingWrapper != null) {
            for (BookingWrapper b : bookingWrapper) {
                FillRows(b);
            }
        }
    }

    //TODO TEST
    public void BuildingSelected(String chosenBuilding){
        classroomSpinner.JsonToClassroomList(new ObjectMapper(), String.format("{\"building\":\"%s\"}", chosenBuilding));
    }

    public void onClickNextWeek(View view){
        //TODO FIND a way to make these two methods combined
        currentWeek += 1;
        ClassRoomSelected(currentRoom);
        Toast.makeText(this, "" + currentWeek, Toast.LENGTH_SHORT).show();
        scheduleUtility.AddDatesToHashMap(ScheduleUtility.GetCalendarSetAtWeek(currentWeek), 5);
        scheduleUtility.PrintDates();
    }

    public void onClickPreviousWeek(View view){
        currentWeek -=1;
        ClassRoomSelected(currentRoom);
        Toast.makeText(this, "" + currentWeek, Toast.LENGTH_SHORT).show();
        scheduleUtility.AddDatesToHashMap(ScheduleUtility.GetCalendarSetAtWeek(currentWeek), 5);
        scheduleUtility.PrintDates();
    }

}
