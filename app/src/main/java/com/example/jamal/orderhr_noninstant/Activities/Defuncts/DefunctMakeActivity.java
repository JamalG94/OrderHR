package com.example.jamal.orderhr_noninstant.Activities.Defuncts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.Activities.IDataStructure;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.Datastructures.Defunct;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

/**
 * Created by Robin on 3/26/2018.
 */

public class DefunctMakeActivity extends AppCompatActivity implements IDataStructure {
    Defunct receiveddefunct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makedefunctreport);
//        TextView roomview = (TextView)findViewById(R.id.viewroomid);
//        roomview.setText(roomview.getText() );
        Bundle extras = getIntent().getExtras();
        receiveddefunct = new Defunct();
        IFillDataStructures(new ObjectMapper(), extras.getString("jsonparser"));

        Spinner defuncttype = (Spinner) findViewById(R.id.spinnerType);
        TextView roomview = (TextView)findViewById(R.id.viewroomid);
        roomview.setText(roomview.getText() + " " +receiveddefunct.getRoom());
        defuncttype.setSelection(((ArrayAdapter)defuncttype.getAdapter()).getPosition(receiveddefunct.getType()));

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
}
