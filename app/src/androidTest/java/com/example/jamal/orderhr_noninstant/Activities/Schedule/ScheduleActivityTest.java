package com.example.jamal.orderhr_noninstant.Activities.Schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.jamal.orderhr_noninstant.Activities.Defuncts.ViewDefunctDetailsActivity;
import com.example.jamal.orderhr_noninstant.Datastructures.Staff;
import com.example.jamal.orderhr_noninstant.Datastructures.TimeDay;
import com.example.jamal.orderhr_noninstant.Session.Session;

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
@RunWith(AndroidJUnit4.class)
public class ScheduleActivityTest {
    @Rule
     public ActivityTestRule<ScheduleActivity> rule = new ActivityTestRule<ScheduleActivity>(ScheduleActivity.class);

    Context appContext;
//    ScheduleActivity targetactivity;
    private Session session;

    public ScheduleActivityTest(){
        appContext = InstrumentationRegistry.getTargetContext();

        session = new Session(appContext);
        session.setUser(new Staff());
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void clickReserve() throws Exception {
        String getfromactivity = rule.getActivity().status_stringstatus;
        assertEquals(getfromactivity,"Not yet saved");
    }
    @Test
    public void clickedReserve() throws Exception{
        try{
            Looper.prepare();

            Field method = ScheduleActivity.class.getDeclaredField("selectedBookings");
            method.setAccessible(true);
            ArrayList<TimeDay> test = new ArrayList<TimeDay>();
            test.add(new TimeDay(2,2));
            method.set(rule.getActivity(),test);
            rule.getActivity().ClickReserve(new View(appContext));
            String getfromactivity = rule.getActivity().status_stringstatus;
            assertEquals(getfromactivity,"Fill in a lesson code");

        }catch(Exception e){

        }

//        rule.getActivity().
//        String getfromactivity = rule.getActivity().status_stringstatus;
//        assertEquals(getfromactivity,"Not yet saved");
    }
}