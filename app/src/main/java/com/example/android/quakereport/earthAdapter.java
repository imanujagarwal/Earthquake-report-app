package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anuj on 29/12/16.
 */

public class earthAdapter extends ArrayAdapter<earth> {


    earthAdapter(Context context,ArrayList<earth> values){
        super(context,0,values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listitemview = convertView;

        if(listitemview == null){

            listitemview = LayoutInflater.from(getContext()).inflate(R.layout.listitem,parent,false);
        }

        earth earthObject = getItem(position);

        TextView mMag = (TextView)listitemview.findViewById(R.id.magTextView);
        TextView mPlace = (TextView) listitemview.findViewById(R.id.placeTextView);
        TextView mDate = (TextView)listitemview.findViewById(R.id.dayTextView);

        mMag.setText(earthObject.getMag());
        mPlace.setText(earthObject.getPlace());
        mDate.setText(earthObject.getDate());


        return listitemview;
    }
}
