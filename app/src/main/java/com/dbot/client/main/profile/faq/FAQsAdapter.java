package com.dbot.client.main.profile.faq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dbot.client.R;
import com.dbot.client.main.profile.faq.model.FAQsData;

import java.util.List;

public class FAQsAdapter extends ArrayAdapter<FAQsData> {
    public FAQsAdapter(Context context, List<FAQsData> faQsDataList) {
        super(context, 0, faQsDataList);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FAQsData faQsData = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_faqs, parent, false);
        }

        TextView tv_faq_qus = convertView.findViewById(R.id.tv_faq_qus);
        TextView tv_faq_ans = convertView.findViewById(R.id.tv_faq_ans);

        tv_faq_qus.setText(faQsData.getQuestion());
        tv_faq_ans.setText(faQsData.getAnswer());

        return convertView;
    }
}