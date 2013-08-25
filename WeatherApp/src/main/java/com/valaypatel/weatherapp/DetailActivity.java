package com.valaypatel.weatherapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

import com.valaypatel.weatherapp.modal.WeatherDay;

import java.util.Calendar;
import java.util.Locale;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        WeatherDay day = (WeatherDay)getIntent().getSerializableExtra("Day");
        ((TextView)findViewById(R.id.date)).setText(day.getDate().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) + " " +day.getDate().get(Calendar.DATE) + " "+ day.getDate().getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));
        ((TextView)findViewById(R.id.avgTemp)).setText("Avg. : " +day.getDayTemp());
        ((TextView) findViewById(R.id.maxTemp)).setText("Max. : " +day.getDayMax());
        ((TextView)findViewById(R.id.minTemp)).setText("Min. : " +day.getDayMin());
        ((TextView)findViewById(R.id.humid)).setText("Humidity. : " +day.getHumidity());
        ((TextView) findViewById(R.id.speed)).setText("Speed. : " +day.getSpeed());
        ((TextView)findViewById(R.id.pressure)).setText("Pressure. : " +day.getPressure());
        ((TextView)findViewById(R.id.description)).setText("Discription. : " +day.getDiscription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }
    
}
