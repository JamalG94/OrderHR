package com.example.jamal.orderhr_noninstant;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by jamal on 5/16/2018.
 */

public class ClassroomSpinner{

    private Activity activity;
    private String[][][] classrooms = new String[2][5][];

    public ClassroomSpinner(Activity activity){
        this.activity = activity;
        classrooms[0] = new String[5][];
        classrooms[0][0] = new String[6];
        classrooms[0][1] = new String[3];
        classrooms[1][1] = new String[3];
        classrooms[1][0] = new String[3];
        classrooms[0][0][0] = "H.1.110";
        classrooms[0][0][1] = "H.1.112";
        classrooms[0][0][2] = "H.1.114";
        classrooms[0][0][3] = "H.1.206";
        classrooms[0][0][4] = "H.1.1306";
        classrooms[0][0][5] = "H.1.1308";
        classrooms[0][1][0] = "H.2.111";
        classrooms[0][1][1] = "H.2.204";
        classrooms[0][1][2] = "H.2.306";

        classrooms[1][0][0] = "WD.01.003";
        classrooms[1][0][1] = "WD.01.016";
        classrooms[1][0][1] = "WD.01.019";
        classrooms[1][1][0] = "WD.02.002";
        classrooms[1][1][1] = "WD.02.016";
        classrooms[1][1][2] = "WD.02.019";

    }

    public void FillSpinner(){
        Spinner dropdown = this.activity.findViewById(R.id.classroom_spinner);
        //create a list of items for the spinner.
        String[] items = SelectContent(1, 1);
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.activity, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    private String[] SelectContent(int building, int level){
        return classrooms[building][level];

    }
}
