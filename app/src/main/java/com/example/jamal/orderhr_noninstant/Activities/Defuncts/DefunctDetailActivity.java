package com.example.jamal.orderhr_noninstant.Activities.Defuncts;

import android.os.Bundle;
import android.support.constraint.solver.widgets.ConstraintHorizontalLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.Activities.IDataStructure;
import com.example.jamal.orderhr_noninstant.Datastructures.Defunct;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Robin on 3/22/2018.
 */

public class DefunctDetailActivity extends AppCompatActivity implements IDataStructure{
    List<Defunct> receiveddefuncts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defunctlist);

        this.IFillDataStructures(new ObjectMapper(),"{}");
        ListView screenlistview = (ListView)findViewById(R.id.listviewDefuncts);
        ArrayAdapter<String> test = (new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1));
        screenlistview.setAdapter(test);
        AddAllReceivedBookingsToListView(this.receiveddefuncts,test);
//        TextView roomview = (TextView)findViewById(R.id.viewroomid);
//        roomview.setText(roomview.getText() + "WN012");
        test.notifyDataSetChanged();
    }

    public void AddAllReceivedBookingsToListView(List<Defunct> receiveddefuncts, ArrayAdapter<String> test){

        for (Defunct receiveddefunct: receiveddefuncts) {
//            Button gotowidget = new Button(this);
//            gotowidget.setText(receiveddefunct.getRoom() + receiveddefunct.getType() + receiveddefunct.getDate());
            test.add(receiveddefunct.getRoom() + " " + receiveddefunct.getType() + " " +  receiveddefunct.getDate());
        }
    }
//    public View CreateViewForReceivedDefunctsListViewFromDefunct(Defunct defuncttoprocess)
//    {
//        ConstraintHorizontalLayout view = new ConstraintHorizontalLayout();
////        view.add(new TextView(this));
//
//    }
    @Override
    public void IFillDataStructures(ObjectMapper objectMapper, String json) {
        try{
//            JSONObject jsonstuff = new JSONObject(json);
            receiveddefuncts = new ArrayList<>();
            Defunct defunct1 = new Defunct();
            defunct1.setRoom("WN.4.401");
            defunct1.setDefunctid(1);
            defunct1.setType("notype");
            defunct1.setDescription("I don' t know what I' m doing");
            defunct1.setDate(new Date(2012,1,12));
            Defunct defunct2= new Defunct();
            defunct2.setRoom("WN.1.116");
            defunct2.setDefunctid(2);
            defunct2.setType("missing");
            defunct2.setDate(new Date(2014,12,1));
            defunct2.setDescription("Went into this room to put my keys on the table, only to find out the table is missing. please fix");
            receiveddefuncts.add(defunct1);
            receiveddefuncts.add(defunct2);

        }catch(Exception e){

        }
    }
}