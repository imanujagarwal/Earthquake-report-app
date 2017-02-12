package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anuj on 8/2/17.
 */

public class earthquakeLoader extends AsyncTaskLoader<List<earth>> {

    /**
     * Query URL
     */
    private String mUrl;

    public earthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<earth> loadInBackground() {

        // Create a fake list of earthquake locations.
        List<earth> earthquakes = QueryUtils.extractEarthquakes(mUrl);


        return earthquakes;
    }

}
