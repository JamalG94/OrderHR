package com.example.jamal.orderhr_noninstant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.Date;

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

    }

    private void ParseReservations(JSONObject json){


    }

    private void FillRows(Booking booking){
        String tablerow_id;
        String lesson = "DEVB"; //booking.Lesson;
        String teacher = "Omar"; //booking.Username;
        //int day = DatetoColumn();
        int timeslotfrom = 3;
        int timeslotto = 4;

        TableRow tablerow;
        TextView timeslot_lesson;

        //Create a listener that does something unique with each cell
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Booking booking = (Booking)(v.getTag());
                System.out.print(booking.Lesson);
            }
        };

        for(int i = timeslotfrom; i <= timeslotto; i++ ){
            //Create a textview object to hold the lesson and teacher strings
            timeslot_lesson = new TextView(this);
            timeslot_lesson.setText(teacher + lesson);
            timeslot_lesson.setTag(booking);
            timeslot_lesson.setOnClickListener(listener);

            //Find the appropiate timeslot of the reservation
            tablerow_id = "timeslot_" + i;
            tablerow = findViewById(getResources().getIdentifier(tablerow_id, "id", getPackageName()));
            tablerow.addView(timeslot_lesson);

            //Create a new TableRow.LayoutParams so we can set the reservation at the right day through help of DatetoColumn
            TableRow.LayoutParams tbr = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            tbr.column = 3; //DatetoColumn
            timeslot_lesson.setLayoutParams(tbr);
        }
    }

    private int DatetoColumn(Date date){
        Calendar calender = Calendar.getInstance();
        int dayOfWeek = calender.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

}
