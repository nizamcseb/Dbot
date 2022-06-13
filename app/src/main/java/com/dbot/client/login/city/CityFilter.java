package com.dbot.client.login.city;

import android.util.Log;
import android.widget.Filter;

import com.dbot.client.login.city.CityAdapter;
import com.dbot.client.login.city.CityData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CityFilter extends Filter {

    List<CityData> cityDataList;
    CityAdapter cityAdapter;

    public CityFilter(List<CityData> cityDataList, CityAdapter cityAdapter) {
        this.cityDataList = cityDataList;
        this.cityAdapter = cityAdapter;
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        //RESULTS
        Filter.FilterResults results = new Filter.FilterResults();
        Log.d("constraint",constraint.toString());
        //VALIDATION
        if (constraint != null && constraint.length() > 0) {

            //CHANGE TO UPPER FOR CONSISTENCY
            constraint = constraint.toString().toUpperCase();

            List<CityData> filterCities = new ArrayList<>();

            //LOOP THRU FILTER LIST
            for (int i = 0; i < cityDataList.size(); i++) {
                //FILTER
                System.out.println("cityDataList "+cityDataList.get(i).getCityName().toUpperCase(Locale.ROOT)+" constraint "+constraint);
                if (cityDataList.get(i).getCityName().toUpperCase(Locale.ROOT).contains(constraint)) {
                    filterCities.add(cityDataList.get(i));
                }
            }

            results.count = filterCities.size();
            results.values = filterCities;
        } else {
            results.count = cityDataList.size();
            results.values = cityDataList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        cityAdapter.cityDataList = (List<CityData>) results.values;
        cityAdapter.notifyDataSetChanged();
    }
}
