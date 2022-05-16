package com.dbot.client.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dbot.client.R;
import com.dbot.client.login.model.CityData;

import java.util.List;

public class CityAdapter extends ArrayAdapter<CityData> {
    public CityAdapter(Context context,
                            List<CityData> cityDataArrayList)
    {
        super(context, 0, cityDataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_cities, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.tvCityName);
        CityData cityData = getItem(position);
        // current item is not null.
        if (cityData != null) {
            textViewName.setText(cityData.getCityName());
        }
        return convertView;
    }
}
