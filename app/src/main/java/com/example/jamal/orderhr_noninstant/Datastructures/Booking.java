package com.example.jamal.orderhr_noninstant.Datastructures;

import android.content.Intent;

import org.json.JSONObject;

import java.util.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Robin on 3/22/2018.
 */

public class Booking implements IEasyScannable{
    public int reservationid,timeslotto,timeslotfrom;
    public String Username,Lesson,Room;
    public java.util.Date TimeFrom, TimeTo, DateOn;
    public EnumParseStatus parsestatus = EnumParseStatus.ERROR;

    public void parsedata(JSONObject json){
        try {
            Username = json.optJSONObject("Reservation").optString("Username");
            Lesson = json.optJSONObject("Reservation").optString("Lesson");
            Room =json.optJSONObject("Reservation").optString("Room");

            DateFormat formatter = new SimpleDateFormat("hh:mm");
            TimeTo = formatter.parse(json.optJSONObject("Reservation").optString("TimeTo"));
            TimeFrom = formatter.parse(json.optJSONObject("Reservation").optString("TimeFrom"));

            formatter = new SimpleDateFormat("DD-MM-YYYY");
            DateOn= formatter.parse(json.optJSONObject("Reservation").optString("Date"));

            timeslotfrom= json.optJSONObject("Reservation").optInt("TimeSlotFrom");
            timeslotto= json.optJSONObject("Reservation").optInt("TimeSlotTo");
            parsestatus = EnumParseStatus.PARSED;
        }
        catch(Exception e){

        }
    }

}
