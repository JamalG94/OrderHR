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

import com.example.jamal.orderhr_noninstant.Activities.IDataStructure;
import com.example.jamal.orderhr_noninstant.Activities.MainActivity;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.Datastructures.Defunct;
import com.example.jamal.orderhr_noninstant.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

/**
 * Created by Robin on 3/26/2018.
 */

public class DefunctMakeActivity extends AppCompatActivity implements IDataStructure {
    Defunct receiveddefunct;
    TextView descriptionedit;
    TextView roomview;
    Spinner defuncttypeedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makedefunctreport);
//        TextView roomview = (TextView)findViewById(R.id.viewroomid);
//        roomview.setText(roomview.getText() );
        Bundle extras = getIntent().getExtras();
        receiveddefunct = new Defunct();
        IFillDataStructures(new ObjectMapper(), extras.getString("jsonparser"));

        defuncttypeedit = (Spinner) findViewById(R.id.spinnerType);
        roomview = (TextView)findViewById(R.id.viewroomid);
        descriptionedit = (TextView)findViewById(R.id.edittextdescription);

        roomview.setText(roomview.getText() + " " +receiveddefunct.getRoom());
        defuncttypeedit.setSelection(((ArrayAdapter)defuncttypeedit.getAdapter()).getPosition(receiveddefunct.getType()));

    }

    @Override
    public void IFillDataStructures(ObjectMapper objectMapper, String json) {

        try {
            JSONObject test = new JSONObject(json);
            String gottenres = test.getJSONObject("defunct").toString();


            receiveddefunct = objectMapper.readValue(gottenres, receiveddefunct.getClass());

        } catch (Exception e) {
            Log.d(e.toString(), e.toString());
        }
    }
    //Handler of the onClickButtonSave.
    public void onClickSaveDefunct(View view){
        if((! descriptionedit.getText().toString().equals(""))){
//            String status = saveBooking();
            String status = "Error";
            IO ioisntance = IO.GetInstance();
            String jsonsending = "{ \"room\":\"" + receiveddefunct.getRoom() +"\",\"description\":\""+descriptionedit.getText()+"\",\"type\":\""+defuncttypeedit.getSelectedItem()+"\"}";

                status = ioisntance.DoPostRequestToAPIServer(jsonsending,"http://markb.pythonanywhere.com/makedefunct/",this);

            if(status.equals("Error") || status.equals("")){
                Toast.makeText(this, "Something went wrong with saving the data! (is all data correct and do you have connection?)",
                        Toast.LENGTH_LONG).show();
                Log.i("there you go:",jsonsending);
            }else{
                Intent dostuff = new Intent(this, MainActivity.class);
                startActivity(dostuff);
            }
        }
        else{
            Toast.makeText(this, "Please fill in Lesson and Class fields",
                    Toast.LENGTH_LONG).show();
            descriptionedit.setError("!");
        }
    }

}
