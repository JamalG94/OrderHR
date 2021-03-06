package com.example.jamal.orderhr_noninstant.Activities.EasyScan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.Activities.Booking.BookingMakeByQRJsonActivity;
import com.example.jamal.orderhr_noninstant.Activities.Defuncts.DefunctMakeByQRJsonActivity;
import com.example.jamal.orderhr_noninstant.Activities.Main.MainActivity;
import com.example.jamal.orderhr_noninstant.Activities.UserDetails.UserDetailsActivity;
import com.example.jamal.orderhr_noninstant.Session.Session;


import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Robin on 3/23/2018.
 */

public class EasyScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    public static String pathStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"You have no accepted the permissions to use your camera!",Toast.LENGTH_LONG).show();
        }
        else{
            FindOpenAndLaunchCamera();
        }

    }

    //THIS HANDLES THE RESULT OUTPUT BY THE QR SCANNER:
    @Override
    public void handleResult(com.google.zxing.Result result) {
        String textreadfromQR = result.getText();

        startActivity( GetNextIntentFromInputJson(textreadfromQR,this));
    }

    //TODO
    //Based on the type of the input jsonstring, this decides that logical path will be progressed.
    static public Intent GetNextIntentFromInputJson(String jsonresult, Context thiscontext){
        Intent resultingint = new Intent();
        try {
            JSONObject jsonparser = new JSONObject(jsonresult);

            if(jsonparser.has("defunct")){
                pathStatus = "defunct";
                resultingint.setClass(thiscontext, DefunctMakeByQRJsonActivity.class);

            }
            else if(jsonparser.has("reservation")){
                //If a reservation is found, check if user is staff or admin, else go to user permissions and show the lacking permissions
                if(Session.getIsStaff() || Session.getIsAdmin()){
                    pathStatus = "reservation";
                    resultingint.setClass(thiscontext, BookingMakeByQRJsonActivity.class);
                }
                else{
                    pathStatus = "no permission";
                    Toast.makeText(thiscontext,"No permissions for bookings found!",Toast.LENGTH_LONG).show();
                    resultingint.setClass(thiscontext, UserDetailsActivity.class);
                    resultingint.putExtra("permissionhighlight","bookingmake");
                }
            }
            else{
                pathStatus = "JSON FORMAT WITH QR NOT RECOGNIZED";
                Toast.makeText(thiscontext,"JSON FORMAT WITH QR NOT RECOGNIZED",Toast.LENGTH_LONG).show();
            }
        }catch(JSONException e){
            Toast error = Toast.makeText(thiscontext,"Is this a valid EasyScan QR code?",Toast.LENGTH_LONG);
            Log.d(e.getClass().toString(), e.getMessage());
            error.show();
            resultingint.setClass(thiscontext, MainActivity.class);
        }

        resultingint.putExtra("jsonparser",jsonresult);
        return resultingint;
    }

    //Finds the appropriate camera and opens it.
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

