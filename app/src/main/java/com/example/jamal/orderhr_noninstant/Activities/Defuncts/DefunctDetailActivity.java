package com.example.jamal.orderhr_noninstant.Activities.Defuncts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.Activities.IDataStructure;
import com.example.jamal.orderhr_noninstant.Datastructures.Defunct;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Robin on 3/22/2018.
 */

public class DefunctDetailActivity extends AppCompatActivity implements IDataStructure{
    List<Defunct> receiveddefuncts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defunct);
//        TextView roomview = (TextView)findViewById(R.id.viewroomid);
//        roomview.setText(roomview.getText() + "WN012");
    }

    @Override
    public void IFillDataStructures(ObjectMapper objectMapper, String json) {
        try{
            JSONObject jsonstuff = new JSONObject(json);

        }catch(Exception e){

        }
    }
}