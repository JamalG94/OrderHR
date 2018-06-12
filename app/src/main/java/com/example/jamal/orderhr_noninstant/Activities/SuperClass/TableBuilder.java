package com.example.jamal.orderhr_noninstant.Activities.SuperClass;

import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.Datastructures.TimeDay;
import com.example.jamal.orderhr_noninstant.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by jamal on 5/7/2018.
 */

public class TableBuilder extends AppCompatActivity {

    private View.OnClickListener OnSelectCell;

    protected void setOnSelectCell(View.OnClickListener onSelectCell) {
        OnSelectCell = onSelectCell;
    }


    protected void CreateTable(int daysAmount, int timeslots, String identifier, Boolean clickAble){
        //weight of every cell which means how much space it gets from the screen
        float weight = 1 / timeslots;


        //go through all the timeslots and foreach timeslot create *daysAmount of TextViews horizontally
        for (int j = 1; j <= timeslots; j++){
            String tablerow_id = identifier+j;
            TableRow timeslot = findViewById(getResources().getIdentifier(tablerow_id, "id", getPackageName()));

            for(int z = 1; z <= daysAmount; z++){
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(40,0,40,30);


                TextView dayTimeslot = new TextView(this);
                dayTimeslot.setText(" ");//"timeslot"+j + "\n" + "day"+ z);
                dayTimeslot.setTag(new TimeDay(z, j));
                dayTimeslot.setLayoutParams(layoutParams);
                dayTimeslot.setBackgroundResource(R.drawable.label_bg);

                //try to assign an id to each TextView based on the timeslot and day so we can later find it back.
                try {
                    String cellSpecific= Integer.toString(j)+ Integer.toString(z);
                    int DTID = Integer.valueOf(cellSpecific);
                    dayTimeslot.setId(DTID);
                }
                catch (Exception e){}


                if(clickAble){
                    dayTimeslot.setOnClickListener(this.OnSelectCell);
                }
                timeslot.addView(dayTimeslot);
            }
        }

        setOnSelectCell(null);
    }

    //TODO TEST
    protected static int DatetoColumn(Date date){
        Calendar calendar = DateFormat.getDateInstance().getCalendar();
        calendar.setTime(date);

        //JAVA's CALENDAR STARTS COUNTING FROM SUNDAY
        return calendar.get(Calendar.DAY_OF_WEEK) -1;
    }


}
