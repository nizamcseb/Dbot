package com.dbot.client.main.profile.refer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dbot.client.R;
import com.dbot.client.main.profile.faq.model.FAQsData;
import com.dbot.client.main.profile.refer.model.ReferalHistory;

import java.util.List;

public class ReferalHistoryAdapter extends ArrayAdapter<ReferalHistory> {

    public ReferalHistoryAdapter(@NonNull Context context, List<ReferalHistory> referalHistoryList) {
        super(context,0, referalHistoryList);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ReferalHistory referalHistory = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_referal_history, parent, false);
        }

        TextView tv_rh_name = convertView.findViewById(R.id.tv_rh_name);
        tv_rh_name.setText(referalHistory.getFullname());

        return convertView;
    }
}
