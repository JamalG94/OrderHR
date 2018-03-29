package com.example.jamal.orderhr_noninstant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
import android.widget.CalendarView;
=======
import android.widget.TableRow;
import android.widget.TextView;

>>>>>>> 73687915c4ebc481dc336e19522ce23fc52219ce

/**
 * Created by jamal on 3/19/2018.
 */

public class ScheduleActivity extends AppCompatActivity {
<<<<<<< HEAD
    CalendarView calendarView;
=======
>>>>>>> 73687915c4ebc481dc336e19522ce23fc52219ce

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
<<<<<<< HEAD

        calendarView = findViewById(R.id.scheduleCalendar);

=======
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
>>>>>>> 73687915c4ebc481dc336e19522ce23fc52219ce

    }
}
