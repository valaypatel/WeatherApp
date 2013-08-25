package com.valaypatel.weatherapp.modal;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by valaypatel on 2013-08-25.
 */
public class WeatherDay implements Serializable {

    private Calendar mDate;
    private double mDayTemp;
    private double mDayMax;
    private double mDayMin;

    private double mHumidity;
    private double mPressure;
    private double mSpeed;
    private String mDiscription;

    public WeatherDay(JSONObject day) throws JSONException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(day.getLong("dt")*1000);
        this.mDate = calendar;
        JSONObject temp = day.getJSONObject("temp");
        this.mDayTemp =temp.getDouble("day");
        this.mDayMax = temp.getDouble("max");
        this.mDayMin = temp.getDouble("min");

        this.mHumidity = day.getDouble("humidity");
        this.mPressure = day.getDouble("pressure");
        this.mSpeed = day.getDouble("speed");
        this.mDiscription = day.getJSONArray("weather").getJSONObject(0).getString("description");
    }

    public Calendar getDate() {
        return mDate;
    }

    public double getDayTemp() {
        return mDayTemp;
    }

    public double getDayMax() {
        return mDayMax;
    }

    public double getDayMin() {
        return mDayMin;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public double getPressure() {
        return mPressure;
    }

    public double getSpeed() {
        return mSpeed;
    }

    public String getDiscription() {
        return mDiscription;
    }
}
