package com.example.jamal.orderhr_noninstant.Activities.Schedule;

import android.content.res.Resources;
import android.graphics.Color;
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
import com.example.jamal.orderhr_noninstant.R;
import com.example.jamal.orderhr_noninstant.Utility.Schedule.TimeSlotConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by jamal on 3/19/2018.
 */

public class ScheduleActivity extends RowFiller {

    private ArrayList<TimeDay> selectedBookings = new ArrayList<>();

    private IO _IO;
    private ObjectMapper objectMapper;

    private String currentRoom;
    private int currentWeek;
    private Date selectedDate;

    private ClassroomSpinner classroomSpinner;
    private EditText lesson;

    private WeekDate weekDate;
    public String status_stringstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        status_stringstatus = "Not yet saved";

        this.setOnSelectCell(onSelectCell);
        super.CreateTable(5, 15, "timeslot_", true);

        _IO = IO.GetInstance();

        objectMapper = new ObjectMapper();
        DateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        objectMapper.setDateFormat(simpleDateFormat);

        lesson = findViewById(R.id.lesson);
        Button reserveButton = findViewById(R.id.reserveButton);

        BuildingRadioButton buildingRadioButton = new BuildingRadioButton(this);
        buildingRadioButton.AssignOnClickRadioButton();

        weekDate = new WeekDate();

        //Disables reserve feature if not a staff member
        if(Session.getIsStaff() | Session.getIsAdmin()){
            lesson.setVisibility(View.VISIBLE);
            reserveButton.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void onStart(){
        super.onStart();

        classroomSpinner = new ClassroomSpinner(this);
        classroomSpinner.FillSpinner();

        //TODO CHECK FOR BUGS
        ClearSelectedBookings();
        currentRoom = "";
        currentWeek = weekDate.GetWeek();
        weekDate.AddDatesToHashMap(weekDate.GetCalendarSetAtWeek(currentWeek), 5);
    }

    //test case C4
    //TODO Click Button
    //This function is used for the reserve button and transforms each booking object into a jsonobject.
    public void ClickReserve(View view){
        //TODO TRY THIS
        if(selectedBookings.size() > 0){
            Collections.sort(selectedBookings, new TimeSlotComparator());
            int timeslotFrom = selectedBookings.get(0).getTimeslot();
            int timeslotTo = selectedBookings.get(selectedBookings.size() - 1).getTimeslot();

            if(!lesson.getText().toString().isEmpty()){
                if(ReservationProcess.CheckCorrectRoomFormat(currentRoom)){
                Booking booking = ReservationProcess.CreateBooking(timeslotFrom, timeslotTo, TimeSlotConverter.TimeSlotToTimeString(timeslotFrom).first, TimeSlotConverter.TimeSlotToTimeString( timeslotTo).second,
                        currentRoom, selectedDate, lesson.getText().toString(),  currentWeek);

                AvailableSlot availableSlot = ReservationProcess.CreateAvailableSlot(timeslotFrom, timeslotTo, currentRoom, selectedDate);

                //Checks the availability based upon the api, if so a user can now truly book.
                if(ReservationProcess.CheckAvailability(ReservationProcess.CreateAvailabilityJson(availableSlot), this)){
                    status_stringstatus = ReservationProcess.ParseReservations(ReservationProcess.CreateBookingJson(booking), this);
                    }else{
                        status_stringstatus = "Already booked!";
                    }
                }
                else{
                    status_stringstatus = "Choose a room";
                }
            }
            else{
                status_stringstatus ="Fill in a lesson code";
            }
        }
        else{
            status_stringstatus = "Select Timeslots";
            Log.d("No Timeslots", "ClickReserve: User hasn't selected any timeslots");
        }
        Toast.makeText(this, status_stringstatus, Toast.LENGTH_LONG).show();
        //We call SelectedRoom here again to refresh the Bookings after we pushed a reservation
        ClassRoomSelected(currentRoom);
    }

    //TODO ClickButton
    private View.OnClickListener onSelectCell = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!reservedSlots.contains(Integer.toString(v.getId()))){
                if (v.getTag() != null) {
                    TimeDay booking = (TimeDay) (v.getTag());
                    if (SameDayCheck(weekDate.DayToDate(booking.getDay()))) {
                        ChangeBackGroundCell(MarkTimedays(booking), v);
                    }
                }
            }
        }
    };

    //testcase C12
    //this is the function that each cell uses and we give to the onclick of each cell
    public boolean MarkTimedays(TimeDay timeDay){
        if(selectedBookings.contains(timeDay)){
            selectedBookings.remove(timeDay);
            if(selectedBookings.size() <= 0){
                selectedDate = null;
            }
            return false;
        }
        else {
            selectedBookings.add(timeDay);
            return true;
        }
    }

    private void ChangeBackGroundCell(Boolean select, View cell){
        if(select){
            cell.setBackgroundResource(R.color.orange);
        }
        else{
            cell.setBackgroundResource(R.color.white);
        }
    }

    private void ClearSelectedBookings(){
        selectedBookings.clear();
    }

    //TODO CLICK BUTTON
    public void BuildingSelected(String chosenBuilding){
        classroomSpinner.GetClassRooms(String.format("{\"building\":\"%s\"}", chosenBuilding));
    }


    //TODO CLICK BUTTON
    public void onClickNextWeek(View view){
        try{
            if(currentWeek + 1 <= 52)
            {
                currentWeek += 1;
            }
            else{
                currentWeek = 1;
                weekDate.PassYear();
                Toast.makeText(this, weekDate.GetYear(), Toast.LENGTH_SHORT).show();
            }
            ChangeWeek();
        }
        catch (Exception e){

        }
    }

    //TODO CLICK BUTTON
    public void onClickPreviousWeek(View view){
        try {
            if (currentWeek - 1 > 1) {
                currentWeek -= 1;
            } else {
                currentWeek = 52;
                weekDate.YearBack();
                Toast.makeText(this, weekDate.GetYear(), Toast.LENGTH_SHORT).show();
            }
            ChangeWeek();
        }
        catch (Exception e){

        }
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
        try {
            Toast.makeText(this, "" + String.valueOf(currentWeek), Toast.LENGTH_SHORT).show();
        }
        catch (IllegalArgumentException e){
            Log.d("ChangeWeekIllegal", "ChangeWeek: " + e.getMessage());
        }
        catch(Resources.NotFoundException e){
            Log.d("ChangeWeek", "ChangeWeek: " + e.getMessage());
        }
        Log.d("weekdate", weekDate.GetWeek() + " " + weekDate.GetYear());
        Log.d("weekdate"," currentweekdate now is " + currentWeek + " updating...");
        //Everytime we change a week we update the weekschedule's cells to include the new dates
        weekDate.AddDatesToHashMap(weekDate.GetCalendarSetAtWeek(currentWeek), 5);
        Log.d("weekdate", weekDate.GetWeek() + " " + weekDate.GetYear());
    }


    //TODO ONCLICK
    public void ClassRoomSelected(String room) {
        BookingWrapper[] bookingWrapper = new BookingWrapper[]{};
        if(ReservationProcess.CheckCorrectRoomFormat(room)){
            String weekSchedule = BookingAPI.GetBookingsAtRoom(room, currentWeek, this);

            if(!weekSchedule.isEmpty()){
                bookingWrapper = BookingAPI.JsonToBookingWrapper(weekSchedule);
            }
            ResetSchedule(bookingWrapper, room);
        }
    }

    //TODO ONCLICK
    private void ResetSchedule(BookingWrapper[] bookingWrapper, String room){
        super.ClearRows();
        currentRoom = room;

        //weekDate.GetYear();
        if (bookingWrapper.length > 0) {
            for (BookingWrapper bookingWrapperEntry : bookingWrapper) {
                Booking booking = bookingWrapperEntry.getFields();
                Date date = booking.getDate();

                //This if is needed because of database design, without it bookings from 2018 will show in years that are not 2018
                if(weekDate.CompareYears(date))
                    super.FillRows(booking.getLesson() + "\n" + booking.getUsername(), date , booking.getTimeslotfrom(), booking.getTimeslotto());
            }
        }
        else{
            //Toast.makeText(this, "The room you selected has no bookings this week!", Toast.LENGTH_SHORT).show();
        }
    }
    public void setTextEditLesson(String text) {
        this.lesson.setText(text);

    }

}
