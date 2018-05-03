package com.example.jamal.orderhr_noninstant;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.Activities.IDataStructure;
import com.example.jamal.orderhr_noninstant.Datastructures.Booking;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamal on 3/29/2018.
 */

public class ScheduleFragment extends Fragment implements IDataStructure{

    private List<Booking> selectedBookings = new ArrayList<>();
    private List<Booking> allBookings;
    LinearLayout fragmentTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        fragmentTable = getView().findViewById(R.id.fragment_scheduleLinear);
        View.OnClickListener onSelectCell = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag() != null){
                    Booking booking = (Booking)(v.getTag());
                    MarkBookings(booking);
                }
            }
        };

        CreateTable(5, 10, "timeslot_", onSelectCell);
    }

    @Override
    public void IVisit(ObjectMapper objectMapper, String json) {
        try{
            allBookings = objectMapper.readValue(json, new TypeReference<List<Booking>>(){});
            Log.d("", "check");
        }
        catch(Exception e ){
            Log.d(e.toString(), e.toString());
        }
    }

    private void MarkBookings(Booking booking){
        Log.d("SelectBookingCell", booking.getLesson());
        if(selectedBookings.contains(booking)){
            selectedBookings.remove(booking);
        }
        else {
            selectedBookings.add(booking);
        }
    }

    public void CreateTable(int daysAmount, int timeslots, String identifier, View.OnClickListener onSelectCell){
        //weight of every cell which means how much space it gets from the screen
        float weight = 1 / timeslots;



        //go through all the timeslots and foreach timeslot create *daysAmount of TextViews horizontally
        for (int j = 1; j <= timeslots; j++){
            String tablerow_id = identifier+j;
            TableRow timeslot = getView().findViewById(getResources().getIdentifier(tablerow_id, "id", getActivity().getPackageName()));

            for(int z = 1; z <= daysAmount; z++){
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(0,0,0,30);


                TextView dayTimeslot = new TextView(getActivity());
                dayTimeslot.setText("timeslot"+j + "\n" + "day"+ z);
                dayTimeslot.setTextSize(12);
                dayTimeslot.setLayoutParams(layoutParams);


                //try to assign an id to each TextView based on the timeslot and day so we can later find it back.
                try {
                    String cellSpecific= Integer.toString(j)+ Integer.toString(z);
                    int DTID = Integer.valueOf(cellSpecific);
                    dayTimeslot.setId(DTID);
                }
                catch (Exception e){}

                dayTimeslot.setOnClickListener(onSelectCell);
                timeslot.addView(dayTimeslot);
            }
        }
    }


}
