package com.example.jamal.orderhr_noninstant.Datastructures;

import com.example.jamal.orderhr_noninstant.NoName2DBAPI;

import org.json.JSONObject;

import java.util.Date;
import java.sql.Time;

/**
 * Created by Robin on 3/22/2018.
 */

public class Booking {
    public int reservationid,timeslotto,timeslotfrom;
    public String Username,Lesson,Room;
    public Time TimeFrom,TimeTo;
    public Date DateOn;

    public Booking(int inputreservationid){
        reservationid  = inputreservationid;
    }

    public void GetBooking(){
        String result = "";
        NoName2DBAPI apicaller = new NoName2DBAPI();
        String url = "https://1f1bd618-6842-4b57-b340-53dde06fd3f2.mock.pstmn.io/getschedule";
        try{
            result = apicaller.execute(url).get();
        }catch(Exception e){

        }finally {
            try {
                JSONObject jsonparser = new JSONObject(result);
                Username = jsonparser.optJSONObject("Reservation").optString("Username");
                Lesson = jsonparser.optJSONObject("Reservation").optString("Lesson");
                Room =jsonparser.optJSONObject("Reservation").optString("Room");
                TimeFrom = java.sql.Time.valueOf(jsonparser.optJSONObject("Reservation").optString("TimeFrom"));
                TimeTo= java.sql.Time.valueOf(jsonparser.optJSONObject("Reservation").optString("TimeTo"));
                DateOn= java.sql.Date.valueOf(jsonparser.optJSONObject("Reservation").optString("Date"));
                timeslotfrom= jsonparser.optJSONObject("Reservation").optInt("TimeSlotFrom");
                timeslotto= jsonparser.optJSONObject("Reservation").optInt("TimeSlotTo");
            }
            catch(Exception e){

            }
        }
    }
}
