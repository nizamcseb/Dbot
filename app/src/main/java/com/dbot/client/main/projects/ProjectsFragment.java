package com.dbot.client.main.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.main.MainActivity;
import com.dbot.client.main.home.HomeFragment;
import com.dbot.client.main.projects.details.ProjectFullDetailsFragment;
import com.dbot.client.main.projects.model.ClientProjectData;

import java.util.List;

public class ProjectsFragment extends Fragment {

    private ProjectsViewModel projectsViewModel;
    ProjectAdapter projectAdapter;
    List<ClientProjectData> projectDataList;
    ListView lvProject;
    ImageView iv_back_projects;

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_projects, container, false);
        iv_back_projects = root.findViewById(R.id.iv_back_projects);
        lvProject = root.findViewById(R.id.lv_projects);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        projectsViewModel = new ViewModelProvider(this).get(ProjectsViewModel.class);
        // TODO: Use the ViewModel
        projectsViewModel.getProjects(MainActivity.sessionManager.getClientId());
        projectsViewModel.getProjectResult().observe(this, new Observer<List<ClientProjectData>>() {
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
        lvProject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ProjectFullDetailsFragment projectFullDetailsFragment = new ProjectFullDetailsFragment(projectDataList.get(i));
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, projectFullDetailsFragment);
                fragmentTransaction.commit();
            }
        });
        iv_back_projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, homeFragment);
                fragmentTransaction.commit();
            }
        });

    }

}