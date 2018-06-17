package com.example.jamal.orderhr_noninstant.Activities.Booking;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.example.jamal.orderhr_noninstant.Datastructures.Booking;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Created by jamal on 6/16/2018.
 */

public class BookingMakeByQRNotAllFieldsTest {

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
    public void TimeSlotOutOfRange(){
        try {
            BookingMakeByQRJsonActivity.initiateBookingDataFromJson("{\"reservation\": {\"timeslotfrom\": 4, \"date\": \"17-6-2018\", \"room\": \"H.3.403\"}}");

        }
        catch (IOException e){
            Log.d("IOException", "wrongJson: IOException");
        }
        catch(IndexOutOfBoundsException e){
            //We expect an indexOutofbounds since timeslotto is not filled in and automatically converted to 0, 0 is not a timeslot in TimeSlotConverter.
            Log.d("IndexOutofBounds", "wrongJson: IndexBoundsException");
            assertEquals("INVALID TIMESLOT: " + 0, e.getMessage());
        }
        catch (JSONException e){
            Log.d("JsonException", "wrongJson: JsonException");
        }
        catch (NullPointerException e){
            Log.d("NullPointer", "wrongJson: NullPointer");
        }
    }

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
}
