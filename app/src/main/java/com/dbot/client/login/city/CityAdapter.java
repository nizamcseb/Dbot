package com.dbot.client.login.city;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.dbot.client.R;

import java.util.List;

public class CityAdapter extends BaseAdapter implements Filterable {

    Activity activity;
    Context context;
    public List<CityData> cityDataList;
    LayoutInflater inflater;

    List<CityData> filterList;
    CityFilter filter;

    public CityAdapter(Activity activity, Context context, List<CityData> cityDataList) {
        this.activity = activity;
        this.context = context;
        this.cityDataList = cityDataList;
        this.filterList = cityDataList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return cityDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View root;
       // It is used to set our custom view.
        if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_cities, parent, false);
                }

                TextView textViewName = convertView.findViewById(R.id.tvCityName);
                //CityData cityData = getItem(position);
                // current item is not null.
                if (cityDataList != null) {
                textViewName.setText(cityDataList.get(position).getCityName());
                }

                return convertView;
    }



    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CityFilter(filterList, this);
        }

        return filter;
    }





}
