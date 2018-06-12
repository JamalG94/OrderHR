package com.example.jamal.orderhr_noninstant.Activities.SuperClass;

import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.Datastructures.TimeDay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jamal on 6/11/2018.
 */

public class RowFiller extends TableBuilder {

    protected List<String> reservedSlots = new ArrayList<String>();

    protected void FillRows(String text, Date date, int timeslotfrom, int timeslotto){
        String cell_id;
        TextView cell;
        int day = DatetoColumn(date);

        for(int i = timeslotfrom; i <= timeslotto; i++ ){
            //Each cell has a specific id
            cell_id = Integer.toString(i) + Integer.toString(day);
            cell = findViewById(getResources().getIdentifier(cell_id, "id", getPackageName()));
            if(cell != null){
                //Create a textview object to hold the lesson and teacher strings
                cell.setText(text);
                //Put the booking object in our specific cell;
                cell.setTag(new TimeDay(day, i));

                reservedSlots.add(cell_id);
            }
        }
    }

    protected void ClearRows(){
        TextView cell;
        if(reservedSlots != null){
            for (String cell_id: reservedSlots) {
                cell = findViewById(getResources().getIdentifier(cell_id, "id", getPackageName()));
                cell.setText("");
            }
        }
    }

}
