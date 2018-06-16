package com.example.jamal.orderhr_noninstant.Activities.Schedule;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.Datastructures.ClassRooms;
import com.example.jamal.orderhr_noninstant.API.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


/**
 * Created by jamal on 5/16/2018.
 */

public class ClassroomSpinner{

    private ScheduleActivity activity;
    private ClassRooms CurrentListClassrooms;
    private Spinner dropdown;
    private String[] items;

    public ClassroomSpinner(final ScheduleActivity activity){
        this.activity = activity;
        this.CurrentListClassrooms = new ClassRooms();
        this.CurrentListClassrooms.setResults(new String[]{"Choose"});
        this.dropdown = this.activity.findViewById(R.id.classroom_spinner);
        this.dropdown.setOnItemSelectedListener(Spinner_OnItemSelect);
    }

    public void FillSpinner(){
        //create a list of items for the spinner.
        items = CurrentListClassrooms.getResults();

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.activity, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    //This method is also attached to the spinner, when an item is selected from the spinner this method gets triggered and calls the API call in scheduleactivity
    private AdapterView.OnItemSelectedListener Spinner_OnItemSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            Toast.makeText(parentView.getContext(), "OnItemSelectedListener : " + parentView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            activity.ClassRoomSelected(parentView.getItemAtPosition(position).toString());
        }
        //This method needed to be implemented, but I have no use case for it
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            Toast.makeText(parentView.getContext(), "Selected nothing", Toast.LENGTH_SHORT).show();
        }
    };


    public void GetClassRooms(String building){
        IO _IO = IO.GetInstance();
        String classrooms =  _IO.DoPostRequestToAPIServer(building, "http://markb.pythonanywhere.com/rooms/", activity);
        if(!classrooms.isEmpty()){
            JsonToClassroomList(new ObjectMapper(), classrooms);
        }
    }

    //Is used to display all available classrooms in the chosen building
    public void JsonToClassroomList(ObjectMapper objectMapper, String classrooms) {
        try {
            CurrentListClassrooms = objectMapper.readValue(classrooms, ClassRooms.class);
        }
        catch(JsonMappingException e){
            Log.d("ClassroomSpinner, Map", "ObjectMapper failed to convert values");
        }
        catch(IOException e){
            Log.d("ClassroomSpinner, IO", "ObjectMapper threw an IO error");
        }
        this.FillSpinner();
    }

}
