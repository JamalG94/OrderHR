package com.example.jamal.orderhr_noninstant.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.jamal.orderhr_noninstant.Activities.EasyScan.EasyScanActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Robin on 5/31/2018.
 */
@RunWith(AndroidJUnit4.class)
public class EasyScanActivityTest {
    Context appContext;
    @Before
    public void setUp() throws Exception {
        // Context of the app under test.
         appContext = InstrumentationRegistry.getTargetContext();
    }

//    @Test
//    public void validateIfJsonInputIsValid() throws Exception {
//        assert(EasyScanActivity.ValidateIfJsonInputIsValid("This is not a json"));
//        assert(EasyScanActivity.ValidateIfJsonInputIsValid("{\"type\":\"json\"})"));
//        assert(EasyScanActivity.ValidateIfJsonInputIsValid("{\"type\":brokenjson})"));
//    }

    @Test
    public void getNextIntentFromInputJson() throws Exception {
//        assertEquals(EasyScanActivity.GetNextIntentFromInputJson("This is not a json",appContext).cls,MainActivity.class);
        Log.d("help","reached");
        Intent hello = new Intent();
        try{
            Looper.prepare();

        }catch(Exception e){
            fail();
        }
    }
}