package com.dbot.client.main.projects;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.dbot.client.common.Popup;
import com.dbot.client.main.projects.model.ClientProjectData;
import com.dbot.client.main.projects.model.FileRequestResponse;
import com.dbot.client.main.projects.model.ProjectTrackingResponse;
import com.dbot.client.main.projects.model.RefundAmountResponse;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectFullDetailsFragment extends Fragment implements View.OnClickListener {
    View root;
    ImageView iv_back_projects, iv_project_details_edit, iv_pt_document_request, iv_pt_cp_confirmation, iv_pt_site_access_confirmation, iv_pt_site_documentation, iv_pt_file_sharing;
    TextView tv_pt_document_request,
            tv_pt_cp_confirmation,
            tv_pt_site_access_confirmation,
            tv_pt_site_documentation,
            tv_pt_file_sharing,
            tv_project_details_project_name,
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
            tv_project_details_bill_total_price,
            tv_project_details_cancel,
            tv_project_details_reshedule;
    CheckBox cb_project_details_electrical, cb_project_details_plumbing, cb_project_details_plastering, cb_project_details_flooring;
    View v_pt_cp_confirmation, v_pt_site_access_confirmation, v_pt_site_documentation, v_pt_file_sharing;
    NestedScrollView nsv_my_projects;
    String booking_id;
    Button btn_pt_send_file;
    boolean isFinalProcessCompleted = false;

    Popup popup;
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

        iv_pt_document_request = root.findViewById(R.id.iv_pt_document_request);
        iv_pt_cp_confirmation = root.findViewById(R.id.iv_pt_cp_confirmation);
        iv_pt_site_access_confirmation = root.findViewById(R.id.iv_pt_site_access_confirmation);
        iv_pt_site_documentation = root.findViewById(R.id.iv_pt_site_documentation);
        iv_pt_file_sharing = root.findViewById(R.id.iv_pt_file_sharing);

        v_pt_cp_confirmation = root.findViewById(R.id.v_pt_cp_confirmation);
        v_pt_site_access_confirmation = root.findViewById(R.id.v_pt_site_access_confirmation);
        v_pt_site_documentation = root.findViewById(R.id.v_pt_site_documentation);
        v_pt_file_sharing = root.findViewById(R.id.v_pt_file_sharing);

        tv_pt_document_request = root.findViewById(R.id.tv_pt_document_request);
        tv_pt_cp_confirmation = root.findViewById(R.id.tv_pt_cp_confirmation);
        tv_pt_site_access_confirmation = root.findViewById(R.id.tv_pt_site_access_confirmation);
        tv_pt_site_documentation = root.findViewById(R.id.tv_pt_site_documentation);
        tv_pt_file_sharing = root.findViewById(R.id.tv_pt_file_sharing);


        iv_project_details_edit = root.findViewById(R.id.iv_project_details_edit);
        tv_project_details_cancel = root.findViewById(R.id.tv_project_details_cancel);
        tv_project_details_reshedule = root.findViewById(R.id.tv_project_details_reshedule);
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
        cb_project_details_electrical = root.findViewById(R.id.cb_project_details_electrical);
        cb_project_details_plumbing = root.findViewById(R.id.cb_project_details_plumbing);
        cb_project_details_plastering = root.findViewById(R.id.cb_project_details_plastering);
        cb_project_details_flooring = root.findViewById(R.id.cb_project_details_flooring);


        btn_pt_send_file = root.findViewById(R.id.btn_pt_send_file);

        iv_project_details_edit.setOnClickListener(this::onClick);
        tv_project_details_cancel.setOnClickListener(this::onClick);
        tv_project_details_reshedule.setOnClickListener(this::onClick);
        btn_pt_send_file.setOnClickListener(this::onClick);
        tv_project_details_cp_phone.setOnClickListener(this::onClick);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.d("projectDataList", new GsonBuilder().setPrettyPrinting().create().toJson(projectData.getProjectName()));

        popup = new Popup();
        mViewModel = new ViewModelProvider(this).get(ProjectFullDetailsViewlModel.class);
        mViewModel.getProjectTracking(projectData.getBookingId());
        mViewModel.getProjectTrackingResult().observe(this, new Observer<ProjectTrackingResponse>() {
            @Override
            public void onChanged(ProjectTrackingResponse projectTrackingResponse) {
                if (projectTrackingResponse != null) {
                    if (projectTrackingResponse.getProjectTrackingData().getRequestedOn() != null) {
                        iv_pt_document_request.setImageDrawable(getActivity().getDrawable(R.drawable.ic_project_status_with_tick_png));
                        tv_pt_document_request.setText(projectTrackingResponse.getProjectTrackingData().getRequestedOn());
                    }
                    if (projectTrackingResponse.getProjectTrackingData().getContactPersonConfirmation() != null) {
                        iv_pt_cp_confirmation.setImageDrawable(getActivity().getDrawable(R.drawable.ic_project_status_with_tick_png));
                        tv_pt_cp_confirmation.setText(projectTrackingResponse.getProjectTrackingData().getContactPersonConfirmation());
                        v_pt_cp_confirmation.setBackgroundColor(R.color.primary_varient);
                    } else
                        tv_pt_cp_confirmation.setText("ETA - " + projectTrackingResponse.getProjectTrackingData().getContactPersonConfirmationEta());

                    if (projectTrackingResponse.getProjectTrackingData().getSiteAccessConfirmation() != null) {
                        iv_pt_site_access_confirmation.setImageDrawable(getActivity().getDrawable(R.drawable.ic_project_status_with_tick_png));
                        tv_pt_site_access_confirmation.setText(projectTrackingResponse.getProjectTrackingData().getSiteAccessConfirmation());
                        v_pt_site_access_confirmation.setBackgroundColor(R.color.primary_varient);
                    } else
                        tv_pt_site_access_confirmation.setText("ETA - " + projectTrackingResponse.getProjectTrackingData().getSiteAccessConfirmationEta());

                    if (projectTrackingResponse.getProjectTrackingData().getSiteDocumentation() != null) {
                        iv_pt_site_documentation.setImageDrawable(getActivity().getDrawable(R.drawable.ic_project_status_with_tick_png));
                        tv_pt_site_documentation.setText(projectTrackingResponse.getProjectTrackingData().getSiteDocumentation());
                        v_pt_site_documentation.setBackgroundColor(R.color.primary_varient);
                    } else
                        tv_pt_site_documentation.setText("ETA - " + projectTrackingResponse.getProjectTrackingData().getSiteDocumentationEta());

                    if (projectTrackingResponse.getProjectTrackingData().getFilesSharing() != null) {
                        iv_pt_file_sharing.setImageDrawable(getActivity().getDrawable(R.drawable.ic_project_status_with_tick_png));
                        tv_pt_file_sharing.setText(projectTrackingResponse.getProjectTrackingData().getFilesSharing());
                        v_pt_file_sharing.setBackgroundColor(R.color.primary_varient);
                        isFinalProcessCompleted = true;
                        btn_pt_send_file.setBackgroundTintList(getActivity().getColorStateList(R.color.primary_varient));
                    } else
                        tv_pt_file_sharing.setText("ETA - " + projectTrackingResponse.getProjectTrackingData().getFilesSharingEta());

                }
            }
        });
        mViewModel.getProject(projectData);
        mViewModel.getProjectResult().observe(this, new Observer<ClientProjectData>() {
            @Override
            public void onChanged(ClientProjectData clientProjectData) {
                Log.d("clientProjectData", new GsonBuilder().setPrettyPrinting().create().toJson(clientProjectData));
                //Snackbar.make(getView(),clientProjectData.getProjectName(),Snackbar.LENGTH_SHORT).show();
                if (clientProjectData.getClientId() != null) {
                    booking_id = clientProjectData.getBookingId();
                    tv_project_details_project_name.setText(clientProjectData.getProjectName());
                    tv_project_details_project_status.setText(clientProjectData.getProjectStatus().getStatusValue());
                    tv_project_details_project_booking_id.setText(clientProjectData.getBookingId());
                    tv_project_details_project_service.setText(clientProjectData.getPackage().getPackageName());
                    tv_project_details_project_site_address.setText(clientProjectData.getDoorNumber() + "\n" + clientProjectData.getBuildingName());
                    tv_project_details_cp_name.setText(clientProjectData.getContactPersonName());
                    tv_project_details_cp_phone.setText(clientProjectData.getContactPersonPhone());
                    if (clientProjectData.getProjectType().equals("1"))
                        tv_project_details_project_type.setText("New");
                    else if (clientProjectData.getProjectType().equals("2"))
                        tv_project_details_project_type.setText("Renovation");
                    tv_project_details_property_type.setText(clientProjectData.getPropertySize().getSizeValue());
                    tv_project_details_bill_service.setText(clientProjectData.getPackage().getPackageName());
                    tv_project_details_bill_service_price.setText(clientProjectData.getPackageAmount());
                    tv_project_details_coupon_discount_price.setText("0");
                    tv_project_details_bill_total_price.setText(clientProjectData.getAmountPaid());
                    Log.d("scope size", String.valueOf(clientProjectData.getScope().size()));
                    if (clientProjectData.getScope() != null) {
                        for (int i = 0; i < clientProjectData.getScope().size(); i++) {
                            if (clientProjectData.getScope().get(i) == 1)
                                cb_project_details_electrical.setChecked(true);
                            if (clientProjectData.getScope().get(i) == 2)
                                cb_project_details_plumbing.setChecked(true);
                            if (clientProjectData.getScope().get(i) == 3)
                                cb_project_details_plastering.setChecked(true);
                            if (clientProjectData.getScope().get(i) == 4)
                                cb_project_details_flooring.setChecked(true);

                        }
                    }

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
                    Log.d("isNestedScrollingEnabled", "true");
                // else Log.d("isNestedScrollingEnabled","false");
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // end of the scroll view
                    Log.d("isNestedScrollingEnabled", "false");
                }
                //Log.d("ScrollView", "scrollX_" + scrollX + "_scrollY_" + scrollY + "_oldScrollX_" + oldScrollX + "_oldScrollY_" + oldScrollY);
                //Do something
            }
        });
        mViewModel.getFileRequestResult().observe(getViewLifecycleOwner(), new Observer<FileRequestResponse>() {
            @Override
            public void onChanged(FileRequestResponse fileRequestResponse) {
                if(fileRequestResponse!=null) {
                    Snackbar.make(getView(),fileRequestResponse.getStatus().getMessage(),Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_project_details_cancel:
                /*mViewModel.getRefundAmound(booking_id);
                mViewModel.getRefundAmoundResult().observe(this, new Observer<RefundAmountResponse>() {
                    @Override
                    public void onChanged(RefundAmountResponse refundAmountResponse) {
                        if (refundAmountResponse != null) {

                            popup.showCancelRequestConfirmationPopupWindow(booking_id, refundAmountResponse.getRefundAmount(), tv_project_details_cancel);
                        }

                    }
                });*/
                getRefundAmound(booking_id);
                break;
            case R.id.tv_project_details_cp_phone:
                String number=tv_project_details_cp_phone.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));
                startActivity(callIntent);
                break;
             case R.id.tv_project_details_reshedule:
                break;

            case R.id.iv_project_details_edit:
                popup.showEditProjectPopupWindow(getActivity(), getView(), projectData, mViewModel);
                break;
            case R.id.btn_pt_send_file:
                if(isFinalProcessCompleted)
                    mViewModel.sendFileRequest(booking_id);

                break;

        }
    }

    public void getRefundAmound(String booking_id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RefundAmountResponse> call = apiInterface.getRefundAmount(booking_id);
        call.enqueue(new Callback<RefundAmountResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<RefundAmountResponse> call, Response<RefundAmountResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("RefundAmountResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    if (response.body() != null)
                        //refundAmountResponseMutableLiveData.setValue(response.body());
                        popup.showCancelRequestConfirmationPopupWindow(booking_id, response.body().getRefundAmount(), tv_project_details_cancel, mViewModel);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<RefundAmountResponse> call, Throwable t) {
                Log.e("RefundAmountResponse error", t.getMessage());
                //refundAmountResponseMutableLiveData.setValue(null);
            }
        });
    }
}