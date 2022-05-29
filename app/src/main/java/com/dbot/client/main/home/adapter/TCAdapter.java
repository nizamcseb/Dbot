package com.dbot.client.main.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dbot.client.R;

import java.util.List;

public class TCAdapter extends ArrayAdapter<String> {
    public TCAdapter(Context context, List<String> stringList) {
        super(context, 0, stringList);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String str_point = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tc, parent, false);
        }

        TextView tv_tc_point = convertView.findViewById(R.id.tv_tc_point);

        tv_tc_point.setText(str_point);

        return convertView;
    }
}
