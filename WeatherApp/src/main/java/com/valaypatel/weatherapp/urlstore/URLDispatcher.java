package com.valaypatel.weatherapp.urlstore;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by valaypatel on 2013-08-24.
 */
public class URLDispatcher extends AsyncTask<Void,Void,Void> {

    private HttpClient mHttpClient;
    private HttpPost mHttpPost;
    private URLDispatcherListener mListener;

    public URLDispatcher(HttpPost httpPost,URLDispatcherListener listener) {
        this.mHttpClient = new DefaultHttpClient();
        this.mHttpPost = httpPost;
        this.mListener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            HttpResponse response = this.mHttpClient.execute(this.mHttpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }
            JSONObject jsonObject = new JSONObject(builder.toString());
            mListener.dataReceived(jsonObject);
        } catch (IOException e) {
            mListener.errorOccured(e);
        } catch (JSONException e) {
            mListener.errorOccured(e);
        }
        return null;
    }
}
