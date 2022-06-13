package com.dbot.client.main.profile.faq;

import android.util.Log;
import android.widget.Filter;

import com.dbot.client.main.profile.faq.model.FAQsData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FAQsFilter extends Filter {

    List<FAQsData> faqFilterList;
    FAQsAdapter fagAdapter;

    public FAQsFilter(List<FAQsData> faqFilterList,FAQsAdapter  fagAdapter) {
        this.faqFilterList = faqFilterList;
        this.fagAdapter = fagAdapter;
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        //RESULTS
        Filter.FilterResults results = new Filter.FilterResults();
        Log.d("constraint", constraint.toString());
        //VALIDATION
        if (constraint != null && constraint.length() > 0) {

            //CHANGE TO UPPER FOR CONSISTENCY
            constraint = constraint.toString().toUpperCase();

            List<FAQsData> filterCities = new ArrayList<>();

            //LOOP THRU FILTER LIST
            for (int i = 0; i < faqFilterList.size(); i++) {
                //FILTER
                System.out.println("faqFilterList " + faqFilterList.get(i).getQuestion().toUpperCase(Locale.ROOT) + " constraint " + constraint);
                if (faqFilterList.get(i).getQuestion().toUpperCase(Locale.ROOT).contains(constraint) || faqFilterList.get(i).getAnswer().toUpperCase(Locale.ROOT).contains(constraint)) {
                    filterCities.add(faqFilterList.get(i));
                }
            }

            results.count = filterCities.size();
            results.values = filterCities;
        } else {
            results.count = faqFilterList.size();
            results.values = faqFilterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        fagAdapter.faQsDataList = (List<FAQsData>) results.values;
        fagAdapter.notifyDataSetChanged();
    }
}
