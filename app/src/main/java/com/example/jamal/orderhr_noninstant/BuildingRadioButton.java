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

    public void AssignOnClickRadioButton(){
        for (int i = 0; i < radioGroup.getChildCount(); i++
             ) {
            radioGroup.getChildAt(i).setOnClickListener(onRadioButtonClicked);
        }
    }

    private View.OnClickListener onRadioButtonClicked = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            // Is the button now checked?
            boolean checked = ((RadioButton) v).isChecked();
            String chosenBuilding = "H";

            // Check which radio button was clicked
            switch(v.getId()) {

                case R.id.radio_H:
                    if (checked){
                        chosenBuilding = "H";
                        break;
                    }
                case R.id.radio_WN:
                    if (checked){
                        chosenBuilding = "WN";
                        break;
                    }
                case R.id.radio_WD:
                    if(checked){
                        chosenBuilding = "WD";
                    }
            }

            scheduleActivity.getClassroomSpinner().JsonToClassroomList(new ObjectMapper(), String.format("{\"building\":\"%s\"}", chosenBuilding));
        }
    };
}
