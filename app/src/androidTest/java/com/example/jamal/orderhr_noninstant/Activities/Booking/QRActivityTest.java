package com.example.jamal.orderhr_noninstant.Activities.Booking;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.jamal.orderhr_noninstant.Activities.Defuncts.DefunctMakeByQRJsonActivity;
import com.example.jamal.orderhr_noninstant.Activities.EasyScan.EasyScanActivity;
import com.example.jamal.orderhr_noninstant.Datastructures.Staff;
import com.example.jamal.orderhr_noninstant.Datastructures.Student;
import com.example.jamal.orderhr_noninstant.Session.Session;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

/**
 * Created by jamal on 6/15/2018.
 */


@RunWith(AndroidJUnit4.class)
public class QRActivityTest {

    Context appContext;
    Session session;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
        session = new Session(appContext);

    }

    @Test
    public void testQRDefunct(){
        String json = "{defunct:{}}";
        String statusExpected = "defunct";

        EasyScanActivity.GetNextIntentFromInputJson(json, appContext);
        String statusGiven = EasyScanActivity.pathStatus;

        Assert.assertEquals(statusExpected, statusGiven);
    }

    @Test
    public void testQRReservationStaff(){
        String json = "{reservation:{}}";
        session.setUser(new Staff());

        String statusExpected = "reservation";
        EasyScanActivity.GetNextIntentFromInputJson(json, appContext);
        String statusGiven = EasyScanActivity.pathStatus;

        Assert.assertEquals(statusExpected, statusGiven);
    }

    @Test
    public void testQRReservationStudent(){
        try{
            Looper.prepare();

        }catch(Exception e){
            fail();
        }

        String json = "{reservation:{}}";
        session.setUser(new Student());

        String statusExpected = "no permission";
        EasyScanActivity.GetNextIntentFromInputJson(json, appContext);
        String statusGiven = EasyScanActivity.pathStatus;

        Assert.assertEquals(statusExpected, statusGiven);
    }


}
