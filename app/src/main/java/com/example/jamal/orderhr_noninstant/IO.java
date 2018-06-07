package com.example.jamal.orderhr_noninstant;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Robin on 3/29/2018.
 */

public class IO extends AsyncTask<String,String, String> {
//    public IO() {
//        super();
//    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    private static final String REQUEST_METHOD = "POST";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;



    //Singleton
    private static IO _instance = null;

    private IO()
    {
        super();

    }

    public static IO GetInstance()
    {
       _instance = new IO();
        return _instance;
    }



    @Override
    protected String doInBackground(String... params){
        String stringUrl = params[0];
        String json = params[1];
        String result = "";
        String inputLine;
        try {
//            HttpResponseCache client = new DefaultHttpClient();


            URL url = new URL(stringUrl); //in the real code, there is an ip and a port
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","Text");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.connect();



            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
//                os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(json);
            os.flush();
            String output ="";
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            while((output = br.readLine()) != null){
                sb.append(output);
            }
            result= sb.toString();


            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , result);
            conn.disconnect();
            os.close();






        } catch (Exception e) {
            Log.i("error","..");
        }


        return result;
    }

//    @Override
//    protected Object doInBackground(Object[] objects) {
//        t
//    }



    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }


    public String DoPostRequestToAPIServer(String RawJsonStringToInput, String ApiURL, Activity context) {
//        GetInstance("");
        String temp = "";
        String returnf = "";

        try{
            temp = this.execute(ApiURL,RawJsonStringToInput).get();
//            Toast.makeText(context, temp, Toast.LENGTH_SHORT).show();
        }catch(ExecutionException | InterruptedException e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }finally{
            returnf = temp;
        }

        return returnf;
    }

}


