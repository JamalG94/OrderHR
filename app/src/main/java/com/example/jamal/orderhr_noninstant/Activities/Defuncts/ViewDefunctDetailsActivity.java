package com.example.jamal.orderhr_noninstant.Activities.Defuncts;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.Datastructures.DefunctWrapper;
import com.example.jamal.orderhr_noninstant.API.IO;
import com.example.jamal.orderhr_noninstant.LocalDBControllers.LocalDatabaseRepository;
import com.example.jamal.orderhr_noninstant.R;
import com.example.jamal.orderhr_noninstant.Session.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 3/22/2018.
 */

public class ViewDefunctDetailsActivity extends AppCompatActivity{
    LocalDatabaseRepository db;
    List<DefunctWrapper> receiveddefuncts;
    ListView screenlistview;
    IO ioinstance;
    Spinner typeselectionfilter;
    Switch switchhandled;
    DefunctViewAdapter defunctarrayadapt;
    Boolean synchronizedwithdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new LocalDatabaseRepository(getApplication());

        loadMainDefunctListToView(findViewById(android.R.id.content));
    }

    public void loadMainDefunctListToView(View view){
        setContentView(R.layout.activity_defunctlist);

        screenlistview = (ListView)findViewById(R.id.listviewDefuncts);
        typeselectionfilter = (Spinner)findViewById(R.id.spinnertypefiler);
        switchhandled = (Switch)findViewById(R.id.switch1);

        attemptUpdateData();
        if(! synchronizedwithdb){
            //ListView list = (ListView)findViewById(R.id.listviewDefuncts);
            screenlistview.setBackgroundColor(Color.GRAY);
        }
        loadLocalDatabyFiltersIntoArrayAdapter(typeselectionfilter.getSelectedItem().toString(),!switchhandled.isChecked());

        screenlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final DefunctWrapper p = (DefunctWrapper) screenlistview.getItemAtPosition(i);
                setContentView(R.layout.activity_defunct);
                TextView tvid = (TextView)findViewById(R.id.textviewID);
                TextView tvdescription = (TextView)findViewById(R.id.textviewdescription);
                TextView tvtype = (TextView)findViewById(R.id.textviewtype);
                TextView tvhandled = (TextView)findViewById(R.id.textviewhandled);
                TextView tvroom = (TextView)findViewById(R.id.textviewDefunctRoom);
                Button buttonsethandled = (Button)findViewById(R.id.buttonsetHandled);
                if(p.getFields().isHandled() || ( !Session.getIsAdmin() || !Session.getIsAdmin())){
                    buttonsethandled.setVisibility(View.GONE);
                }

                buttonsethandled.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setDefunctHandles(p);
                    }
                });
                tvroom.setText(tvroom.getText()+ " " + p.getFields().getRoom());
                tvhandled.setText("Handled : " + p.getFields().isHandled());
                tvid.setText(tvid.getText()+" " + p.getPk());
                tvdescription.setText(tvdescription.getText() +" " + p.getFields().getDescription());
                tvtype.setText(tvtype.getText() + " " +p.getFields().getType());
            }
        });
    }

    //when clicking handle on a defunct, this opens a confirm box. When clicked, io attempts to set this defunct to handled.
    public void setDefunctHandles(final DefunctWrapper inputdefunct){
        new AlertDialog.Builder(this)
                .setTitle("Defunct Handled?")
                .setMessage("Do you really want to save this defunct as handled?" + inputdefunct.getPk())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        loadMainDefunctListToView(findViewById(R.id.buttonsetHandled));
                        ioinstance = IO.GetInstance();
                        if(ioinstance.DoPostRequestToAPIServer("{\"id\":"+inputdefunct.getPk()+",\"handled\":\"True\"}","http://markb.pythonanywhere.com/alterdefunct/",ViewDefunctDetailsActivity.this).equals("")){
                            Toast.makeText(ViewDefunctDetailsActivity.this,"Defunct Handled!",Toast.LENGTH_LONG).show();
                        };
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    //NEED TO SPLIT FUNCTIONALITIES HERE:
    public void loadLocalDatabyFiltersIntoArrayAdapter(String type, boolean showhandled){
        receiveddefuncts = db.getmAllDefuncts();
        List<DefunctWrapper> filtereddefunctlist = FillListWithFilteredItems(type,showhandled,receiveddefuncts);

        defunctarrayadapt = new DefunctViewAdapter(this,R.layout.fragment_defunct_list_item,filtereddefunctlist);
        screenlistview.setAdapter(defunctarrayadapt);
        defunctarrayadapt.notifyDataSetChanged();
    }

    //Updates the shown defuncts USED FOR onCLICK HANDLER FOR A BUTTON
    public void OnClickSearch(View view){
        loadLocalDatabyFiltersIntoArrayAdapter(typeselectionfilter.getSelectedItem().toString(),!switchhandled.isChecked());
    }

    //Does an API call to get data from the server
    public String getdatafromio(boolean handled){
        String returnjson = "";
        ioinstance = IO.GetInstance();
        returnjson  = ioinstance.DoPostRequestToAPIServer("{\"handled\":"+handled+"}","http://markb.pythonanywhere.com/alldefuncts/",this);
        return returnjson;
    }

    //complex for loops to filter a list of defuncts based on 2 parameters (showhandled, type), no linq or streaming in this api level so ugly for loops :c
    public static List<DefunctWrapper> FillListWithFilteredItems(String type, final boolean showhandled, List<DefunctWrapper> inputreceiveddefuncts){
        List<DefunctWrapper> filtereddefunctlist = new ArrayList<>();
        for(DefunctWrapper receiveddefunct: inputreceiveddefuncts){
            if(!receiveddefunct.getFields().isHandled() || showhandled){
                if(!type.equals("view all")){
                    if(receiveddefunct.getFields().getType().toUpperCase().equals(type.toUpperCase())){
                        filtereddefunctlist.add(receiveddefunct);
                    }
                }else{
                    filtereddefunctlist.add(receiveddefunct);
                }
            }
        }
        return filtereddefunctlist;
    }

    //Returns a list of Defunct Wrappers from a Json file
    public static List<DefunctWrapper> fromJsontoListOfDefunctDataWrappers(String jsonstring, ObjectMapper objectmapperjson) throws JSONException,IOException{
        List<DefunctWrapper> listtofill = new ArrayList<>();
        JSONArray jsonarray = new JSONArray(jsonstring);
        //Add all defucnts found in the json to the list.
        for (int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobjectparser = jsonarray.getJSONObject(i);
            listtofill.add(  objectmapperjson.readValue(jsonobjectparser.toString(), DefunctWrapper.class));
        }
        return  listtofill;
    }

    //Gets all defuncts from the server through the io method, converts it into 1 list.
    public static List<DefunctWrapper> fromApiServerConvertAllDefunctDataToList(ViewDefunctDetailsActivity thisactivity) throws JSONException,IOException{
        //DUE TO LIMITS IN OUR API CALL, WE NEED TO DO 2 API CALLS, ONE TO GET ALL THE HANDLED DEFUNCTS
        //AND ONE TO GET THE UNHANDLED DEFUNCTS.
        String jsonhandled = thisactivity.getdatafromio(true);
        String jsonunhandled = thisactivity.getdatafromio(false);
        ObjectMapper mapper= new ObjectMapper();
        List<DefunctWrapper> totallistofwrappers = new ArrayList<>();
        totallistofwrappers.addAll(fromJsontoListOfDefunctDataWrappers(jsonhandled,mapper));
        totallistofwrappers.addAll(fromJsontoListOfDefunctDataWrappers(jsonunhandled,mapper));
        return totallistofwrappers;
    }

    //Attempts to get a list of defuncts data from the server, if failed, load latest local database.
    public void attemptUpdateData(){
        synchronizedwithdb = false;
        List<DefunctWrapper> listOfDefunctsWrapper;
        try{
            listOfDefunctsWrapper = fromApiServerConvertAllDefunctDataToList(this);
            db.synchronizeDatabaseWithNewDataAfterDelete(listOfDefunctsWrapper);
            synchronizedwithdb = true;
        }catch(JSONException|IOException e){
            Toast.makeText(this,"Data could not have been synchronized! Connection?",Toast.LENGTH_LONG).show();
        }
    }
}