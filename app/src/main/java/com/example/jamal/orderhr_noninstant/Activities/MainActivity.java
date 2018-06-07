package com.example.jamal.orderhr_noninstant.Activities;

import android.arch.lifecycle.Transformations;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.Activities.Defuncts.DefunctDetailActivity;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.example.jamal.orderhr_noninstant.Datastructures.BookingWrapper;
import com.example.jamal.orderhr_noninstant.LocalDBControllers.appDataBaseLocal;
import com.example.jamal.orderhr_noninstant.Datastructures.Defunct;
import com.example.jamal.orderhr_noninstant.Datastructures.DefunctWrapper;
import com.example.jamal.orderhr_noninstant.LocalDBControllers.LocalDatabaseRepository;
import com.example.jamal.orderhr_noninstant.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
