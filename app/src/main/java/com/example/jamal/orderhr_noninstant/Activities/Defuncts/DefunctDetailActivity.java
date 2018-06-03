package com.example.jamal.orderhr_noninstant.Activities.Defuncts;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.Activities.IDataStructure;
import com.example.jamal.orderhr_noninstant.Activities.MainActivity;
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

import static android.view.View.GONE;

/**
 * Created by Robin on 3/22/2018.
 */

public class DefunctDetailActivity extends AppCompatActivity implements IDataStructure{
    List<DefunctWrapper> receiveddefuncts;
    ListView screenlistview;
    IO ioinstance;
    Spinner typeselectionfilter;
    Switch switchhandled;
    DefunctViewAdapter defunctarrayadapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //DUE TO LIMITS IN OUR API CALL, WE NEED TO DO 2 API CALLS, ONE TO GET ALL THE HANDLED DEFUNCTS
        //AND ONE TO GET THE UNHANDLED DEFUNCTS.
        LoadNShowData();
        loadMainDefunctListToView(findViewById(android.R.id.content));
    }

    public void loadMainDefunctListToView(View view){
        setContentView(R.layout.activity_defunctlist);
        screenlistview = (ListView)findViewById(R.id.listviewDefuncts);
        typeselectionfilter = (Spinner)findViewById(R.id.spinnertypefiler);
        switchhandled = (Switch)findViewById(R.id.switch1);
        ModifyAllReceivedBookingsToListView2(typeselectionfilter.getSelectedItem().toString(),!switchhandled.isChecked());

        screenlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final DefunctWrapper p = (DefunctWrapper) screenlistview.getItemAtPosition(i);
                setContentView(R.layout.activity_defunct);
                TextView tvid = (TextView)findViewById(R.id.textviewID);
                TextView tvdescription = (TextView)findViewById(R.id.textviewdescription);
                TextView tvtype = (TextView)findViewById(R.id.textviewtype);
                TextView tvhandled = (TextView)findViewById(R.id.textviewhandled);
                Button buttonsethandled = (Button)findViewById(R.id.buttonsetHandled);
//                buttonsethandled.setVisibility(GONE);
                buttonsethandled.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setDefunctHandles(p);
                    }
                });
                tvhandled.setText("Handled : " + p.getFields().isHandled());
                tvid.setText(tvid.getText()+" " + p.getPk());
                tvdescription.setText(tvdescription.getText() +" " + p.getFields().getDescription());
                tvtype.setText(tvtype.getText() + " " +p.getFields().getType());
            }
        });
    }

    public void setDefunctHandles(final DefunctWrapper inputdefunct){
        new AlertDialog.Builder(this)
                .setTitle("Defunct Handled?")
                .setMessage("Do you really want to save this defunct as handled?" + inputdefunct.getPk())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        ioinstance = IO.GetInstance();
                        ioinstance.DoPostRequestToAPIServer("{\"id\":"+inputdefunct.getPk()+",\"handled\":\"True\"}","http://markb.pythonanywhere.com/alterdefunct/",DefunctDetailActivity.this);
                        Toast.makeText(DefunctDetailActivity.this, "Defunct Updated", Toast.LENGTH_SHORT).show();
                        DefunctDetailActivity.this.LoadNShowData();
                        loadMainDefunctListToView(findViewById(R.id.buttonsetHandled));
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    //NEED TO SPLIT FUNCTIONALITIES HERE:
    public void ModifyAllReceivedBookingsToListView2(String type, boolean showhandled){
        List<DefunctWrapper> filtereddefunctlist = FillListWithFilteredItems(type,showhandled,receiveddefuncts);

        defunctarrayadapt = new DefunctViewAdapter(this,R.layout.defunct_list_view_item,filtereddefunctlist);
        screenlistview.setAdapter(defunctarrayadapt);
        defunctarrayadapt.notifyDataSetChanged();
    }

    //Updates the shown defuncts USED FOR onCLICK HANDLER FOR A BUTTON
    public void OnClickSearch(View view){
        ModifyAllReceivedBookingsToListView2(typeselectionfilter.getSelectedItem().toString(),!switchhandled.isChecked());
    }
    public void LoadNShowData(){
        receiveddefuncts = new ArrayList<>();
        this.IFillDataStructures(new ObjectMapper(),getdatafromio(true));
        this.IFillDataStructures(new ObjectMapper(),getdatafromio(false));
    }
    //Does an API call to get data from the server
    public String getdatafromio(boolean handled){
        String returnjson = "";
        ioinstance = IO.GetInstance();
        returnjson  = ioinstance.DoPostRequestToAPIServer("{\"handled\":"+handled+"}","http://markb.pythonanywhere.com/alldefuncts/",this);
        return returnjson;
    }

    @Override
    public void IFillDataStructures(ObjectMapper objectMapper, String json) {
        //TODO JSONException
        try{
            JSONArray jsonstuff = new JSONArray(json);
            //Add all defucnts found in the json to the list.
            for (int i=0; i < jsonstuff.length(); i++) {
                JSONObject jsonobjectparser = jsonstuff.getJSONObject(i);
                receiveddefuncts.add( objectMapper.readValue(jsonobjectparser.toString(), DefunctWrapper.class));
            }
        }catch(JSONException|IOException e){
            Log.i("hello?",e.getMessage());
        }
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

}