package com.dbot.client.main.projects;

import android.widget.Filter;

import com.dbot.client.main.projects.model.ClientProjectData;

import java.util.ArrayList;
import java.util.List;

public class ProjectFilter extends Filter {

    List<ClientProjectData> clientProjectDataList;
    ProjectAdapter projectAdapter;

    public ProjectFilter(List<ClientProjectData> clientProjectDataList, ProjectAdapter projectAdapter) {
        this.clientProjectDataList = clientProjectDataList;
        this.projectAdapter = projectAdapter;
    }

    @Override
    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        //RESULTS
        Filter.FilterResults results = new Filter.FilterResults();

        //VALIDATION
        if (constraint != null && constraint.length() > 0) {

            //CHANGE TO UPPER FOR CONSISTENCY
            constraint = constraint.toString().toUpperCase();

            List<ClientProjectData> filterProjects = new ArrayList<>();

            //LOOP THRU FILTER LIST
            for (int i = 0; i < clientProjectDataList.size(); i++) {
                //FILTER
                //if (clientProjectDataList.get(i).getCompletionStatus().equals(Integer.valueOf((String) constraint))) {
                    filterProjects.add(clientProjectDataList.get(i));
               // }
            }

            results.count = filterProjects.size();
            results.values = filterProjects;
        } else {
            results.count = clientProjectDataList.size();
            results.values = clientProjectDataList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        projectAdapter.clientProjectDataList = (List<ClientProjectData>) results.values;
        projectAdapter.notifyDataSetChanged();
    }
}
