package com.example.jamal.orderhr_noninstant.Activities.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jamal.orderhr_noninstant.Activities.Defuncts.ViewDefunctDetailsActivity;
import com.example.jamal.orderhr_noninstant.Activities.EasyScan.EasyScanActivity;
import com.example.jamal.orderhr_noninstant.Activities.Schedule.ScheduleActivity;
import com.example.jamal.orderhr_noninstant.Activities.SuperClass.TableBuilder;
import com.example.jamal.orderhr_noninstant.Activities.UserDetails.UserDetailsActivity;
import com.example.jamal.orderhr_noninstant.R;


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
        Intent intent = new Intent(this, ViewDefunctDetailsActivity.class);
        startActivity(intent);
    }




}
