package com.dbot.client.common.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dbot.client.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class CalendarAdapter extends ArrayAdapter<Date> {
    // days with events
    private List<Date> availableDates;

    // for view inflation
    private LayoutInflater inflater;


    public CalendarAdapter(Context context, ArrayList<Date> days, List<Date> availableDates) {
        super(context, R.layout.control_calendar_day, days);
        this.availableDates = availableDates;
        inflater = LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public Date getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // day in question
        Date date = getItem(position);
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();

        // today
        Date today = new Date();

        // inflate item if it does not exist yet
        if (view == null)
            view = inflater.inflate(R.layout.control_calendar_day, parent, false);

        // if this day has an event, specify event image
        view.setBackgroundResource(0);
        if (availableDates != null) {
            for (Date eventDate : availableDates) {
                if (eventDate.getDate() == day &&
                        eventDate.getMonth() == month &&
                        eventDate.getYear() == year) {
                    // mark this day for event
                    view.setBackgroundResource(R.drawable.hexagon);
                    ((TextView) view).setTypeface(null, Typeface.BOLD);
                    break;
                }
            }
        }

        // clear styling
        //((TextView) view).setTypeface(null, Typeface.NORMAL);
        ((TextView) view).setTextColor(Color.BLACK);

        if (month != today.getMonth() || year != today.getYear()) {
            // if this day is outside current month, grey it out
            ((TextView) view).setTextColor(getContext().getResources().getColor(R.color.greyed_out));
            //((TextView) view).setVisibility(GONE);
        } else if (day == today.getDate()) {
            // if it is today, set it to blue/bold
            //((TextView) view).setTypeface(null, Typeface.BOLD);
            //((TextView)view).setTextColor(getResources().getColor(R.color.today));
            //((TextView)view).setBackground(getResources().getDrawable(R.drawable.hexagon));
        }

        // set text
        ((TextView) view).setText(String.valueOf(date.getDate()));
       /* ((TextView) view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) view).setTextColor(getContext().getResources().getColor(R.color.white));
                ((TextView) view).setBackground(getContext().getResources().getDrawable(R.drawable.hexagon_selected));
                //Log.d("onItemClick", new GsonBuilder().setPrettyPrinting().create().toJson(getItem(position)));
            }
        });*/

        return view;
    }
}
