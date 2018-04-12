package com.example.jamal.orderhr_noninstant;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Robin on 3/29/2018.
 */

public class IO extends AsyncTask<String,Void, String> {

    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private static ObjectMapper objectMapper = new ObjectMapper();

    private String url = "";

    //Singleton
    private static IO _instance = null;

    private IO(String apiUrl)
    {
        this.url = apiUrl;

    }

    public static IO GetInstance(String apiUrl)
    {
        if (_instance == null) _instance = new IO(apiUrl);
        return _instance;
    }

    public String GetData(int id){

        String resultstring = "";
        try{
            resultstring = this.execute(url).get();
        }
        catch(Exception e){
            Log.d("GetDataGoneWrong", e.toString());
        }
        return resultstring;
    }


    @Override
    protected String doInBackground(String... params){
        String stringUrl = params[0];
        String result = "";
        String inputLine;
        try{
            //create a url object holding our url
            URL myUrl = new URL(stringUrl);

            //Create a connection
            HttpURLConnection connection =
                    (HttpURLConnection) myUrl.openConnection();

            //set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);

            connection.setReadTimeout(READ_TIMEOUT);

            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
        }
        catch(IOException e) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }

}


