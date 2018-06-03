package com.example.jamal.orderhr_noninstant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;

import com.example.jamal.orderhr_noninstant.Activities.Defuncts.DefunctDetailActivity;
import com.example.jamal.orderhr_noninstant.R;
import com.example.jamal.orderhr_noninstant.ScheduleUtility;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends TableBuilder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.CreateTable(5, 10, "timeslot_", false);
    }

    public void ClickSchedule(View view){
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
    public void ClickEasyScan(View view){
        Intent intent = new Intent(this, EasyScanActivity.class);
        startActivity(intent);
    }

    public void ClickGoToUserDetails(View view){
        Intent intent = new Intent(this, UserDetailsActivity.class);
        startActivity(intent);
    }

    public void ClickDefunctList(View view){
        Intent intent = new Intent(this, DefunctDetailActivity.class);
        startActivity(intent);
    }




}
