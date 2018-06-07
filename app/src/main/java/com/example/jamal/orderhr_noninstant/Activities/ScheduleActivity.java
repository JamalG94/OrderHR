package com.example.jamal.orderhr_noninstant.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.BuildingRadioButton;
import com.example.jamal.orderhr_noninstant.ClassroomSpinner;
import com.example.jamal.orderhr_noninstant.Datastructures.AvailableSlot;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.Datastructures.BookingWrapper;
import com.example.jamal.orderhr_noninstant.Datastructures.TimeDay;
import com.example.jamal.orderhr_noninstant.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.example.jamal.orderhr_noninstant.ReservationProcess;
import com.example.jamal.orderhr_noninstant.ScheduleUtility;
import com.example.jamal.orderhr_noninstant.Session;
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
public class ScheduleActivity extends TableBuilder implements IDataFiller<Booking>{

    private ArrayList<TimeDay> selectedBookings = new ArrayList<>();
    private List<String> reservedSlots = new ArrayList<>();

    private IO _IO;
    private ObjectMapper objectMapper;

    private String currentRoom;
    private int currentWeek;
    private Date selectedDate;

    private ClassroomSpinner classroomSpinner;
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
        Button reserveButton = findViewById(R.id.reserveButton);

        BuildingRadioButton buildingRadioButton = new BuildingRadioButton(this);
        buildingRadioButton.AssignOnClickRadioButton();

        scheduleUtility = new ScheduleUtility();
        scheduleUtility.FillTimeSlots(this);
        currentWeek = ScheduleUtility.GetWeek();
        scheduleUtility.AddDatesToHashMap(ScheduleUtility.GetCalendarSetAtWeek(currentWeek), 5);

        //Disables reserve feature if not a staff member
//        if(Session.getIsStaff()){
//            lesson.setVisibility(View.VISIBLE);
//            reserveButton.setVisibility(View.VISIBLE);
//        }


    }

    //TODO Click Button
    //This function is used for the reserve button and transforms each booking object into a jsonobject.
    public void ClickReserve(View view){

        //TODO TRY THIS
        Collections.sort(selectedBookings, new TimeSlotComparator());
        int timeslotFrom = selectedBookings.get(0).getTimeslot();
        int timeslotTo = selectedBookings.get(selectedBookings.size() - 1).getTimeslot();

        Booking booking = ReservationProcess.CreateBooking(timeslotFrom, timeslotTo, scheduleUtility.TimeSlotToTimeString(timeslotFrom), scheduleUtility.TimeSlotToTimeString( timeslotTo),
                currentRoom, selectedDate, lesson.getText().toString(),  currentWeek);

        AvailableSlot availableSlot = ReservationProcess.CreateAvailableSlot(timeslotFrom, timeslotTo, currentRoom, selectedDate);

        //Checks the availability based upon the api, if so a user can now truly book.
        if(ReservationProcess.CheckAvailability(ReservationProcess.CreateAvailabilityJson(availableSlot), this))
            ReservationProcess.ParseReservations(ReservationProcess.CreateBookingJson(booking), this);
    }

    //TODO ClickButton
    private View.OnClickListener onSelectCell = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!reservedSlots.contains(Integer.toString(v.getId()))){
                if (v.getTag() != null) {
                    TimeDay booking = (TimeDay) (v.getTag());
                    if (SameDayCheck(scheduleUtility.DayToDate(booking.getDay()))) {
                        MarkTimedays(booking);
                        }
                    }
                }
            }
        };

    //TODO CLICK BUTTON
    //this is the function that each cell uses and we give to the onclick of each cell
    private void MarkTimedays(TimeDay timeDay){
        if(selectedBookings.contains(timeDay)){
            selectedBookings.remove(timeDay);
        }
        else {
            selectedBookings.add(timeDay);
        }
    }

    //TODO CLICK BUTTON
    //TODO TEST
    public void BuildingSelected(String chosenBuilding){
        classroomSpinner.GetClassRooms(String.format("{\"building\":\"%s\"}", chosenBuilding));
    }


    //TODO CLICK BUTTON
    public void onClickNextWeek(View view){
        if(currentWeek + 1 <= 52)
        {
            currentWeek += 1;
        }
        else{
            currentWeek = 1;
        }
        ChangeWeek();
    }

    //TODO CLICK BUTTON
    public void onClickPreviousWeek(View view){
        if(currentWeek -1 > 1){
            currentWeek -= 1;
        }
        else{
            currentWeek = 52;
        }
        ChangeWeek();
    }

    //TODO Dates
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

    //TODO DATES
    private void ChangeWeek(){
        ClassRoomSelected(currentRoom);
        Toast.makeText(this, "" + currentWeek, Toast.LENGTH_SHORT).show();
        scheduleUtility.AddDatesToHashMap(ScheduleUtility.GetCalendarSetAtWeek(currentWeek), 5);
    }


    //TODO FILLSCHEDULE
    //we created the base table though the means of the superclass's method tablecreator, with this method we fill the cells with bookingobjects
    @Override
    public void FillView(Booking booking) {
        String cell_id;
        String lesson = booking.getLesson();
        String teacher = booking.getUsername();
        int day = DatetoColumn(booking.getDate());
        int timeslotfrom = booking.getTimeslotfrom();
        int timeslotto = booking.getTimeslotto();

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

    //TODO FILLSCHEDULE
    private void ClearRows(){
        TextView cell;
        for (String cell_id: reservedSlots) {
            cell = findViewById(getResources().getIdentifier(cell_id, "id", getPackageName()));
            cell.setText("");
        }
    }

    //TODO FILLSCHEDULE
    //TODO TEST
    private BookingWrapper[] JsonToBookingWrapper(ObjectMapper objectMapper, String json){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            //Set the objectmapper's date format in the try otherwise it doesn't register
            objectMapper.setDateFormat(format);
            return objectMapper.readValue(json, BookingWrapper[].class);
        } catch(IOException exception){
            Log.d("BookingWrapper", "JsonToBookingWrapper: " + exception.getMessage());
        }
        return new BookingWrapper[]{};
    }

    //TODO FILLSCHEDULE
    public void ClassRoomSelected(String room) {
        _IO = IO.GetInstance();
        BookingWrapper[] bookingWrapper = new BookingWrapper[]{};

        String weekSchedule = _IO.DoPostRequestToAPIServer(String.format("{\"room\":\"%s\",  \"weeknummer\":\"%s\"}", room, currentWeek),
                "http://markb.pythonanywhere.com/bookingbyroom/", this);
        if(!weekSchedule.isEmpty()){
            bookingWrapper = JsonToBookingWrapper(objectMapper, weekSchedule);
        }

        ClearRows();
        if (bookingWrapper.length > 0) {
            currentRoom = room;
            for (BookingWrapper b : bookingWrapper) {
                FillView(b.getFields());
            }
        }
    }

}
