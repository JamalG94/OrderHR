package com.example.jamal.orderhr_noninstant.Activities.Schedule;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.Activities.SuperClass.RowFiller;
import com.example.jamal.orderhr_noninstant.Session.Session;
import com.example.jamal.orderhr_noninstant.Utility.Schedule.TimeSlotComparator;
import com.example.jamal.orderhr_noninstant.Datastructures.AvailableSlot;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.Datastructures.BookingWrapper;
import com.example.jamal.orderhr_noninstant.Datastructures.TimeDay;
import com.example.jamal.orderhr_noninstant.API.IO;
import com.example.jamal.orderhr_noninstant.LocalDBControllers.LocalDatabaseRepository;
import com.example.jamal.orderhr_noninstant.R;
import com.example.jamal.orderhr_noninstant.Utility.Schedule.ScheduleUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by jamal on 3/19/2018.
 */

//TODO IMPLEMENT FILLROWS INTERFACE
public class ScheduleActivity extends RowFiller {

    private ArrayList<TimeDay> selectedBookings = new ArrayList<>();
    private List<String> reservedSlots = new ArrayList<>();

    private IO _IO;
    private ObjectMapper objectMapper;
    LocalDatabaseRepository localDB;

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
        localDB = new LocalDatabaseRepository(getApplication());

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
        if(Session.getIsStaff()){
            lesson.setVisibility(View.VISIBLE);
            reserveButton.setVisibility(View.VISIBLE);
        }


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
            ScheduleUtility.PassYear();
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
            ScheduleUtility.YearBack();
        }
        ChangeWeek();
    }

    //TODO Dates
    private Boolean SameDayCheck(Date date){
        //First time user selects a date
        if(selectedDate == null) {
            selectedDate = date;
            return true;
        }
        //Checks if dates are equal
        else if(selectedDate.getTime() == date.getTime())
        {
            return true;
        }
        Toast.makeText(this, "You've chosen a different day, choose a timeslot in the same day", Toast.LENGTH_SHORT).show();
        return false;
    }

    //TODO FILLSCHEDULE
    private void ChangeWeek(){
        ClassRoomSelected(currentRoom);
        Toast.makeText(this, "" + currentWeek, Toast.LENGTH_SHORT).show();
        scheduleUtility.AddDatesToHashMap(ScheduleUtility.GetCalendarSetAtWeek(currentWeek), 5);
    }


    //TODO ONCLICK
    public void ClassRoomSelected(String room) {
        BookingWrapper[] bookingWrapper = new BookingWrapper[]{};
        String weekSchedule = BookingAPI.GetBookingsAtRoom(room, currentWeek, this);

        if(!weekSchedule.isEmpty()){
            bookingWrapper = BookingAPI.JsonToBookingWrapper(weekSchedule);
        }
        ResetSchedule(bookingWrapper, room);
    }

    //TODO ONCLICK
    private void ResetSchedule(BookingWrapper[] bookingWrapper, String room){
        super.ClearRows();
        if (bookingWrapper.length > 0) {
            currentRoom = room;
            for (BookingWrapper b : bookingWrapper) {
                Booking booking = b.getFields();
                super.FillRows(booking.getLesson() + "/n" + booking.getUsername(), booking.getDate(), booking.getTimeslotfrom(), booking.getTimeslotto());
            }
        }
        else{
            Toast.makeText(this, "The room you selected has no bookings this week!", Toast.LENGTH_SHORT).show();
        }
    }
}
