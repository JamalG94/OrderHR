package com.example.jamal.orderhr_noninstant.Activities.Defuncts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.Activities.Main.MainActivity;
import com.example.jamal.orderhr_noninstant.Datastructures.Defunct;
import com.example.jamal.orderhr_noninstant.API.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Robin on 3/26/2018.
 */

public class DefunctMakeByQRJsonActivity extends AppCompatActivity {
    Defunct modelreceiveddefunct;
    TextView textviewdescription;
    TextView textviewroom;
    Spinner spinnerdefuncttype;

    String save_string_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        save_string_status = "Not yet attempted!";

        setContentView(R.layout.activity_makedefunctreport);
        Bundle extras = getIntent().getExtras();
        modelreceiveddefunct = new Defunct();
        try{
            parseJsonToModelDataStructures(new ObjectMapper(), extras.getString("jsonparser"));
        }catch(NullPointerException e){
            Intent test = new Intent(this,MainActivity.class);
            Toast error = Toast.makeText(this,"Something went wrong while reading this QR code, as none was received by the defunct activity!",Toast.LENGTH_LONG);
            error.show();
            startActivity(test);
        }catch(IOException|JSONException e){
            Intent test = new Intent(this,MainActivity.class);
            Toast error = Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG);
            error.show();
            startActivity(test);
        }

        spinnerdefuncttype = (Spinner) findViewById(R.id.spinnerType);
        textviewroom = (TextView)findViewById(R.id.viewroomid);
        textviewdescription = (TextView)findViewById(R.id.edittextdescription);

        textviewroom.setText(textviewroom.getText() + " " + modelreceiveddefunct.getRoom());
        spinnerdefuncttype.setSelection(((ArrayAdapter) spinnerdefuncttype.getAdapter()).getPosition(modelreceiveddefunct.getType()));

    }

    //Parses the JSON and fils the model
    private void parseJsonToModelDataStructures(ObjectMapper objectMapper, String json) throws IOException,JSONException{
        JSONObject test = new JSONObject(json);
        String gottenres = test.getJSONObject("defunct").toString();
        modelreceiveddefunct = objectMapper.readValue(gottenres, modelreceiveddefunct.getClass());
    }

    //Handler of the onClickButtonSave. Generates a string json, handles IO and goes to main activity
    public void onClickSaveDefunct(View view){
        //Checks if all fields are filled in!
        if((! textviewdescription.getText().toString().equals(""))){
            //Gets IO, constructs json string and does request to server through IO
            IO ioisntance = IO.GetInstance();
            String jsonsending = "{ \"room\":\"" + modelreceiveddefunct.getRoom() +"\",\"description\":\""+ textviewdescription.getText()+"\",\"type\":\""+ spinnerdefuncttype.getSelectedItem()+"\"}";
            save_string_status = ioisntance.DoPostRequestToAPIServer(jsonsending,"http://markb.pythonanywhere.com/makedefunct/",this);

            //Checks if the return save_string_status is none or error, return error, else go to main menu
            if(save_string_status.equals("Error") || save_string_status.equals("")){

                save_string_status = "Something went wrong with saving the data! Do you have a connection?";
            }else{
                Intent dostuff = new Intent(this, MainActivity.class);
                startActivity(dostuff);
            }
        }
        else{
            save_string_status = "Please fill in the Description field";
            textviewdescription.setError("!");
        }
        Toast.makeText(this, save_string_status, Toast.LENGTH_LONG).show();
    }


    //Used for testing
    public String getSave_string_status() {
        return save_string_status;
    }

    //Used for testing
    public void setTextviewdescription(String text) {
        this.textviewdescription.setText(text);
    }

}
