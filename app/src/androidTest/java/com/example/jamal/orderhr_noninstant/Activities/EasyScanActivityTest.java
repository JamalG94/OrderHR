package com.example.jamal.orderhr_noninstant.Activities;

import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Robin on 5/31/2018.
 */
public class EasyScanActivityTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void validateIfJsonInputIsValid() throws Exception {
        assert(EasyScanActivity.ValidateIfJsonInputIsValid("This is not a json"));
        assert(EasyScanActivity.ValidateIfJsonInputIsValid("{\"type\":\"json\"})"));
        assert(EasyScanActivity.ValidateIfJsonInputIsValid("{\"type\":brokenjson})"));
    }

    @Test
    public void getNextIntentFromInputJson() throws Exception {
        try{
            Looper.prepare();
            EasyScanActivity.GetNextIntentFromInputJson("This is not a json",new EasyScanActivity());
            Log.d("help","reached");
        }catch(Exception e){

        }finally{

        }

    }

}