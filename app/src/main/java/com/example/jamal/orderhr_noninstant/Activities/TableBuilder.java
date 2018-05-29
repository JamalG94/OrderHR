package com.example.jamal.orderhr_noninstant.Activities;

import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.Datastructures.TimeDay;
import com.example.jamal.orderhr_noninstant.R;

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



    public void setOnSelectCell(View.OnClickListener onSelectCell) {
        OnSelectCell = onSelectCell;
    }


    public void CreateTable(int daysAmount, int timeslots, String identifier, Boolean clickAble){
        //weight of every cell which means how much space it gets from the screen
        float weight = 1 / timeslots;


        //go through all the timeslots and foreach timeslot create *daysAmount of TextViews horizontally
        for (int j = 1; j <= timeslots; j++){
            String tablerow_id = identifier+j;
            TableRow timeslot = findViewById(getResources().getIdentifier(tablerow_id, "id", getPackageName()));

            for(int z = 1; z <= daysAmount; z++){
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,0,0,30);


                TextView dayTimeslot = new TextView(this);
                dayTimeslot.setText("timeslot"+j + "\n" + "day"+ z);
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

    public int DatetoColumn(Date date){
        //date = new GregorianCalendar(2018, Calendar.JUNE, 20).getTime();
        //testBooking.setDate(new GregorianCalendar(2018, Calendar.JUNE, 5).getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setCalendar(Calendar.getInstance());

        sdf.format(date);
        //Here you set to your timezone
        sdf.setTimeZone(TimeZone.getDefault());
        //Will print on your default Timezone
        System.out.println(sdf.format(sdf.getCalendar().getTime()));

        int day = sdf.getCalendar().get(Calendar.DAY_OF_WEEK);
        //We return -1 because the Calendar.DAY_OF_WEEK method starts at sunday, and my week schedule starts counting from monday
        return day;
    }




}
