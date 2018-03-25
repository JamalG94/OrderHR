package com.example.jamal.orderhr_noninstant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Created by jamal on 3/19/2018.
 */

public class ScheduleActivity extends AppCompatActivity {

    private TableLayout schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        schedule = findViewById(R.id.schedule_lessons);
        FillSchedule("bullshit");
    }

    private void FillSchedule(String json){
        String tablerow_id;
        String day;
        String lesson;
        String teacher;
        int timeslot;

        //foreach loop to go through all the json data
        tablerow_id = "timeslot_one";

        int id = getResources().getIdentifier(tablerow_id, "id", getPackageName());
        TableRow tableRow = (TableRow)findViewById(R.id.timeslot_one);
        System.out.print(tableRow);


    }

}
