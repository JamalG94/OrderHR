package com.example.jamal.orderhr_noninstant;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.jamal.orderhr_noninstant.Activities.ScheduleActivity;
import com.example.jamal.orderhr_noninstant.Datastructures.ClassRooms;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Created by jamal on 5/16/2018.
 */

public class ClassroomSpinner{

    private ScheduleActivity activity;
    private ClassRooms CurrentListClassrooms;
    private Spinner dropdown;
    private String[] items;
    private Boolean isTouched;

    public ClassroomSpinner(final ScheduleActivity activity){
        this.activity = activity;
        this.CurrentListClassrooms = new ClassRooms();
        this.CurrentListClassrooms.setResults(new String[]{"Choose", "A", "Building"});
        this.isTouched = false;
        this.dropdown = this.activity.findViewById(R.id.classroom_spinner);

        this.dropdown.setOnTouchListener(Spinner_OnTouch);
        this.dropdown.setOnItemSelectedListener(Spinner_OnItemSelect);
    }

    public void FillSpinner(){
        //create a list of items for the spinner.
        items = CurrentListClassrooms.getResults();

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.activity, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        isTouched = false;
    }


    //This method is attached to the spinner, it causes the spinner to only react when it actually get's pressed. Without this method, OnItemSelect will get called
    private View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            v.performClick();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                return isTouched = true;
            }
            return isTouched = false;
        }
    };

    //This method is also attached to the spinner, when an item is selected from the spinner this method gets triggered and calls the API call in scheduleactivity
    private AdapterView.OnItemSelectedListener Spinner_OnItemSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            Toast.makeText(parentView.getContext(), "Dafuq : " + isTouched.toString(), Toast.LENGTH_SHORT).show();

            if(isTouched){
                Toast.makeText(parentView.getContext(), "OnItemSelectedListener : " + parentView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                activity.OnItemSelectedInSpinner(parentView.getItemAtPosition(position).toString());
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
        }
    };

    //Is used to display all available classrooms in the chosen building
    public void JsonToClassroomList(ObjectMapper objectMapper, String json) {
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
