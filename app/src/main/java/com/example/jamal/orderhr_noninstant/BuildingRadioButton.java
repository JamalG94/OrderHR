package com.example.jamal.orderhr_noninstant;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jamal.orderhr_noninstant.Activities.ScheduleActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by jamal on 5/22/2018.
 */

public class BuildingRadioButton {

    private ScheduleActivity scheduleActivity;
    private RadioGroup radioGroup;

    public BuildingRadioButton(ScheduleActivity scheduleActivity){
        this.scheduleActivity = scheduleActivity;
        radioGroup = scheduleActivity.findViewById(R.id.radio_buildings);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String chosenBuilding = "H";

        // Check which radio button was clicked
        switch(view.getId()) {

            case R.id.radio_pirates:
                if (checked){
                    chosenBuilding = "H";
                    break;
                }
            case R.id.radio_ninjas:
                if (checked){
                    chosenBuilding = "WN";
                    break;
                }
            case R.id.radio_vikings:
                if(checked){
                    chosenBuilding = "WD";
                }
        }

        scheduleActivity.getClassroomSpinner().IFillDataStructures(new ObjectMapper(), String.format("{\"building\":\"%s\"}", chosenBuilding));
    }

}
