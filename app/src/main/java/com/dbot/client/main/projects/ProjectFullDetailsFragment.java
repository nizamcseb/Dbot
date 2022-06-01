package com.dbot.client.main.projects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.main.projects.model.ClientProjectData;
import com.google.gson.GsonBuilder;

public class ProjectFullDetailsFragment extends Fragment {
    View root;
    ImageView iv_back_projects;
    TextView tv_project_details_project_name,
            tv_project_details_project_status,
            tv_project_details_project_booking_id,
            tv_project_details_project_service,
            tv_project_details_project_site_address,
            tv_project_details_cp_name,
            tv_project_details_cp_phone,
            tv_project_details_project_type,
            tv_project_details_property_type,
            tv_project_details_bill_service,
            tv_project_details_bill_service_price,
            tv_project_details_coupon_discount_price,
            tv_project_details_bill_total_price;
    NestedScrollView nsv_my_projects;

    private ProjectFullDetailsViewlModel mViewModel;

    ClientProjectData projectData;

    public ProjectFullDetailsFragment(ClientProjectData clientProjectData) {
        this.projectData = clientProjectData;
    }


    public static ProjectFullDetailsFragment newInstance() {
        return new ProjectFullDetailsFragment(newInstance().projectData);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_project_details, container, false);
        iv_back_projects = root.findViewById(R.id.iv_back_projects);
        nsv_my_projects = root.findViewById(R.id.nsv_my_projects);
        tv_project_details_project_name = root.findViewById(R.id.tv_project_details_project_name);
        tv_project_details_project_status = root.findViewById(R.id.tv_project_details_project_status);
        tv_project_details_project_booking_id = root.findViewById(R.id.tv_project_details_project_booking_id);
        tv_project_details_project_service = root.findViewById(R.id.tv_project_details_project_service);
        tv_project_details_project_site_address = root.findViewById(R.id.tv_project_details_project_site_address);
        tv_project_details_cp_name = root.findViewById(R.id.tv_project_details_cp_name);
        tv_project_details_cp_phone = root.findViewById(R.id.tv_project_details_cp_phone);
        tv_project_details_project_type = root.findViewById(R.id.tv_project_details_project_type);
        tv_project_details_property_type = root.findViewById(R.id.tv_project_details_property_type);
        tv_project_details_bill_service = root.findViewById(R.id.tv_project_details_bill_service);
        tv_project_details_bill_service_price = root.findViewById(R.id.tv_project_details_bill_service_price);
        tv_project_details_coupon_discount_price = root.findViewById(R.id.tv_project_details_coupon_discount_price);
        tv_project_details_bill_total_price = root.findViewById(R.id.tv_project_details_bill_total_price);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.d("projectDataList", new GsonBuilder().setPrettyPrinting().create().toJson(projectData.getProjectName()));
        mViewModel = new ViewModelProvider(this).get(ProjectFullDetailsViewlModel.class);
        mViewModel.getProject(projectData);
        mViewModel.getProjectResult().observe(this, new Observer<ClientProjectData>() {
            @Override
            public void onChanged(ClientProjectData clientProjectData) {
                Log.d("clientProjectData", new GsonBuilder().setPrettyPrinting().create().toJson(clientProjectData));
                //Snackbar.make(getView(),clientProjectData.getProjectName(),Snackbar.LENGTH_SHORT).show();
                if (clientProjectData.getClientId() != null) {
                    tv_project_details_project_name.setText(clientProjectData.getProjectName());
                    tv_project_details_project_status.setText(clientProjectData.getProjectStatus().getStatusValue());
                    tv_project_details_project_booking_id.setText(clientProjectData.getBookingId());
                    tv_project_details_project_service.setText(clientProjectData.getPackage().getPackageName());
                    tv_project_details_project_site_address.setText(clientProjectData.getDoorNumber() + "\n" + clientProjectData.getBuildingName());
                    tv_project_details_cp_name.setText(clientProjectData.getContactPersonName());
                    tv_project_details_cp_phone.setText(clientProjectData.getContactPersonPhone());
                    tv_project_details_project_type.setText(clientProjectData.getProjectType());
                    tv_project_details_property_type.setText(clientProjectData.getPropertySize().getSizeValue());
                    tv_project_details_bill_service.setText(clientProjectData.getPackage().getPackageName());
                    tv_project_details_bill_service_price.setText(clientProjectData.getPackageAmount());
                    tv_project_details_coupon_discount_price.setText("0");
                    tv_project_details_bill_total_price.setText(clientProjectData.getAmountPaid());
                }
            }
        });
        iv_back_projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectsFragment projectsFragment = new ProjectsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, projectsFragment);
                fragmentTransaction.commit();
            }
        });
        nsv_my_projects.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.isNestedScrollingEnabled())
                    Log.d("isNestedScrollingEnabled","true");
               // else Log.d("isNestedScrollingEnabled","false");
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // end of the scroll view
                    Log.d("isNestedScrollingEnabled","false");
                }
                    //Log.d("ScrollView", "scrollX_" + scrollX + "_scrollY_" + scrollY + "_oldScrollX_" + oldScrollX + "_oldScrollY_" + oldScrollY);
                //Do something
            }
        });
        /*nsv_my_projects.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onScrollChanged() {
                View view = (View) nsv_my_projects.getChildAt(nsv_my_projects.getChildCount() - 1);

                int diff = (view.getBottom() - (nsv_my_projects.getHeight() + nsv_my_projects
                        .getScrollY()));

                if (scrollY === v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // end of the scroll view
                }
            }
        });*/
    }
}