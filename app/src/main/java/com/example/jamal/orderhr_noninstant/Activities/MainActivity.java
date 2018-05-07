package com.example.jamal.orderhr_noninstant.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.jamal.orderhr_noninstant.R;


public class MainActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            
        }

    public void ClickSchedule(View view){
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
    public void ClickEasyScan(View view){
        Intent intent = new Intent(this, EasyScanActivity.class);
        startActivity(intent);
    }
}
