package com.example.jamal.orderhr_noninstant;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Robin on 3/23/2018.
 */

public class QRHandlerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
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

        builder.setTitle("aaaaa");

        try {
            JSONObject jsonparser = new JSONObject(result.getText());
//            String type = jsonparser.optJSONObject("Reservation").optString("Username");
            if(jsonparser.has("Defunct")){
                builder.setMessage("REPORTED DEFUNCT MATE");
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else if(jsonparser.has("BOOKED BOOKING MATE")){
                builder.setMessage(result.getText());
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        }
        catch(Exception e){

            }





    }

}

