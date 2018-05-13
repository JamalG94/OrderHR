package com.example.jamal.orderhr_noninstant.Activities;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.Activities.Booking.BookingMakeActivity;
import com.example.jamal.orderhr_noninstant.Activities.Defuncts.DefunctMakeActivity;


import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Robin on 3/23/2018.
 */

public class EasyScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FindOpenAndLaunchCamera();
    }

    //THIS HANDLES THE RESULT OUTPUT BY THE QR SCANNER:
    @Override
    public void handleResult(com.google.zxing.Result result) {
        String textreadfromQR = result.getText();

        //If this is a json text in the json QR, then start a new activity based on the information within.Else retry
        if(ValidateIfJsonInputIsValid(textreadfromQR)){
            startActivity(GetNextIntentFromInputJson(textreadfromQR));
        }
        else{
            Toast.makeText(this,"Please scan a valid easy scan QR code!",Toast.LENGTH_LONG).show();
            FindOpenAndLaunchCamera();
        }
    }

    //Checks if this json string returns any errors or so. Translates it into a boolean.
    private boolean ValidateIfJsonInputIsValid(String jsonresult){
        boolean resultvalue = true;
        try {
            JSONObject ob = new JSONObject(jsonresult);
        } catch (JSONException ex) {
            resultvalue= false;
        }
        return resultvalue;
    }

    //Based on the type of the input jsonstring, this decides that logical path will be progressed.
    private Intent GetNextIntentFromInputJson(String jsonresult){
        Intent resultingint = new Intent();
        try {
            JSONObject jsonparser = new JSONObject(jsonresult);

            if(jsonparser.has("defunct")){
                resultingint.setClass(this, DefunctMakeActivity.class);

            }
            else if(jsonparser.has("reservation")){

                resultingint.setClass(this, BookingMakeActivity.class);

            }
            else{
                Toast.makeText(this,"JSON FORMAT WITH QR NOT RECOGNIZED",Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){resultingint.setClass(this, EasyScanActivity.class);}

        resultingint.putExtra("jsonparser",jsonresult);
        return resultingint;
    }

    //Finds the appriopiate camera and opens it.
    public void FindOpenAndLaunchCamera(){
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

        ZXingScannerView mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view<br />
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.<br />
        mScannerView.startCamera();         // Start camera<br />
    }

}

