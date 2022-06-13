package com.dbot.client.main.profile.faq;

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
import com.dbot.client.main.profile.faq.model.FAQsData;

import java.util.List;

public class FAQsAdapter extends BaseAdapter implements Filterable {

    Activity activity;
    Context context;
    public List<FAQsData> faQsDataList;
    LayoutInflater inflater;

    List<FAQsData> filterList;
    FAQsFilter filter;

    public FAQsAdapter(Activity activity, Context context, List<FAQsData> faQsDataList) {
        this.activity = activity;
        this.context = context;
        this.faQsDataList = faQsDataList;
        this.filterList = faQsDataList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return faQsDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return faQsDataList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_faqs, parent, false);
        }

        TextView tv_faq_qus = convertView.findViewById(R.id.tv_faq_qus);
        TextView tv_faq_ans = convertView.findViewById(R.id.tv_faq_ans);
        //FAQsData cityData = getItem(position);
        // current item is not null.
        if (faQsDataList != null) {
            tv_faq_qus.setText("Q: "+faQsDataList.get(position).getQuestion());
            tv_faq_ans.setText("A: "+faQsDataList.get(position).getAnswer());
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FAQsFilter(filterList, this);
        }

        return filter;
    }
}