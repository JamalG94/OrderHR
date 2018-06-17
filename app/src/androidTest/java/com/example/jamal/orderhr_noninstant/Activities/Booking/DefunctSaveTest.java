package com.example.jamal.orderhr_noninstant.Activities.Booking;

import android.content.Context;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.jamal.orderhr_noninstant.Activities.Defuncts.DefunctMakeByQRJsonActivity;
import com.example.jamal.orderhr_noninstant.Activities.Schedule.ScheduleActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Created by jamal on 6/16/2018.
 */


@RunWith(AndroidJUnit4.class)
public class DefunctSaveTest {

    Context appContext;
    DefunctMakeByQRJsonActivity activity;

    @Rule
    public ActivityTestRule<DefunctMakeByQRJsonActivity> rule = new ActivityTestRule<>(DefunctMakeByQRJsonActivity.class);

    @Before
    public void setUp(){
        appContext = InstrumentationRegistry.getTargetContext();
        activity = rule.getActivity();
        //if the Looper has not been prepared yet, initialize it. This is needed, else Toasts used in the method will cause it to crash.
        if(Looper.myLooper() == null){
            Looper.prepare();
        }
    }

    //Test case when user hasn't filled in a description for the defunct
    @Test
    public void EmptyString(){
        activity.setTextviewdescription("");
        activity.onClickSaveDefunct(new View(appContext));

        assertEquals("Please fill in the Description field", activity.getSave_string_status());
    }

    //Test case when a random description gets filled in by the user for the defunct
    @Test
    public void GibberishString(){
        activity.setTextviewdescription("%s3412145a;fd;a");
        activity.onClickSaveDefunct(new View(appContext));

        assertEquals("Something went wrong with saving the data! Do you have a connection?", activity.getSave_string_status());
    }

    //Test case when a realistic string gets filled in by the user for the defunct
    @Test
    public void RealisticString(){
        activity.setTextviewdescription("EMP Strike");
        activity.onClickSaveDefunct(new View(appContext));

        Boolean given = !activity.getSave_string_status().equals("Something went wrong with saving the data! Do you have a connection?");

        assertEquals(true, given);
    }



}


