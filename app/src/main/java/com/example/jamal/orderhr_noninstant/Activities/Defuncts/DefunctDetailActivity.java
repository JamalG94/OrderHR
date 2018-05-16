package com.example.jamal.orderhr_noninstant.Activities.Defuncts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.jamal.orderhr_noninstant.Activities.IDataStructure;
import com.example.jamal.orderhr_noninstant.Datastructures.DefunctWrapper;
import com.example.jamal.orderhr_noninstant.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Robin on 3/22/2018.
 */

public class DefunctDetailActivity extends AppCompatActivity implements IDataStructure{
    List<DefunctWrapper> receiveddefuncts;
    ListView screenlistview;
    IO ioinstance;
    Spinner typeselectionfilter;

    public String getdatafromio(String type){
        String returnjson = "";
        ioinstance = IO.GetInstance("");
        try{
            if(type.equals("")){
                returnjson = ioinstance.DoPostRequestToAPIServer("{  \"handled\":\"False\"}","http://markb.pythonanywhere.com/alldefuncts/");
            }else{
                returnjson = ioinstance.DoPostRequestToAPIServer("{  \"type\":\""+type+"\",\"handled\":\"False\"}","http://markb.pythonanywhere.com/alldefuncts/");
            }

        }catch(ExecutionException|InterruptedException e){
            //TODO
        }
        return returnjson;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defunctlist);



        this.IFillDataStructures(new ObjectMapper(),getdatafromio(""));
        screenlistview = (ListView)findViewById(R.id.listviewDefuncts);
        typeselectionfilter = (Spinner)findViewById(R.id.spinnertypefiler) ;
        ModifyAllReceivedBookingsToListView(this.receiveddefuncts);
//        TextView roomview = (TextView)findViewById(R.id.viewroomid);
//        roomview.setText(roomview.getText() + "WN012");

    }

    public void ModifyAllReceivedBookingsToListView(List<DefunctWrapper> receiveddefuncts){
        ArrayAdapter<String> test = (new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1));
        screenlistview.setAdapter(test);
        for (DefunctWrapper receiveddefunct: receiveddefuncts) {
//            Button gotowidget = new Button(this);
//            gotowidget.setText(receiveddefunct.getRoom() + receiveddefunct.getType() + receiveddefunct.getDate());
            test.add(receiveddefunct.getFields().getRoom() + " " + receiveddefunct.getFields().getType() + " " +  receiveddefunct.getFields().getDate());
        }
        test.notifyDataSetChanged();
    }

    public void OnClickSearch(View view){
        IFillDataStructures(new ObjectMapper(),getdatafromio(typeselectionfilter.getSelectedItem().toString()));
        ModifyAllReceivedBookingsToListView(receiveddefuncts);
    }


    @Override
    public void IFillDataStructures(ObjectMapper objectMapper, String json) {
        receiveddefuncts = new ArrayList<>();
        //First get json
        //Give json and api link to io
        //convert io data into list of defuncts
        //TODO JSONException
        try{
            JSONArray jsonstuff = new JSONArray(json);
//            JSONArray values = jsonstuff.
//            JSONObject values = jsonstuff.getJSONObject(1);
//            values.equals(jsonstuff);

            for (int i=0; i < jsonstuff.length(); i++) {
                JSONObject jsonobjectparser = jsonstuff.getJSONObject(i);
                receiveddefuncts.add( objectMapper.readValue(jsonobjectparser.toString(), DefunctWrapper.class));
            }

//            receiveddefuncts = new ArrayList<>();
//            Defunct defunct1 = new Defunct();
//            defunct1.setRoom("WN.4.401");
//            defunct1.setDefunctid(1);
//            defunct1.setType("notype");
//            defunct1.setDescription("I don' t know what I' m doing");
//            defunct1.setDate(new Date(2012,1,12));
//            Defunct defunct2= new Defunct();
//            defunct2.setRoom("WN.1.116");
//            defunct2.setDefunctid(2);
//            defunct2.setType("missing");
//            defunct2.setDate(new Date(2014,12,1));
//            defunct2.setDescription("Went into this room to put my keys on the table, only to find out the table is missing. please fix");
//            receiveddefuncts.add(defunct1);
//            receiveddefuncts.add(defunct2);
//
        }catch(JSONException|IOException e){
            Log.i("hello?",e.getMessage());
        }
    }
}