package com.valaypatel.weatherapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.valaypatel.weatherapp.modal.WeatherDay;
import com.valaypatel.weatherapp.urlstore.URLDispatcher;
import com.valaypatel.weatherapp.urlstore.URLDispatcherListener;
import com.valaypatel.weatherapp.viewcomponent.DateListAdapter;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements URLDispatcherListener{

    private static final String LOG_TAG = "Weather App";
    private static final String baseForWebService = "http://api.openweathermap.org/data/2.5/forecast/daily";
    private static final String defaultParameter = "&mode=JSON&units=imperial&cnt=7";
    private ListView mListView;
    private ProgressBar mProgressBar;
    private TextView mErrorText;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.sevenDayReport);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mErrorText = (TextView) findViewById(R.id.errorText);
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
            searchView.setQuery("", false);
            searchView.clearFocus();
        }
    }

    private void doMySearch(String query) {
        String cityQuery = "?q="+query;
        mErrorText.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
        URLDispatcher urlDispatcher = new URLDispatcher(new HttpPost(baseForWebService+cityQuery+defaultParameter),this);
        urlDispatcher.execute();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        searchView = (SearchView) menu.findItem( R.id.options_menu_main_search ).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public void dataReceived(JSONObject object) {
        try {
            Log.v(LOG_TAG,object.toString());
            if(object.getString("cod").equals("404")) {
                mErrorText.setText("City Not found !!!");
                return;
            }
            final DateListAdapter dateListAdapter = new DateListAdapter(this,this.getDaysData(object.getJSONArray("list")));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mErrorText.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                    mListView.setAdapter(dateListAdapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            WeatherDay day = (WeatherDay)adapterView.getAdapter().getItem(i);
                            Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                            intent.putExtra("Day",day);
                            startActivity(intent);
                        }
                    });
                }
            });

        } catch (final JSONException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mErrorText.setVisibility(View.VISIBLE);
                    mErrorText.setText(e.getLocalizedMessage());
                    mProgressBar.setVisibility(View.GONE);
                    mListView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void errorOccured(final Exception exception) {
        Log.v(LOG_TAG,exception.getLocalizedMessage());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mErrorText.setVisibility(View.VISIBLE);
                mErrorText.setText(exception.getLocalizedMessage());
                mProgressBar.setVisibility(View.GONE);
                mListView.setVisibility(View.GONE);
            }
        });

    }

    private WeatherDay[] getDaysData(JSONArray array) throws JSONException {
        WeatherDay[] weatherDays = new WeatherDay[array.length()];
        for(int i=0;i<array.length();i++) {
            JSONObject day = array.getJSONObject(i);

            weatherDays[i] = new WeatherDay(day);
        }
        return weatherDays;
    }
}
