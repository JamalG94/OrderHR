package com.example.jamal.orderhr_noninstant;

import com.example.jamal.orderhr_noninstant.Datastructures.Booking;

import org.json.JSONObject;

/**
 * Created by Robin on 3/29/2018.
 */

public final class IO {

    public static Booking Getbookingbyid(int id){

        NoName2DBAPIgetter asyncapicaller = new NoName2DBAPIgetter();
        String resultstring = "";
        Booking resultbooking = new Booking();
        resultbooking.reservationid = id;

        String url = "https://1f1bd618-6842-4b57-b340-53dde06fd3f2.mock.pstmn.io/getschedule";
        try{
            resultstring = asyncapicaller.execute(url).get();
        }catch(Exception e){

        }finally {
            try{resultbooking.parsedata(new JSONObject(resultstring));}catch(Exception e){}
        }
        return resultbooking;
    }



}


