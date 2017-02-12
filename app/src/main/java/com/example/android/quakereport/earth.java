package com.example.android.quakereport;

/**
 * Created by anuj on 29/12/16.
 */

public class earth {

    private Double mag;
    private String place;
    private Long date;
    private String mUrl;

    earth(Double mag, String place, Long date, String mUrl) {
        this.mag = mag;
        this.place = place;
        this.date = date;
        this.mUrl = mUrl;
    }

    public Double getMag() {
        return mag;
    }

    public String getPlace(){
        return place;
    }


    public Long getDate() {
        return date;
    }

    public String getUrl() {
        return mUrl;
    }

}
