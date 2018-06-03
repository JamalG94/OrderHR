package com.example.jamal.orderhr_noninstant.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.R;
import com.example.jamal.orderhr_noninstant.Session;

/**
 * Created by Robin on 5/31/2018.
 */

public class UserDetailsActivity extends AppCompatActivity {
    TextView textviewusername;
    TextView textviewisstaff;
    TextView textviewisadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        textviewisadmin = (TextView)findViewById(R.id.textviewSuperuser);
        textviewisstaff = (TextView)findViewById(R.id.textviewStaff);
        textviewusername = (TextView)findViewById(R.id.textviewusername);
        textviewusername.setText(textviewusername.getText() + " " + Session.getUsername());
        textviewisstaff.setText(textviewisstaff.getText() + " " + Session.getIsStaff());
        textviewisadmin.setText(textviewisadmin.getText() + " " + Session.getIsAdmin());

        Bundle extras = getIntent().getExtras();
        if(extras.containsKey("permissionhighlight")){
            switch(extras.getString("permissionhighlight")){
                case("bookingmake"):
                    textviewisadmin.setError("You need one of these permissions");
                    textviewisstaff.setError("");
            }

        }

    }
}
