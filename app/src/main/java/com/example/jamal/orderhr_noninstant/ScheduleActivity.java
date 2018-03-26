package com.example.jamal.orderhr_noninstant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * Created by jamal on 3/19/2018.
 */

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
    }

    @Override
    protected void onStart(){
        super.onStart();
        FillSchedule();
    }

    private void FillSchedule(){
        String tablerow_id;
        String day;
        String lesson = "ICTLAB";//booking.Lesson;
        String teacher = "Omar";//booking.Username;
        int timeslotfrom = 3;
        int timeslotto = 4;

        TextView timeslot_lesson;
        TableRow tablerow;

        for(int i = timeslotfrom; i <= timeslotto; i++ ){
            timeslot_lesson = new TextView(this);
            tablerow_id = "timeslot_" + i;
            tablerow = findViewById(getResources().getIdentifier(tablerow_id, "id", getPackageName()));
            timeslot_lesson.setText(teacher + lesson);
            tablerow.addView(timeslot_lesson);
        }

    }

}
