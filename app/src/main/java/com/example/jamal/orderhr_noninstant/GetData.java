package com.example.jamal.orderhr_noninstant;

/**
 * Created by Robin on 5/3/2018.
 */

public final class GetData {
    static public String checkbooking(String Json){
        String temp = "";
        String returnf = "";
        IO instance = new IO();
        try{
            temp = instance.execute("http://markb.pythonanywhere.com/availableslot/",Json).get();
            //temp = instance.doInBackground("http://markb.pythonanywhere.com/availableslot/",Json);
        }catch(Exception e){}finally{
            returnf = temp;
        }

        return returnf;
    }
}
