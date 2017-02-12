package com.example.android.quakereport;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.drawable.GradientDrawable;


import static com.example.android.quakereport.R.id.magnitude;


/**
 * Created by anuj on 29/12/16.
 */

public class earthAdapter extends ArrayAdapter<earth> {

    private static final String LOCATION_SEPARATOR = " of ";


    earthAdapter(Context context, List<earth> values) {
        super(context,0,values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listitemview = convertView;

        if(listitemview == null){

            listitemview = LayoutInflater.from(getContext()).inflate(R.layout.listitem,parent,false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        earth earthObject = getItem(position);

        // Find the TextView with view ID magnitude
        TextView mMag = (TextView) listitemview.findViewById(magnitude);

        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(earthObject.getMag());
        // Display the magnitude of the current earthquake in that TextView
        mMag.setText(formattedMagnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) mMag.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthObject.getMag());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // Get the original location string from the Earthquake object,
        String originalLocation = earthObject.getPlace();

        String primaryLocation;
        String locationOffset;

        // Check whether the originalLocation string contains the " of " text
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] seperatedLocationsArray = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = seperatedLocationsArray[0] + LOCATION_SEPARATOR;
            primaryLocation = seperatedLocationsArray[1];
        } else {

            locationOffset = getContext().getString(R.string.near_by);
            primaryLocation = originalLocation;
        }

        // Find the TextView with view ID location
        TextView tvPrimaryLocation = (TextView) listitemview.findViewById(R.id.primary_location);


        tvPrimaryLocation.setText(primaryLocation);

        TextView tvLocationOffset = (TextView) listitemview.findViewById(R.id.location_offset);
        tvLocationOffset.setText(locationOffset);


        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(earthObject.getDate());

        // Find the TextView with view ID date
        TextView dateView = (TextView) listitemview.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        TextView mDate = (TextView) listitemview.findViewById(R.id.time);


        String formattedTime = formatTime(dateObject);

        mDate.setText(formattedTime);

        // Return the list item view that is now showing the appropriate data
        return listitemview;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double mag) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(mag);

        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;

            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);


    }


}
