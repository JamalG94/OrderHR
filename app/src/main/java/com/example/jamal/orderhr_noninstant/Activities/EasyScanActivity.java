package com.example.jamal.orderhr_noninstant.Activities;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.jamal.orderhr_noninstant.Activities.Booking.BookingMakeActivity;
import com.example.jamal.orderhr_noninstant.Activities.Defuncts.DefunctDetailActivity;


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
        }
        else{
            mScannerView.startCamera();
        }
    }

    //Checks if this json string returns any errors or so. Translates it into a boolean.
    private boolean isvalidatedjson(String jsonresult){
        boolean resultvalue = true;
        try {
            JSONObject ob = new JSONObject(jsonresult);
        } catch (JSONException ex) {
            resultvalue= false;
        }
        return resultvalue;
    }

    //Based on the type of the input jsonstring, this decides that logical path will be progressed.
    private Intent getnextintent(String jsonresult){
        Intent resultingint = new Intent();
        try {
            JSONObject jsonparser = new JSONObject(jsonresult);

            if(jsonparser.has("defunct")){
                resultingint.setClass(this, DefunctDetailActivity.class);

            }
            else if(jsonparser.has("reservation")){
                resultingint.putExtra("jsonparser",jsonresult);
                resultingint.setClass(this, BookingMakeActivity.class);

            }
        }catch(Exception e){resultingint.setClass(this, EasyScanActivity.class);}

        return resultingint;
    }


}

