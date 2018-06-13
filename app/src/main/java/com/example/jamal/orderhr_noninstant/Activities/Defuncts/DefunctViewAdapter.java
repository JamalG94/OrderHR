package com.example.jamal.orderhr_noninstant.Activities.Defuncts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jamal.orderhr_noninstant.Datastructures.DefunctWrapper;
import com.example.jamal.orderhr_noninstant.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 5/21/2018.
 */

//an adapter to adapt a defunctwrapper into a view for a list.
public class DefunctViewAdapter extends ArrayAdapter<DefunctWrapper> {
    int resource;

    public DefunctViewAdapter(Context context, int resource, List<DefunctWrapper> items){
     super(context,resource,items);
     this.resource = resource ;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout defunctview;
        DefunctWrapper currentitem = getItem(position);

        if(convertView==null){
            defunctview = new LinearLayout(getContext());
            String inflatter = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi ;
            vi = (LayoutInflater)getContext().getSystemService(inflatter);
            vi.inflate(resource,defunctview,true);

        }
        else{
            defunctview = (LinearLayout) convertView;
        }
        TextView title = (TextView)defunctview.findViewById(R.id.defuncttitle);
        title.setText(currentitem.getFields().getRoom()+ " " + currentitem.getFields().getType());
        TextView description = (TextView)defunctview.findViewById(R.id.defunctdescription);
        description.setText(currentitem.getFields().getDescription());

        return defunctview;
    }
}
