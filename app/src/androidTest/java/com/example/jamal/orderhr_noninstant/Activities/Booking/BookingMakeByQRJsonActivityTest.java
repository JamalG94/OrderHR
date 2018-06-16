package com.example.jamal.orderhr_noninstant.Activities.Booking;

import android.support.test.runner.AndroidJUnit4;

import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Robin on 6/13/2018.
 */

@RunWith(AndroidJUnit4.class)
public class BookingMakeByQRJsonActivityTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void onClickSaveBooking() throws Exception {
    }

    @Test
    public void initiateBookingDataFromJson() throws Exception {
        String no_json_format = " FDf335";
        String wrong_json_format = "{\"timeslotfrom\": 4, \"timeslotto\": 4, \"date\": \"6-17-2018\", \"room\": \"H.3.403\"}";
        String correct_json_format = "{\"reservation\": {\"timeslotfrom\": 4, \"timeslotto\": 4, \"date\": \"17-6-2018\", \"room\": \"H.3.403\"}}";
        String correct_json_wrong_date_format = "{\"reservation\": {\"timeslotfrom\": 4, \"timeslotto\": 4, \"date\": \"6-17-2018\", \"room\": \"H.3.403\"}}";
        try{
            Booking booking_no_json_format_result = BookingMakeByQRJsonActivity.initiateBookingDataFromJson(no_json_format);
            fail("expected json exception");
        }catch(JSONException e){
            assert(e.getMessage().equals(""));
        }
        try{
            Booking booking_wrong_json_format_result = BookingMakeByQRJsonActivity.initiateBookingDataFromJson(wrong_json_format);
            fail("expected json exception");
        }catch(JSONException e){
            assert(e.getMessage().equals(""));
        }
        Booking booking_wrong_json_format_result = BookingMakeByQRJsonActivity.initiateBookingDataFromJson(wrong_json_format);
        Booking booking_correct_json_format_result = BookingMakeByQRJsonActivity.initiateBookingDataFromJson(correct_json_format);
        Booking booking_correct_json_wrong_date_format_result = BookingMakeByQRJsonActivity.initiateBookingDataFromJson(correct_json_wrong_date_format);

    }

}