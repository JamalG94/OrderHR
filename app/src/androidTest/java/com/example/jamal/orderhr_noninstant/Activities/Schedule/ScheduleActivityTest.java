package com.example.jamal.orderhr_noninstant.Activities.Schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.jamal.orderhr_noninstant.Datastructures.Staff;
import com.example.jamal.orderhr_noninstant.Session.Session;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

//        // Context of the app under test.

//
//        try{
//            Looper.prepare();
//            Intent test = new Intent(appContext,ScheduleActivity.class);
//            appContext.startActivity(test);
//        }catch(Exception e){
//
//        }
    }

    @Test
    public void clickReserve() throws Exception {
        String getfromactivity = rule.getActivity().status_stringstatus;
        assertEquals(getfromactivity,"Not yet saved");
    }

}