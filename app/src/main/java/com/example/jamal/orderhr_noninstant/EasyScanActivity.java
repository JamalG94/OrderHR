package com.example.jamal.orderhr_noninstant;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.Datastructures.IEasyScannable;
import com.example.jamal.orderhr_noninstant.Datastructures.NullScannable;
import com.example.jamal.orderhr_noninstant.Defuncts.DefunctDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Robin on 3/23/2018.
 */

public class EasyScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int frontId = 0, backId = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                frontId = i;
            }
            else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                backId = i;
            }
        }
        Camera mCamera = Camera.open(backId);
//        android.hardware.Camera.open(1);
//        Intent intent = new Intent(this, EasyScan.class);
//        TextView editText = (TextView) findViewById(R.id.textview2);
//        String message = editText.getText().toString();
//        intent.putExtra("hello", message);
//        startActivity(intent);
        Log.e("halp","halp");
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view<br />
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.<br />
        mScannerView.startCamera();         // Start camera<br />
    }

    @Override
    public void handleResult(com.google.zxing.Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String resulttext = result.getText();

        builder.setTitle("result:");
        if(isvalidatedjson(resulttext)){
            startActivity(getnextintent(resulttext));
//            IEasyScannable scanresult = methodname(resulttext);
        }
        else{
            mScannerView.startCamera();
        }
//        try {
//            JSONObject jsonparser = new JSONObject(result.getText());
////            String type = jsonparser.optJSONObject("Reservation").optString("Username");
//            if(jsonparser.has("Defunct")){
//                builder.setMessage("REPORTED DEFUNCT MATE");
//
//            }
//            else if(jsonparser.has("Reservation")){
//                builder.setMessage("BOOKED BOOKING MATE");
//                SwapToBooking();
//
//            }
//        }
//        catch(Exception e){
//            builder.setMessage("error, please check QR");
//            }finally{
//            AlertDialog alert1 = builder.create();
//            alert1.show();
//        }
    }
    private boolean isvalidatedjson(String jsonresult){
        boolean resultvalue = true;
        try {
            JSONObject ob = new JSONObject(jsonresult);
        } catch (JSONException ex) {
            resultvalue= false;
        }
        return resultvalue;
    }
    private Intent getnextintent(String jsonresult){
        Intent resultingint = new Intent();

        try {
            JSONObject jsonparser = new JSONObject(jsonresult);

            if(jsonparser.has("Defunct")){
                resultingint.setClass(this, DefunctDetailActivity.class);

            }
            else if(jsonparser.has("Reservation")){
                resultingint.setClass(this, EasyScanActivity.class);

            }
        }catch(Exception e){resultingint.setClass(this, EasyScanActivity.class);}

        return resultingint;
    }

    //This method takes a string json file, and returns a IeasyScannable which is a datastructure with the data
    //parsed into it.
//    private IEasyScannable methodname(String jsonresult){
//        IEasyScannable result = new NullScannable();
//        try {
//            JSONObject jsonparser = new JSONObject(jsonresult);
//
//            if(jsonparser.has("Defunct")){
//                result = new Booking();
//
//            }
//            else if(jsonparser.has("Reservation")){
//                result = new Booking();
//            }
//        }catch(Exception e){}
//
//        return result;
//    }

    protected void SwapToBooking(){
        setContentView(R.layout.activity_makebooking);
    }


}

