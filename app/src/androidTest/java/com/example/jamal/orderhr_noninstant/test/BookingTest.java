//package com.example.jamal.orderhr_noninstant.test;
//
//import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
//import com.example.jamal.orderhr_noninstant.Datastructures.EnumParseStatus;
//
//import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.Date;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//
//import static org.junit.Assert.*;
//
///**
// * Created by Robin on 3/29/2018.
// */
//public class BookingTest {
//    public JSONObject testinput;
//    public Booking targetunit;
//    public Booking expectedbooking;
//    @Before
//    public void setUp() throws Exception {
//        String inputjason = "{ \"Reservation\":{ \"ReservationID\":10, \"TimeSlotFrom\":2, \"TimeSlotTo\":3, \"TimeFrom\":\"10:50\", \"TimeTo\":\"11:40\", \"Date\":\"12-03-2012\", \"Username\":\"OmarA\", \"Lesson\":\"ICTLAB\", \"Room\":\"WN4.101\" } }";
//        testinput = new JSONObject(inputjason);
//
//
//        targetunit = new Booking();
//        targetunit.reservationid = 10;
//
//        expectedbooking =  new Booking();
//        expectedbooking.reservationid = 10;
//        expectedbooking.parsestatus = EnumParseStatus.PARSED;
//        expectedbooking.timeslotto =  3;
//        expectedbooking.timeslotfrom = 2;
//        DateFormat formatter = new SimpleDateFormat("hh:mm");
//        expectedbooking.TimeTo = formatter.parse("11:40");
//        expectedbooking.TimeFrom = formatter.parse("10:50");
//
//         formatter = new SimpleDateFormat("DD-MM-YYYY");
//        expectedbooking.DateOn = formatter.parse("12-03-2012");
//        expectedbooking.Username = "OmarA";
//        expectedbooking.Lesson = "ICTLAB";
//        expectedbooking.Room = "WN4.101";
//    }
//
//    @Test
//    public void parsedata() throws Exception {
//        targetunit.parsedata(testinput);
//        assertEquals(targetunit.reservationid,expectedbooking.reservationid);
//        assertEquals(targetunit.timeslotfrom,expectedbooking.timeslotfrom);
//        assertEquals(targetunit.timeslotto,expectedbooking.timeslotto);
//        assertEquals(targetunit.Room,expectedbooking.Room);
//        assertEquals(targetunit.DateOn,expectedbooking.DateOn);
//        assertEquals(targetunit.parsestatus,expectedbooking.parsestatus);
//        assertEquals(targetunit.Username,expectedbooking.Username);
//        assertEquals(targetunit.Lesson,expectedbooking.Lesson);
//        assertEquals(targetunit.TimeFrom,expectedbooking.TimeFrom);
//        assertEquals(targetunit.TimeTo,expectedbooking.TimeTo);
//    }
//
//}