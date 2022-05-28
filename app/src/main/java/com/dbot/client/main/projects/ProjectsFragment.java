package com.dbot.client.main.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.main.MainActivity;
import com.dbot.client.main.projects.model.ClientProjectData;

import java.util.List;

public class ProjectsFragment extends Fragment {

    private ProjectsViewModel projectsViewModel;
    ProjectAdapter projectAdapter;
    List<ClientProjectData> projectDataList;
    ListView lvProject;

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_projects, container, false);
        lvProject = root.findViewById(R.id.lv_projects);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        projectsViewModel = new ViewModelProvider(this).get(ProjectsViewModel.class);
        // TODO: Use the ViewModel
        projectsViewModel.getProjects(MainActivity.sessionManager.getClientId()).observe(this, new Observer<List<ClientProjectData>>() {
            @Override
            public void onChanged(List<ClientProjectData> clientProjectDataList) {
                if (clientProjectDataList != null) {
                    projectDataList = clientProjectDataList;
                    projectAdapter = new ProjectAdapter(getActivity(), getContext(), projectDataList);
                    projectAdapter.getFilter().filter("0");
                    lvProject.setAdapter(projectAdapter);
                }
            }
        });

    }

}