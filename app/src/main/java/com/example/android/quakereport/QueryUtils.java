package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */

    public static final String TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    /**
     * Return a list of {@link earth} objects that has been built up from
     * parsing a JSON response.
     */


    public static List<earth> extractEarthquakes(String ApiUrl) {


        URL url = createUrl(ApiUrl);
        String jsonresponse = null;

        try {
            jsonresponse = Makeconnection(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<earth> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject jsonObject = new JSONObject(jsonresponse);
            JSONArray jsonArray = jsonObject.getJSONArray("features");

            for (int i=0; i<jsonArray.length();i++){
                JSONObject mjsonObject = jsonArray.getJSONObject(i);
                JSONObject propertyObject = mjsonObject.getJSONObject("properties");
                Double tMag = propertyObject.getDouble("mag");
                String tPlace = propertyObject.getString("place");
                Long tTime = propertyObject.getLong("time");
                String mUrl = propertyObject.getString("url");

                earthquakes.add(new earth(tMag, tPlace, tTime, mUrl));


            }
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    public static URL createUrl(String s) {
        URL url = null;
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error while creating");
        }
        return url;
    }

    public static String Makeconnection(URL url) throws IOException {

        String jsonresponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonresponse;
        }

        HttpsURLConnection urlConnection = null;
        InputStream is = null;

        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(3000);
            urlConnection.setConnectTimeout(6000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                is = urlConnection.getInputStream();
                jsonresponse = readFromStream(is);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "connection: ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (is != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                is.close();
            }

        }
        return jsonresponse;
    }

    public static String readFromStream(InputStream is) throws IOException {
        StringBuilder output = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
        BufferedReader bf = new BufferedReader(isr);
        String initialtext = bf.readLine();
        while (initialtext != null) {
            output.append(initialtext);
            initialtext = bf.readLine();
        }
        return output.toString();

    }



}