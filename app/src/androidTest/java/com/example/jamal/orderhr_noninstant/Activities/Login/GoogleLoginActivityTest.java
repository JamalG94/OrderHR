package com.example.jamal.orderhr_noninstant.Activities.Login;

import android.os.Looper;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.jamal.orderhr_noninstant.Activities.Schedule.ScheduleActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by Robin on 6/16/2018.
 */
@RunWith(AndroidJUnit4.class)
public class GoogleLoginActivityTest {


    @Rule
    public ActivityTestRule<GoogleLoginActivity> rule = new ActivityTestRule<GoogleLoginActivity>(GoogleLoginActivity.class);

    @Before
    public void setUp() throws Exception {
        //if the Looper has not been prepared yet, initialize it. This is needed, else Toasts used in the method will cause it to crash.
        if(Looper.myLooper() == null){
            Looper.prepare();
        }
    }
    @Test //C5.1
    public void EmailChecktest1() throws Exception {
        String inputmail = "0907313@hr.nl";

        Method totestmethod = GoogleLoginActivity.class.getDeclaredMethod("EmailCheck", String.class);
        totestmethod.setAccessible(true);
        assertEquals(true,totestmethod.invoke(rule.getActivity(),inputmail));
    }
    @Test //C5.2
    public void EmailChecktest2() throws Exception {
        String inputmail = "0907313@hotmail.nl";

        Method totestmethod = GoogleLoginActivity.class.getDeclaredMethod("EmailCheck", String.class);
        totestmethod.setAccessible(true);
        assertEquals(false,totestmethod.invoke(rule.getActivity(),inputmail));
    }
    @Test //C5.3
    public void EmailChecktest3() throws Exception {
        String inputmail = "0907313";

        Method totestmethod = GoogleLoginActivity.class.getDeclaredMethod("EmailCheck", String.class);
        totestmethod.setAccessible(true);
        assertEquals(false,totestmethod.invoke(rule.getActivity(),inputmail));
    }
}