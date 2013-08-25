package com.valaypatel.weatherapp.viewcomponent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.valaypatel.weatherapp.R;
import com.valaypatel.weatherapp.modal.WeatherDay;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by valaypatel on 2013-08-25.
 */
public class DateListAdapter extends ArrayAdapter<WeatherDay> {
    private final Context mContext;
    private final WeatherDay[] mDays;

    public DateListAdapter(Context context, WeatherDay[] days) {
        super(context, R.layout.days_list , days);
        this.mContext = context;
        this.mDays = days;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.days_list, parent, false);
        Calendar calendar = mDays[position].getDate();
        ((TextView)rowView.findViewById(R.id.date)).setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) + " " +calendar.get(Calendar.DATE) + " "+ calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));
        ((TextView)rowView.findViewById(R.id.temp)).setText("Avg. : " + mDays[position].getDayTemp());
        ((TextView)rowView.findViewById(R.id.max)).setText("Max. : " +mDays[position].getDayMax());
        ((TextView)rowView.findViewById(R.id.min)).setText("Min. : " +mDays[position].getDayMin());
        return rowView;
    }
}
