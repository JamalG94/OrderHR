package com.example.jamal.orderhr_noninstant.Activities.Booking;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import com.example.jamal.orderhr_noninstant.API.IO;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Created by jamal on 6/16/2018.
 */

public class BookingMakeByQRTest {

    Context appContext;
    BookingMakeByQRJsonActivity activity;

    @Rule
    public ActivityTestRule<BookingMakeByQRJsonActivity> rule  = new  ActivityTestRule<BookingMakeByQRJsonActivity>(BookingMakeByQRJsonActivity.class)
    {
        @Override
        protected Intent getActivityIntent() {
            InstrumentationRegistry.getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra("jsonparser", "{\"reservation\": {\"timeslotfrom\": 6, \"timeslotto\": 9, \"date\": \"13-6-2018\", \"room\": \"H.3.306\"}}");
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

    //Test case for when a unrealistic timeslot gets passed in the json
    @Test
    public void TimeSlotOutOfRange(){
        try {
            BookingMakeByQRJsonActivity.initiateBookingDataFromJson("{\"reservation\": {\"timeslotfrom\": 4, \"timeslotto\": 17, \"date\": \"17-6-2018\", \"room\": \"H.3.403\"}}");

        }
        catch (IOException e){
            Log.d("IOException", "wrongJson: IOException");
        }
        catch(IndexOutOfBoundsException e){
            //We expect an indexOutofbounds since timeslotto is not filled in and automatically converted to 0, 0 is not a timeslot in TimeSlotConverter.
            Log.d("IndexOutofBounds", "wrongJson: IndexBoundsException");
            assertEquals("INVALID TIMESLOT: " + 17, e.getMessage());
        }
        catch (JSONException e){
            Log.d("JsonException", "wrongJson: JsonException");
        }
        catch (NullPointerException e){
            Log.d("NullPointer", "wrongJson: NullPointer");
        }
    }

    //Test case for when the json has additional fields
    @Test
    public void TooManyFields(){
        Boolean given = false;
        try {
            Booking givenBooking = BookingMakeByQRJsonActivity.initiateBookingDataFromJson("{\"reservation\": {\"timeslotfrom\": 4, \"timeslotto\": 4, \"date\": \"17-6-2018\", \"room\": \"H.3.403\", \"randomField\" : \"Random123\"}}");
        }
        catch (IOException e){
            //We expect an IOException since the objectmapper finds an unrecognized field
            Log.d("IOException", "wrongJson: IOException");
            //We caught an IOException!
            given = true;

        }
        catch(IndexOutOfBoundsException e){
            Log.d("IndexOutofBounds", "wrongJson: IndexBoundsException");
        }
        catch (JSONException e){
            Log.d("JsonException", "wrongJson: JsonException");
        }
        catch (NullPointerException e){
            Log.d("NullPointer", "wrongJson: NullPointer");
        }
        assertEquals(true, given);
    }

    //Test case for when the json doesn't have all required fields
    @Test
    public void NotAllFields(){
        Boolean given = false;
        try {
            Booking givenBooking = BookingMakeByQRJsonActivity.initiateBookingDataFromJson("{\"reservation\": {\"timeslotfrom\": 4, \"timeslotto\": 4, \"date\": \"17-6-2018\"}}");
        }
        catch (IOException e){
            Log.d("IOException", "wrongJson: IOException");
        }
        catch(IndexOutOfBoundsException e){
            Log.d("IndexOutofBounds", "wrongJson: IndexBoundsException");
        }
        catch (JSONException e){
            Log.d("JsonException", "wrongJson: JsonException");
        }
        catch (NullPointerException e){
            //We expect an Nullpointer since the room has been left out of the reservation json.
            Log.d("NullPointer", "wrongJson: NullPointer");
            //We caught an NullpointerException!
            given = true;
        }
        assertEquals(true, given);
    }

    //This test is supposed to fail, proving that setDateformat in the objectmapper still accepts other date formats
    @Test
    public void WrongFormatDate(){
        Boolean given = false;
        try {
            Booking givenBooking = BookingMakeByQRJsonActivity.initiateBookingDataFromJson("{\"reservation\": {\"timeslotfrom\": 4, \"timeslotto\": 4, \"date\": \"08-15-2018\", \"room\": \"H.3.403\"}}");
        }
        catch (IOException e){
            //We expect an IOException since the objectmapper finds an unrecognized date format.
            Log.d("IOException", "wrongJson: IOException");
            given = true;
        }
        catch(IndexOutOfBoundsException e){
            Log.d("IndexOutofBounds", "wrongJson: IndexBoundsException");
        }
        catch (JSONException e){
            Log.d("JsonException", "wrongJson: JsonException");
        }
        catch (NullPointerException e){
            Log.d("NullPointer", "wrongJson: NullPointer");
        }
        assertEquals(true, given);
    }

    //Test case when a wrong json gets parsed to create a booking
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

    //Test case when a user tries to book on a reserved slot
    @Test
    public void NoSlotAvailable(){
        activity.onClickSaveBooking(new View(appContext));

        //We expect that the timeslot is already taken.
        assertEquals("Failed! Try generating on another timeslot!",activity.getStatus_stringstatus());
    }

    //Test case when a user hasn't filled in lesson and class
    @Test
    public void TextNotFilled(){
        activity.setInitial_available(true);
        activity.onClickSaveBooking(new View(appContext));
        assertEquals("Failed! Fill in Lesson and Class fields!", activity.getStatus_stringstatus());
    }

//    @Test
//    public void SaveBooking(){
//        activity.setInitial_available(true);
//        activity.setTexteditclass("INF3C");
//        activity.setTexteditlesson("ICTLAB");
//        activity.onClickSaveBooking(new View(appContext));
//    }
//
//    @After
//    public void TearDown(){
//        IO _IO = IO.GetInstance();
//        String json = "{room\":\"H.3.306\",  \"weeknummer\":\"24}";
//        _IO.DoPostRequestToAPIServer(json, "http://markb.pythonanywhere.com/bookingbyroom/", activity);
//    }


}
