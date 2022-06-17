package com.dbot.client.common;

import static com.dbot.client.common.CommonFunctions.getSelectedDate;
import static com.dbot.client.main.MainActivity.book_date;
import static com.dbot.client.main.MainActivity.sessionManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.dbot.client.R;
import com.dbot.client.common.city.CityAdapter;
import com.dbot.client.common.city.CityData;
import com.dbot.client.common.city.SaveCity;
import com.dbot.client.main.MainActivity;
import com.dbot.client.main.home.HomeViewModel;
import com.dbot.client.main.home.model.AvailableSlotsData;
import com.dbot.client.main.projects.details.ProjectFullDetailsViewlModel;
import com.dbot.client.main.projects.details.model.CancelRequestResponse;
import com.dbot.client.main.projects.details.model.RefundAmount;
import com.dbot.client.main.projects.details.model.ResheduleResponse;
import com.dbot.client.main.projects.details.model.UpdateProject;
import com.dbot.client.main.projects.details.model.UpdateProjectResponse;
import com.dbot.client.main.projects.model.ClientProjectData;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Popup implements CompoundButton.OnCheckedChangeListener {
    public Popup() {

    }


    public void showNotifyRequestPopupWindow(final View view) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_notify_me, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = false;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button btn_back_to_home = popupView.findViewById(R.id.btn_back_to_home);
        btn_back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        //Handler for clicking on the inactive zone of the window

       /* popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                //popupWindow.dismiss();
                return true;
            }
        });*/
    }

    public void showCityNotAvailablePopupWindow(final View view) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_city_not_available, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = false;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button btn_popup_exit = popupView.findViewById(R.id.btn_popup_exit);
        btn_popup_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        Button btn_popup_save = popupView.findViewById(R.id.btn_popup_save);
        btn_popup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public void showCancelRequestConfirmationPopupWindow(String booking_id, RefundAmount refundAmount, final View view, ProjectFullDetailsViewlModel mViewModel) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_cancel_request_confirmation, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = false;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        ImageView iv_popup_crc_close = popupView.findViewById(R.id.iv_popup_crc_close);
        iv_popup_crc_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        LinearLayout ll_popup_crc_end = popupView.findViewById(R.id.ll_popup_crc_end);
        LinearLayout ll_popup_crc = popupView.findViewById(R.id.ll_popup_crc);
        TextView tv_popup_crc_refund_amount_with_req_id = popupView.findViewById(R.id.tv_popup_crc_refund_amount_with_req_id);
        TextView tv_popup_crc_refund_amount_with_msg = popupView.findViewById(R.id.tv_popup_crc_refund_amount_with_msg);
        tv_popup_crc_refund_amount_with_msg.setText("You will be eligible for a refund of Rs." + refundAmount.getRefundAmount());
        Button btn_popup_crc_exit = popupView.findViewById(R.id.btn_popup_crc_exit);
        btn_popup_crc_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                mViewModel.getProjectTracking(booking_id);
            }
        });
        Button btn_popup_crc_cancel = popupView.findViewById(R.id.btn_popup_crc_cancel);
        btn_popup_crc_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelRequest(popupWindow, tv_popup_crc_refund_amount_with_req_id, ll_popup_crc, ll_popup_crc_end, booking_id);
            }
        });
    }

    public void cancelRequest(PopupWindow popupWindow, TextView tv_popup_crc_refund_amount_with_req_id, LinearLayout ll_popup_crc, LinearLayout ll_popup_crc_end, String booking_id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CancelRequestResponse> call = apiInterface.cancelRequest(booking_id);
        call.enqueue(new Callback<CancelRequestResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<CancelRequestResponse> call, Response<CancelRequestResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("CancelRequestResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    //if (response.body() !=null)
                    ll_popup_crc.setVisibility(View.GONE);
                    ll_popup_crc_end.setVisibility(View.VISIBLE);
                    tv_popup_crc_refund_amount_with_req_id.setText("Your request" + "#" + booking_id + " has been cancelled." + "\n\n" + "As per Cancellation and Refund Policy, a refund of Rs." + response.body().getRefundAmount().getRefundAmount() + " has been initiated." + "\n\n" + "Track your refunds under Refund Status");

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<CancelRequestResponse> call, Throwable t) {
                Log.e("CancelRequestResponse error", t.getMessage());

            }
        });
    }

    EditText et_edit_popup_door_no, et_edit_popup_building_name, et_edit_popup_landmark, et_edit_popup_contact_person_name, et_edit_popup_contact_person_phone_number;
    List<Integer> scope = null;

    public void showEditProjectPopupWindow(Activity activity, final View view, ClientProjectData projectData, ProjectFullDetailsViewlModel mViewModel) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_edit_project, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        scope = new ArrayList<Integer>();
        et_edit_popup_door_no = popupView.findViewById(R.id.et_edit_popup_door_no);
        et_edit_popup_building_name = popupView.findViewById(R.id.et_edit_popup_building_name);
        et_edit_popup_landmark = popupView.findViewById(R.id.et_edit_popup_landmark);

        //et_edit_popup_project_name = popupView.findViewById(R.id.et_edit_popup_project_name);
        et_edit_popup_contact_person_name = popupView.findViewById(R.id.et_edit_popup_contact_person_name);
        et_edit_popup_contact_person_phone_number = popupView.findViewById(R.id.et_edit_popup_contact_person_phone_number);

        SeekBar sb_edit_popup_size_of_property = popupView.findViewById(R.id.sb_edit_popup_size_of_property);
        TextView tv_edit_popup_size_of_property = popupView.findViewById(R.id.tv_edit_popup_size_of_property);
        if (sb_edit_popup_size_of_property != null) {
            sb_edit_popup_size_of_property.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (seekBar.getProgress() == 0)
                        tv_edit_popup_size_of_property.setText("1BHK");
                    if (seekBar.getProgress() == 1)
                        tv_edit_popup_size_of_property.setText("2BHK");
                    if (seekBar.getProgress() == 2)
                        tv_edit_popup_size_of_property.setText("3BHK");
                    if (seekBar.getProgress() == 3)
                        tv_edit_popup_size_of_property.setText("4BHK");
                    if (seekBar.getProgress() == 4)
                        tv_edit_popup_size_of_property.setText("5BHK+");

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        CheckBox cb_edit_popup_same_as_mine = popupView.findViewById(R.id.cb_edit_popup_same_as_mine);
        CheckBox cb_edit_popup_electrical = popupView.findViewById(R.id.cb_edit_popup_electrical);
        CheckBox cb_edit_popup_plumbing = popupView.findViewById(R.id.cb_edit_popup_plumbing);
        CheckBox cb_edit_popup_plastering = popupView.findViewById(R.id.cb_edit_popup_plastering);
        CheckBox cb_edit_popup_flooring = popupView.findViewById(R.id.cb_edit_popup_flooring);
        RadioGroup rg_edit_popup_type_of_property = popupView.findViewById(R.id.rg_edit_popup_type_of_property);
        RadioButton rb_edit_popup_type_of_property_new = popupView.findViewById(R.id.rb_edit_popup_type_of_property_new);
        RadioButton rb_edit_popup_type_of_property_renovation = popupView.findViewById(R.id.rb_edit_popup_type_of_property_renovation);

        if (projectData.getScope() != null) {
            for (int i = 0; i < projectData.getScope().size(); i++) {
                if (projectData.getScope().get(i) == 1) {
                    cb_edit_popup_electrical.setChecked(true);
                    scope.add(1);
                }
                if (projectData.getScope().get(i) == 2) {
                    cb_edit_popup_plumbing.setChecked(true);
                    scope.add(2);
                }
                if (projectData.getScope().get(i) == 3) {
                    cb_edit_popup_plastering.setChecked(true);
                    scope.add(3);
                }
                if (projectData.getScope().get(i) == 4) {
                    cb_edit_popup_flooring.setChecked(true);
                    scope.add(4);
                }

            }
        }
        cb_edit_popup_same_as_mine.setOnCheckedChangeListener(this::onCheckedChanged);
        cb_edit_popup_electrical.setOnCheckedChangeListener(this::onCheckedChanged);
        cb_edit_popup_plumbing.setOnCheckedChangeListener(this::onCheckedChanged);
        cb_edit_popup_plastering.setOnCheckedChangeListener(this::onCheckedChanged);
        cb_edit_popup_flooring.setOnCheckedChangeListener(this::onCheckedChanged);
        et_edit_popup_door_no.setText(projectData.getDoorNumber());
        et_edit_popup_building_name.setText(projectData.getBuildingName());
        if (projectData.getLandmark() != null)
            et_edit_popup_landmark.setText(projectData.getLandmark());
        //et_edit_popup_project_name.setText(projectData.getProjectName());
        et_edit_popup_contact_person_name.setText(projectData.getContactPersonName());
        et_edit_popup_contact_person_phone_number.setText(projectData.getContactPersonPhone());

        sb_edit_popup_size_of_property.setProgress(Integer.parseInt(projectData.getPropertySize().getId()) - 1);
        tv_edit_popup_size_of_property.setText(projectData.getPropertySize().getSizeValue());
        if (projectData.getProjectType().equals("1"))
            rg_edit_popup_type_of_property.check(rb_edit_popup_type_of_property_new.getId());
        else if (projectData.getProjectType().equals("2"))
            rg_edit_popup_type_of_property.check(rb_edit_popup_type_of_property_renovation.getId());

        Button btn_edit_popup_exit = popupView.findViewById(R.id.btn_edit_popup_exit);
        btn_edit_popup_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        Button btn_edit_popup_save = popupView.findViewById(R.id.btn_edit_popup_save);
        btn_edit_popup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = rg_edit_popup_type_of_property.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) popupView.findViewById(selectedId);
                UpdateProject updateProject = new UpdateProject();
                updateProject.setClientId(projectData.getClientId());
                updateProject.setBookingId(projectData.getBookingId());
                updateProject.setDoorNumber(et_edit_popup_door_no.getText().toString());
                updateProject.setBuildingName(et_edit_popup_building_name.getText().toString());
                updateProject.setLandmark(et_edit_popup_landmark.getText().toString());
                //updateProject.setProjectName(et_edit_popup_project_name.getText().toString());
                updateProject.setContactPersonName(et_edit_popup_contact_person_name.getText().toString());
                updateProject.setContactPersonPhone(et_edit_popup_contact_person_phone_number.getText().toString());
                updateProject.setPropertySize(sb_edit_popup_size_of_property.getProgress() + 1);
                updateProject.setProjectType(Integer.parseInt(radioButton.getTag().toString()));
                updateProject.setScope(scope);

                if (checkManditoryFields(btn_edit_popup_save)) {
                    mViewModel.updateProject(updateProject);
                    mViewModel.getUpdateProjectResult().observe((LifecycleOwner) activity, new Observer<UpdateProjectResponse>() {
                        @Override
                        public void onChanged(UpdateProjectResponse response) {
                            Log.d("updateProject result", new GsonBuilder().setPrettyPrinting().create().toJson(updateProject));
                            if (response != null) {
                                if (response.getStatus().getCode() == 1039) {
                                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                                    mViewModel.getProject(response.getClientProjectData());
                                    popupWindow.dismiss();

                                } else
                                    Snackbar.make(btn_edit_popup_save, response.getStatus().getMessage(), Snackbar.LENGTH_SHORT).show();
                            } else
                                Snackbar.make(btn_edit_popup_save, "Somthing went wrong", Snackbar.LENGTH_SHORT).show();
                        }
                    });

                }
                /*if(MainActivity.scope.size() == 0) {
                    Snackbar.make(btn_edit_popup_save,"Select atleast one scope",Snackbar.LENGTH_SHORT).show();
                }*/
            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_edit_popup_same_as_mine:
                if (b) {
                    et_edit_popup_contact_person_name.setText(sessionManager.getClientFullName());
                    et_edit_popup_contact_person_phone_number.setText(sessionManager.getClientPhone());
                } else {
                    et_edit_popup_contact_person_name.setText("");
                    et_edit_popup_contact_person_phone_number.setText("");
                }
                break;
            case R.id.cb_edit_popup_electrical:
                if (b)
                    scope.add(1);
                else scope.removeIf(i -> i == 1);
                Log.d("scope", new GsonBuilder().setPrettyPrinting().create().toJson(scope));
                break;
            case R.id.cb_edit_popup_plumbing:
                if (b)
                    scope.add(2);
                else scope.removeIf(i -> i == 2);
                Log.d("scope", new GsonBuilder().setPrettyPrinting().create().toJson(scope));
                break;
            case R.id.cb_edit_popup_plastering:
                if (b)
                    scope.add(3);
                else scope.removeIf(i -> i == 3);
                Log.d("scope", new GsonBuilder().setPrettyPrinting().create().toJson(scope));
                break;
            case R.id.cb_edit_popup_flooring:
                if (b)
                    scope.add(4);
                else scope.removeIf(i -> i == 4);
                Log.d("scope", new GsonBuilder().setPrettyPrinting().create().toJson(MainActivity.scope));
                break;
        }
    }

    private boolean checkManditoryFields(View view) {
        if (et_edit_popup_door_no.getText().toString().equals("")) {
            et_edit_popup_door_no.setError("Required");
            return false;
        }
        if (et_edit_popup_building_name.getText().toString().equals("")) {
            et_edit_popup_building_name.setError("Required");
            return false;
        }
        /*if (et_edit_popup_project_name.getText().toString().equals("")) {
            et_edit_popup_project_name.setError("Required");
            return false;
        }*/
        if (et_edit_popup_contact_person_name.getText().toString().equals("")) {
            et_edit_popup_contact_person_name.setError("Required");
            return false;
        }
        if (et_edit_popup_contact_person_phone_number.getText().toString().equals("")) {
            et_edit_popup_contact_person_phone_number.setError("Required");
            return false;
        }
        if (scope.size() == 0) {
            Snackbar.make(view, "Select atleast one scope", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    CalendarView cView;
    int slot_time_id = 0;
    String date = "";
    Button btn_slot_1, btn_slot_2, btn_edit_popup_save;
    boolean btn1Status = false, btn2Status = false;

    public void showReshedulePopupWindow(Context context, ViewModelStoreOwner viewModelStoreOwner, LifecycleOwner lifecycleOwner, String booking_id, final View view, ProjectFullDetailsViewlModel mViewModel, String client_id) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_reshedule, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = false;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        cView = popupView.findViewById(R.id.calendarView);
        date = getSelectedDate(cView.getDate());
        btn_slot_1 = popupView.findViewById(R.id.btn_slot_1);
        btn_slot_2 = popupView.findViewById(R.id.btn_slot_2);
        HomeViewModel homeViewModel = new ViewModelProvider(viewModelStoreOwner).get(HomeViewModel.class);
        homeViewModel.getAvailableSlots(date);
        homeViewModel.getAvailableSlotsResult().observe(lifecycleOwner, new Observer<List<AvailableSlotsData>>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("LongLogTag")
            @Override
            public void onChanged(List<AvailableSlotsData> availableSlotsData) {
                Log.d("getAvailableSlotsResponse", new GsonBuilder().setPrettyPrinting().create().toJson(availableSlotsData));
                createAvailableSlots(context, availableSlotsData);
            }
        });
        mViewModel.getResheduleResponseResult().observe(lifecycleOwner, new Observer<ResheduleResponse>() {
            @Override
            public void onChanged(ResheduleResponse resheduleResponse) {
                if (resheduleResponse != null) {
                    if (resheduleResponse.getStatus().getCode() == 1031) {
                        homeViewModel.getAvailableSlots(getSelectedDate(cView.getDate()));
                        Toast.makeText(context, resheduleResponse.getResheduleData().getRequestId() + " " + "Request Send", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    } else
                        Toast.makeText(context, resheduleResponse.getStatus().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        cView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                i1 = i1 + 1;
                date = String.valueOf(i2 + "-" + i1 + "-" + i);
                Log.d("calendarView ", i2 + "-" + i1 + "-" + i);
                //Log.d("calendarView ", getSelectedDate(calendarView.getDate()));
                homeViewModel.getAvailableSlots(date);
            }
        });
        Button btn_edit_popup_exit = popupView.findViewById(R.id.btn_edit_popup_exit);
        btn_edit_popup_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

            }
        });
        btn_edit_popup_save = popupView.findViewById(R.id.btn_edit_popup_save);
        btn_edit_popup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("btn_edit_popup_save", String.valueOf(slot_time_id));
                if (btn_edit_popup_save.getTag().equals("1")) {
                    mViewModel.resheduleSlot(booking_id, client_id, date, slot_time_id);
                } else if (btn_edit_popup_save.getTag().equals("0")) {
                    Log.d("btn_continue", "notify");
                    //homeViewModel.sendNotifySlotAvailableRequest(sessionManager.getClientId(), getSelectedDate(cView.getDate()),String.valueOf(slot_time_id));
                }
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createAvailableSlots(Context context, List<AvailableSlotsData> availableSlotsData) {
        book_date = getSelectedDate(cView.getDate());
        if (availableSlotsData.size() == 2) {
            btn_slot_1.setVisibility(View.VISIBLE);
            btn_slot_2.setVisibility(View.VISIBLE);
            btn_slot_1.setBackground(context.getDrawable(R.drawable.bg_preferred_slot_not_available_button));
            btn_slot_2.setBackground(context.getDrawable(R.drawable.bg_preferred_slot_not_available_button));
            btn_edit_popup_save.setEnabled(false);
            btn_edit_popup_save.setBackgroundColor(context.getColor(R.color.greyed_out));
            btn_edit_popup_save.setText(R.string.btn_txt_save);
            btn_edit_popup_save.setTextColor(context.getColor(R.color.white));
            btn_slot_1.setText(availableSlotsData.get(0).getSlotTime());
            btn_slot_2.setText(availableSlotsData.get(1).getSlotTime());
            if (availableSlotsData.get(0).getAvailableStatus()) {
                btn_slot_1.setTextColor(context.getColor(R.color.black));
            } else {
                btn_slot_1.setTextColor(context.getColor(R.color.greyed_out));
            }
            if (availableSlotsData.get(1).getAvailableStatus()) {
                btn_slot_2.setTextColor(context.getColor(R.color.black));
            } else {
                btn_slot_2.setTextColor(context.getColor(R.color.greyed_out));
            }

        }

        btn_slot_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn1Status = true;
                if (btn2Status) {
                    btn2Status = false;
                    btn_edit_popup_save.setEnabled(false);
                    btn_edit_popup_save.setText(R.string.btn_txt_save);
                    btn_edit_popup_save.setBackgroundColor(context.getColor(R.color.greyed_out));
                    if (availableSlotsData.get(1).getAvailableStatus()) {
                        btn_slot_2.setTextColor(context.getColor(R.color.black));
                        btn_slot_2.setBackground(context.getDrawable(R.drawable.bg_preferred_slot_not_available_button));
                    } else {
                        btn_slot_2.setTextColor(context.getColor(R.color.greyed_out));
                        btn_slot_2.setBackground(context.getDrawable(R.drawable.bg_preferred_slot_not_available_button));
                    }
                }
                slot_time_id = Integer.parseInt(availableSlotsData.get(0).getId());
                if (availableSlotsData.get(0).getAvailableStatus()) {

                    btn_edit_popup_save.setEnabled(true);
                    btn_edit_popup_save.setBackgroundColor(context.getColor(R.color.primary_varient));
                    btn_edit_popup_save.setText(R.string.btn_txt_save);
                    btn_edit_popup_save.setTag("1");
                    btn_edit_popup_save.setTextColor(context.getColor(R.color.white));
                    btn_slot_1.setTextColor(context.getColor(R.color.white));
                    btn_slot_1.setBackground(context.getDrawable(R.drawable.bg_preferred_slot_available_button));
                } else {

                    btn_edit_popup_save.setEnabled(true);
                    btn_edit_popup_save.setBackgroundColor(context.getColor(R.color.purple_profile));
                    btn_edit_popup_save.setText(R.string.btn_txt_notify_me);
                    btn_edit_popup_save.setTag("0");
                    btn_edit_popup_save.setTextColor(context.getColor(R.color.white));
                    btn_slot_1.setTextColor(context.getColor(R.color.white));
                    btn_slot_1.setBackground(context.getDrawable(R.drawable.bg_preferred_slot_notify_button));
                }
            }
        });
        btn_slot_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2Status = true;
                if (btn1Status) {
                    btn1Status = false;
                    btn_edit_popup_save.setEnabled(false);
                    btn_edit_popup_save.setText(R.string.btn_txt_save);
                    btn_edit_popup_save.setBackgroundColor(context.getColor(R.color.greyed_out));
                    if (availableSlotsData.get(0).getAvailableStatus()) {
                        btn_slot_1.setTextColor(context.getColor(R.color.black));
                        btn_slot_1.setBackground(context.getDrawable(R.drawable.bg_preferred_slot_not_available_button));
                    } else {
                        btn_slot_1.setTextColor(context.getColor(R.color.greyed_out));
                        btn_slot_1.setBackground(context.getDrawable(R.drawable.bg_preferred_slot_not_available_button));
                    }
                }
                slot_time_id = Integer.parseInt(availableSlotsData.get(1).getId());
                if (availableSlotsData.get(1).getAvailableStatus()) {

                    btn_edit_popup_save.setEnabled(true);
                    btn_edit_popup_save.setBackgroundColor(context.getColor(R.color.primary_varient));
                    btn_edit_popup_save.setText(R.string.btn_txt_save);
                    btn_edit_popup_save.setTag("1");
                    btn_edit_popup_save.setTextColor(context.getColor(R.color.white));
                    btn_slot_2.setTextColor(context.getColor(R.color.white));
                    btn_slot_2.setBackground(context.getDrawable(R.drawable.bg_preferred_slot_available_button));
                } else {

                    btn_edit_popup_save.setEnabled(true);
                    btn_edit_popup_save.setBackgroundColor(context.getColor(R.color.purple_profile));
                    btn_edit_popup_save.setText(R.string.btn_txt_notify_me);
                    btn_edit_popup_save.setTag("0");
                    btn_edit_popup_save.setTextColor(context.getColor(R.color.white));
                    btn_slot_2.setTextColor(context.getColor(R.color.white));
                    btn_slot_2.setBackground(context.getDrawable(R.drawable.bg_preferred_slot_notify_button));
                }
            }
        });

    }
    public static void SearchCity(Activity activity, List<CityData> cityDataList, SaveCity saveCity) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_city_search);
        dialog.getWindow().setLayout(650, 800);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.d("dialog", "dismissed");
            }
        });
        dialog.show();
        EditText editText = dialog.findViewById(R.id.edit_text);
        ListView listView = dialog.findViewById(R.id.list_view);
        CityAdapter cityAdapter = new CityAdapter(activity, activity.getApplicationContext(), cityDataList);
        listView.setAdapter(cityAdapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("searchText", s.toString());
                cityAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityData cityData = (CityData) parent.getItemAtPosition(position);
                saveCity.cityData(cityData);
                dialog.dismiss();
            }
        });
    }
}
