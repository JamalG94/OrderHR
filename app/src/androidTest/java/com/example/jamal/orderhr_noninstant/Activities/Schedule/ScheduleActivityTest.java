package com.example.jamal.orderhr_noninstant.Activities.Schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.jamal.orderhr_noninstant.API.IO;
import com.example.jamal.orderhr_noninstant.Activities.Defuncts.ViewDefunctDetailsActivity;
import com.example.jamal.orderhr_noninstant.Datastructures.Staff;
import com.example.jamal.orderhr_noninstant.Datastructures.TimeDay;
import com.example.jamal.orderhr_noninstant.R;
import com.example.jamal.orderhr_noninstant.Session.Session;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Robin on 6/15/2018.
 */

//TESTCASE C4
@RunWith(AndroidJUnit4.class)
public class ScheduleActivityTest {
    @Rule
     public ActivityTestRule<ScheduleActivity> rule = new ActivityTestRule<ScheduleActivity>(ScheduleActivity.class);

    Context appContext;
    private Session session;

    //This constructor is initiated before the acitivty is started. In order to start the activity we NEED a session
    //A Set-up method is initiated only after we have the activity running, making it unusable for our case.
    public ScheduleActivityTest(){
        appContext = InstrumentationRegistry.getTargetContext();
        session = new Session(appContext);
        session.setUser(new Staff());
    }

    @Before
    public void setUp() throws Exception {
        //if the Looper has not been prepared yet, initialize it. This is needed, else Toasts used in the method will cause it to crash.
        if(Looper.myLooper() == null){
            Looper.prepare();
        }
    }
    @Test //C4.1 lesson has not been filled in
    public void clickReserve1() throws Exception{
            rule.getActivity().ClickReserve(new View(appContext));
            String getfromactivity = rule.getActivity().status_stringstatus;
            assertEquals("Select Timeslots",getfromactivity);
    }
    @Test //C4.2 lesson has not been filled in
    public void clickReserve2() throws Exception{
            Field field = ScheduleActivity.class.getDeclaredField("selectedBookings");
            field.setAccessible(true);
            ArrayList<TimeDay> test = new ArrayList<TimeDay>();
            test.add(new TimeDay(2,2));
            field.set(rule.getActivity(),test);
            rule.getActivity().ClickReserve(new View(appContext));
            String getfromactivity = rule.getActivity().status_stringstatus;
            assertEquals("Fill in a lesson code",getfromactivity);
    }
    @Test //C4.3 no room has been selected
    public void clickReserve3() throws Exception{
            Field field = ScheduleActivity.class.getDeclaredField("selectedBookings");
            field.setAccessible(true);
            ArrayList<TimeDay> test = new ArrayList<TimeDay>();
            test.add(new TimeDay(2,2));
            field.set(rule.getActivity(),test);

            EditText lessonfield = (EditText)rule.getActivity().findViewById(R.id.lesson);
            lessonfield.setText("C43TEST");

            rule.getActivity().ClickReserve(new View(appContext));
            String getfromactivity = rule.getActivity().status_stringstatus;
            assertEquals("Choose a room",getfromactivity);
    }
    @Test //C4.4 leverything should work out
    public void clickReserve4() throws Exception {
            Field field_selected_timeslots = ScheduleActivity.class.getDeclaredField("selectedBookings");
            field_selected_timeslots.setAccessible(true);
            ArrayList<TimeDay> test = new ArrayList<TimeDay>();
            test.add(new TimeDay(2, 2));
            field_selected_timeslots.set(rule.getActivity(), test);

            EditText lessonfield = (EditText) rule.getActivity().findViewById(R.id.lesson);
            lessonfield.setText("C44TEST");

            Field field_selected_room = ScheduleActivity.class.getDeclaredField("currentRoom");
            field_selected_room.setAccessible(true);
            field_selected_room.set(rule.getActivity(), "WN.01.001");

            rule.getActivity().ClickReserve(new View(appContext));
            String getfromactivity = rule.getActivity().status_stringstatus;
            //this booking should already exist. Should return already booked
            assertEquals("Already booked!",getfromactivity);
    }
    @Test  //C4.5 control
    public void clickReserve5() throws Exception {
        String getfromactivity = rule.getActivity().status_stringstatus;
        assertEquals("Not yet saved",getfromactivity);
    }

    //TODO
    //calendar weekdate does not seem to work as intended.
    @Test //C14.1 start new year
    public void onClickNextWeek1() throws Exception{
        WeekDate sourceweekdate = new WeekDate();
        sourceweekdate.GetWeek();

        Field field_cal = WeekDate.class.getDeclaredField("cal");
        field_cal.setAccessible(true);
        field_cal.set(sourceweekdate,sourceweekdate.GetCalendarSetAtWeek(51));
        //GetCalendarSetAtWeek does not seem to have any effect ^^^^

        Field field_weekdate = ScheduleActivity.class.getDeclaredField("weekDate");
        field_weekdate.setAccessible(true);
        field_weekdate.set(rule.getActivity(),sourceweekdate);

        rule.getActivity().onClickNextWeek(new View(appContext));

        assertEquals(1,sourceweekdate.GetWeek());
        assertEquals(2018,sourceweekdate.GetYear());

    }
}