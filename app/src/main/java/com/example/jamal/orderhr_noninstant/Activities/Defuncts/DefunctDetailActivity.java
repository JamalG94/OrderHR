package com.example.jamal.orderhr_noninstant.Activities.Defuncts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.R;

/**
 * Created by Robin on 3/22/2018.
 */

public class DefunctDetailActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makebooking);
        TextView roomview = (TextView)findViewById(R.id.viewroomid);
        roomview.setText(roomview.getText() + "WN012");
    }

}