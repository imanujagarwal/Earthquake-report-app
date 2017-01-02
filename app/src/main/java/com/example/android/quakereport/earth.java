package com.example.android.quakereport;

/**
 * Created by anuj on 29/12/16.
 */

public class earth {

    private String place;
    private String date;
    private String mag;

    earth(String mag,String place,String date){
        this.place = place;
        this.date = date;
        this.mag = mag;
    }

    public String getPlace(){
        return place;
    }

    public String getDate(){
        return date;
    }

    public String getMag(){
        return mag;
    }

}
