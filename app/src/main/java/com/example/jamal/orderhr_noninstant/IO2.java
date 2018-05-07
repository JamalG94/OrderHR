package com.example.jamal.orderhr_noninstant;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IO2 extends AsyncTask<String,String,String>{
    public IO2() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
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

    @Override
    protected String doInBackground(String... strings) {
        String test= "";
        try {
//            HttpResponseCache client = new DefaultHttpClient();


            URL url = new URL(strings[0]); //in the real code, there is an ip and a port
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","Text");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.connect();


            try{
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
//                os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(strings[1]);
                os.flush();
                String output ="";
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                StringBuilder sb = new StringBuilder();
                while((output = br.readLine()) != null){
                    sb.append(output);
                }
                test= sb.toString();
                os.close();

            }finally{
                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , test);
                conn.disconnect();
            }






        } catch (Exception e) {
            Log.i("error","..");
        }


        return test;
    }
}