package com.example.asd.httprest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void performRequest(View view) {
        // Starts the new thread and makes the httpRequest.
        new httpRequest().execute();
    }

    // important to extend Asynctask, since android wont let you start a http request in your main Thread.
    private class httpRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            JSONObject json = null;
            try {
                // Can also be given as an array of strings, if you want more request.
                URL url = new URL("http://jsonplaceholder.typicode.com/posts/1");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // Set your header here.
                conn.setRequestMethod("GET");

                // read the response, could maybe be done in a more direct way.
                InputStream in = new BufferedInputStream(conn.getInputStream());
                String response = null;
                response = IOUtils.toString(in, "UTF-8");
                // Makes a JSON object.
                json = new JSONObject(response);
                // Example of how to get a part of the JSON object. This only displays the body.
                Log.d("Body of JsonObject", json.getString("body"));

            } catch (IOException e) {
                Log.d("IOException", e.toString());
            } catch (JSONException e) {
                Log.d("JSON", e.toString());
            }
            return json.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            TextView txt = (TextView) findViewById(R.id.httpRequestText);
            txt.setText(result);
        }
    }

}
