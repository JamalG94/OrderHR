package com.example.jamal.orderhr_noninstant;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.jamal.orderhr_noninstant.Activities.IDataStructure;
import com.example.jamal.orderhr_noninstant.Activities.ScheduleActivity;
import com.example.jamal.orderhr_noninstant.Datastructures.ClassRooms;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Created by jamal on 5/16/2018.
 */

public class ClassroomSpinner implements IDataStructure{

    private ScheduleActivity activity;
    private ClassRooms CurrentListClassrooms;
    private Spinner dropdown;
    String[] items;

    public ClassroomSpinner(ScheduleActivity activity){
        this.activity = activity;
        this.CurrentListClassrooms = new ClassRooms();
        this.CurrentListClassrooms.setResults(new String[]{"Choose", "A", "Building"});
    }

    public void FillSpinner(){
        dropdown = this.activity.findViewById(R.id.classroom_spinner);

        //create a list of items for the spinner.
        items = CurrentListClassrooms.getResults();

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.activity, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    private void SelectItem(final ScheduleActivity activity){
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Toast.makeText(parentView.getContext(),
                        "OnItemSelectedListener : " + parentView.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                        activity.OnItemSelectedInSpinner(parentView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });



    }

    //Is used to display all available classrooms in the chosen building
    @Override
    public void IFillDataStructures(ObjectMapper objectMapper, String json) {
        IO _IO = IO.GetInstance();
        String result = _IO.DoPostRequestToAPIServer(json, "http://markb.pythonanywhere.com/rooms/", activity);
        try {
            CurrentListClassrooms = objectMapper.readValue(result, ClassRooms.class);
        }
        catch(Exception e){
            Log.d("ClassroomSpinner", "IFillDataStructures: failed");
        }
        this.FillSpinner();
    }



}
