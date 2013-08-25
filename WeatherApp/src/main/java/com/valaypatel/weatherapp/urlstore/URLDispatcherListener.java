package com.valaypatel.weatherapp.urlstore;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by valaypatel on 2013-08-24.
 */
public interface URLDispatcherListener {
    public void dataReceived(JSONObject object);
    public void errorOccured(Exception exception);
}
