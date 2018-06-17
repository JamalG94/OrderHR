package com.example.jamal.orderhr_noninstant.Activities.EasyScan;

import android.content.Context;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.jamal.orderhr_noninstant.Activities.EasyScan.EasyScanActivity;
import com.example.jamal.orderhr_noninstant.Datastructures.Staff;
import com.example.jamal.orderhr_noninstant.Datastructures.Student;
import com.example.jamal.orderhr_noninstant.Session.Session;

import junit.framework.Assert;
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
        //if the Looper has not been prepared yet, initialize it. This is needed, else Toasts used in the method will cause it to crash.
        if(Looper.myLooper() == null){
            Looper.prepare();
        }
        appContext = InstrumentationRegistry.getTargetContext();
        session = new Session(appContext);

    }


    //Tests the flow if an defunct json gets parsed
    @Test
    public void testQRDefunct(){
        String json = "{defunct:{}}";
        String statusExpected = "defunct";

        EasyScanActivity.GetNextIntentFromInputJson(json, appContext);
        String statusGiven = EasyScanActivity.pathStatus;

        Assert.assertEquals(statusExpected, statusGiven);
    }

    //Tests if an staff member tries to reserve through QR
    @Test
    public void testQRReservationStaff(){
        String json = "{reservation:{}}";
        session.setUser(new Staff());

        String statusExpected = "reservation";
        EasyScanActivity.GetNextIntentFromInputJson(json, appContext);
        String statusGiven = EasyScanActivity.pathStatus;

        Assert.assertEquals(statusExpected, statusGiven);
    }

    //Tests if an student tries to reserve through QR
    @Test
    public void testQRReservationStudent(){
        String json = "{reservation:{}}";
        session.setUser(new Student());

        String statusExpected = "no permission";
        EasyScanActivity.GetNextIntentFromInputJson(json, appContext);
        String statusGiven = EasyScanActivity.pathStatus;

        Assert.assertEquals(statusExpected, statusGiven);
    }

    //Tests the EasyScan class if a gibberish json is parsed
    @Test
    public void testQRGibberish(){
        String json = "{abc:{}}";
        String statusExpected = "JSON FORMAT WITH QR NOT RECOGNIZED";

        EasyScanActivity.GetNextIntentFromInputJson(json, appContext);
        String statusGiven = EasyScanActivity.pathStatus;

        Assert.assertEquals(statusExpected, statusGiven);
    }



}
