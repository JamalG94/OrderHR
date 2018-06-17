package com.example.jamal.orderhr_noninstant.Activities.Booking;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.jamal.orderhr_noninstant.Activities.Defuncts.DefunctMakeByQRJsonActivity;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Robin on 6/13/2018.
 */


@RunWith(AndroidJUnit4.class)
public class BookingMakeByQRWrongJSONtest {

    Context appContext;
    BookingMakeByQRJsonActivity activity;

    @Rule
    public ActivityTestRule<BookingMakeByQRJsonActivity> rule  = new  ActivityTestRule<BookingMakeByQRJsonActivity>(BookingMakeByQRJsonActivity.class)
    {
        @Override
        protected Intent getActivityIntent() {
            InstrumentationRegistry.getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra("jsonparser", "{\"reservation\": {\"timeslotfrom\": 4, \"timeslotto\": 4, \"date\": \"17-6-2018\", \"room\": \"H.3.403\"}}");
            return intent;
        }
    };


    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
        activity = rule.getActivity();
        //if the Looper has not been prepared yet, initialize it. This is needed, else Toasts used in the method will cause it to crash.
        if(Looper.myLooper() == null){
            Looper.prepare();
        }

    }

    @Test
    public void wrongJson(){
        String no_json_format = " FDf335";

        try{
            BookingMakeByQRJsonActivity.initiateBookingDataFromJson(no_json_format);
            fail("expected json exception");
        }catch(JSONException e){
            assert(e.getMessage().equals("Value FDf335 of type java.lang.String cannot be converted to JSONObject"));
        }
        catch (IOException e){
        }
    }


//    @Test
//    public void initiateBookingDataFromJson() throws Exception {
//        String no_json_format = " FDf335";
//        String wrong_json_format = "{\"timeslotfrom\": 4, \"timeslotto\": 4, \"date\": \"6-17-2018\", \"room\": \"H.3.403\"}";
//        String correct_json_format = "{\"reservation\": {\"timeslotfrom\": 4, \"timeslotto\": 4, \"date\": \"17-6-2018\", \"room\": \"H.3.403\"}}";
//        String correct_json_wrong_date_format = "{\"reservation\": {\"timeslotfrom\": 4, \"timeslotto\": 4, \"date\": \"6-17-2018\", \"room\": \"H.3.403\"}}";
//        try{
//            Booking booking_no_json_format_result = BookingMakeByQRJsonActivity.initiateBookingDataFromJson(no_json_format);
//            fail("expected json exception");
//        }catch(JSONException e){
//
//            assert(e.getMessage().equals(""));
//        }
//        try{
//            Booking booking_wrong_json_format_result = BookingMakeByQRJsonActivity.initiateBookingDataFromJson(wrong_json_format);
//            fail("expected json exception");
//        }catch(JSONException e){
//            assert(e.getMessage().equals(""));
//        }
//        Booking booking_wrong_json_format_result = BookingMakeByQRJsonActivity.initiateBookingDataFromJson(wrong_json_format);
//        Booking booking_correct_json_format_result = BookingMakeByQRJsonActivity.initiateBookingDataFromJson(correct_json_format);
//        Booking booking_correct_json_wrong_date_format_result = BookingMakeByQRJsonActivity.initiateBookingDataFromJson(correct_json_wrong_date_format);
//
//    }

}