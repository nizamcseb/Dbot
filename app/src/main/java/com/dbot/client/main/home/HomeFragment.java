package com.dbot.client.main.home;

import static com.dbot.client.common.CommonFunctions.findCityPosition;
import static com.dbot.client.main.MainActivity.book_date;
import static com.dbot.client.main.MainActivity.slot_time_id;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.common.CommonFunctions;
import com.dbot.client.common.Popup;
import com.dbot.client.common.SessionManager;
import com.dbot.client.login.city.CityAdapter;
import com.dbot.client.login.LoginViewModel;
import com.dbot.client.login.city.CityData;
import com.dbot.client.main.MainActivity;
import com.dbot.client.main.home.adapter.TCAdapter;
import com.dbot.client.main.home.model.AvailableSlotsData;
import com.dbot.client.main.home.model.NotifySlotRequestResponse;
import com.dbot.client.main.home.model.TermsAndConditionsResponse;
import com.dbot.client.main.newrequest.Request1Fragment;
import com.dbot.client.retrofit.Status;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, CalendarView.OnDateChangeListener {
    SessionManager sessionManager;
    private HomeViewModel homeViewModel;
    private LoginViewModel loginViewModel;
    NestedScrollView ns_home;
    List<CityData> cityDataList;
    Spinner spCity;
    CalendarView cView;
    LinearLayout llAvailableSlots, ll_vision_mission, ll_send_quick_msg, ll_terms_and_conditions;
    Button btn_continue, btn_book_documentation, btn_slot_1, btn_slot_2, btn_quick_msg_send;
    TextView tv_product_msg, tv_available_message, tv_support_mail, tv_tc;
    ImageView iv_2d, iv_360, iv_3d, iv_close_quick_msg, iv_close_terms_condition,iv_tri_2d,iv_tri_360,iv_tri_3d;
    EditText et_quick_msg;
    ListView lv_Tc;
    boolean btn1Status = false, btn2Status = false;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        sessionManager = new SessionManager(getContext());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ns_home = root.findViewById(R.id.ns_home);
        spCity = root.findViewById(R.id.sp_city);
        tv_available_message = root.findViewById(R.id.tv_available_message);
        tv_available_message.setVisibility(View.INVISIBLE);
        cView = root.findViewById(R.id.calendarView);
        llAvailableSlots = root.findViewById(R.id.ll_available_slots);
        iv_2d = root.findViewById(R.id.iv_2d);
        iv_360 = root.findViewById(R.id.iv_360);
        iv_3d = root.findViewById(R.id.iv_3d);
        iv_tri_2d = root.findViewById(R.id.iv_tri_2d);
        iv_tri_360 = root.findViewById(R.id.iv_tri_360);
        iv_tri_3d = root.findViewById(R.id.iv_tri_3d);
        tv_product_msg = root.findViewById(R.id.tv_product_msg);
        ll_vision_mission = root.findViewById(R.id.ll_vision_mission);
        ll_send_quick_msg = root.findViewById(R.id.ll_send_quick_msg);
        ll_terms_and_conditions = root.findViewById(R.id.ll_terms_and_conditions);
        iv_close_quick_msg = root.findViewById(R.id.iv_close_quick_msg);
        iv_close_terms_condition = root.findViewById(R.id.iv_close_terms_condition);
        tv_support_mail = root.findViewById(R.id.tv_support_mail);
        et_quick_msg = root.findViewById(R.id.et_quick_msg);
        tv_tc = root.findViewById(R.id.tv_tc);
        btn_quick_msg_send = root.findViewById(R.id.btn_quick_msg_send);
        lv_Tc = root.findViewById(R.id.lv_Tc);
        btn_book_documentation = root.findViewById(R.id.btn_book_documentation);
        btn_continue = root.findViewById(R.id.btn_continue);
        btn_continue.setEnabled(false);
        btn_slot_1 = root.findViewById(R.id.btn_slot_1);
        btn_slot_2 = root.findViewById(R.id.btn_slot_2);
        Log.d("calendarView ", getSelectedDate(cView.getDate()));
        cView.setOnDateChangeListener(this::onSelectedDayChange);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getAvailableSlots(getSelectedDate(cView.getDate()));
        homeViewModel.getAvailableSlotsResult().observe(this, new Observer<List<AvailableSlotsData>>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("LongLogTag")
            @Override
            public void onChanged(List<AvailableSlotsData> availableSlotsData) {
                Log.d("getAvailableSlotsResponse", new GsonBuilder().setPrettyPrinting().create().toJson(availableSlotsData));
                createAvailableSlots(availableSlotsData);
            }
        });
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getCityData();
        loginViewModel.getCityResult().observe(this, new Observer<List<CityData>>() {
            @Override
            public void onChanged(List<CityData> cityData) {
                cityDataList = cityData;
                CityAdapter cityAdapter = new CityAdapter(getActivity(),getContext(), cityDataList);
                spCity.setAdapter(cityAdapter);
                int position = findCityPosition(cityDataList, sessionManager.getCity());
                spCity.setSelection(position);
                spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.d("selected city", cityDataList.get(i).getWorkingCity());
                        if (cityDataList.get(i).getWorkingCity().equals("0")) {
                            tv_available_message.setVisibility(View.VISIBLE);
                        } else {
                            tv_available_message.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
        homeViewModel.getQuickMessageResult().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if (status != null) {
                    Snackbar.make(btn_quick_msg_send, status.getMessage(), Snackbar.LENGTH_SHORT).show();
                    et_quick_msg.setText("");
                }
            }
        });
        homeViewModel.getTCResult().observe(this, new Observer<TermsAndConditionsResponse>() {
            @Override
            public void onChanged(TermsAndConditionsResponse termsAndConditionsResponse) {
                if (termsAndConditionsResponse != null) {
                    TCAdapter tcAdapter = new TCAdapter(getContext(), termsAndConditionsResponse.getData());
                    lv_Tc.setAdapter(tcAdapter);
                }

            }
        });
        homeViewModel.getNotifySlotAvailableRequestResult().observe(this, new Observer<NotifySlotRequestResponse>() {
            @Override
            public void onChanged(NotifySlotRequestResponse notifySlotRequestResponse) {
                if (notifySlotRequestResponse.getStatus().getCode() == 1035) {
                    Popup popup = new Popup();
                    popup.showNotifyRequestPopupWindow(btn_continue);
                }
            }
        });
        btn_continue.setOnClickListener(this::onClick);
        btn_book_documentation.setOnClickListener(this::onClick);
        iv_close_quick_msg.setOnClickListener(this::onClick);
        iv_close_terms_condition.setOnClickListener(this::onClick);
        tv_support_mail.setOnClickListener(this::onClick);
        tv_tc.setOnClickListener(this::onClick);
        btn_quick_msg_send.setOnClickListener(this::onClick);
        //Home2
        iv_2d.setOnClickListener(this::onClick);
        iv_360.setOnClickListener(this::onClick);
        iv_3d.setOnClickListener(this::onClick);
        setDefault2D();
        return root;
    }

    private void setDefault2D() {
        setTriangle("2d");
        iv_2d.setBackground(getContext().getDrawable(R.drawable.ic_2d_drawing_dark));
        iv_360.setBackground(getContext().getDrawable(R.drawable.ic_360_photos_light));
        iv_3d.setBackground(getContext().getDrawable(R.drawable.ic_3d_drawing_light));
        tv_product_msg.setText(getString(R.string.two_d_drawing_product_msg));
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createAvailableSlots(List<AvailableSlotsData> availableSlotsData) {
        book_date = getSelectedDate(cView.getDate());
        if (availableSlotsData.size() == 2) {
            btn_slot_1.setVisibility(View.VISIBLE);
            btn_slot_2.setVisibility(View.VISIBLE);
            btn_slot_1.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_not_available_button));
            btn_slot_2.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_not_available_button));
            btn_continue.setEnabled(false);
            btn_continue.setBackgroundColor(getContext().getColor(R.color.greyed_out));
            btn_continue.setText(R.string.btn_txt_continue);
            btn_continue.setTextColor(getContext().getColor(R.color.white));
            btn_slot_1.setText(availableSlotsData.get(0).getSlotTime());
            btn_slot_2.setText(availableSlotsData.get(1).getSlotTime());
            if (availableSlotsData.get(0).getAvailableStatus()) {
                btn_slot_1.setTextColor(getContext().getColor(R.color.black));
            } else {
                btn_slot_1.setTextColor(getContext().getColor(R.color.greyed_out));
            }
            if (availableSlotsData.get(1).getAvailableStatus()) {
                btn_slot_2.setTextColor(getContext().getColor(R.color.black));
            } else {
                btn_slot_2.setTextColor(getContext().getColor(R.color.greyed_out));
            }

        }

        btn_slot_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn1Status = true;
                if (btn2Status) {
                    btn2Status = false;
                    btn_continue.setEnabled(false);
                    btn_continue.setText(R.string.btn_txt_continue);
                    btn_continue.setBackgroundColor(getContext().getColor(R.color.greyed_out));
                    if (availableSlotsData.get(1).getAvailableStatus()) {
                        btn_slot_2.setTextColor(getContext().getColor(R.color.black));
                        btn_slot_2.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_not_available_button));
                    } else {
                        btn_slot_2.setTextColor(getContext().getColor(R.color.greyed_out));
                        btn_slot_2.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_not_available_button));
                    }
                }
                slot_time_id = Integer.parseInt(availableSlotsData.get(0).getId());
                if (availableSlotsData.get(0).getAvailableStatus()) {

                    btn_continue.setEnabled(true);
                    btn_continue.setBackgroundColor(getContext().getColor(R.color.primary_varient));
                    btn_continue.setText(R.string.btn_txt_continue);
                    btn_continue.setTag("1");
                    btn_continue.setTextColor(getContext().getColor(R.color.white));
                    btn_slot_1.setTextColor(getContext().getColor(R.color.white));
                    btn_slot_1.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_available_button));
                } else {

                    btn_continue.setEnabled(true);
                    btn_continue.setBackgroundColor(getContext().getColor(R.color.purple_profile));
                    btn_continue.setText(R.string.btn_txt_notify_me);
                    btn_continue.setTag("0");
                    btn_continue.setTextColor(getContext().getColor(R.color.white));
                    btn_slot_1.setTextColor(getContext().getColor(R.color.white));
                    btn_slot_1.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_notify_button));
                }
            }
        });
        btn_slot_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2Status = true;
                if (btn1Status) {
                    btn1Status = false;
                    btn_continue.setEnabled(false);
                    btn_continue.setText(R.string.btn_txt_continue);
                    btn_continue.setBackgroundColor(getContext().getColor(R.color.greyed_out));
                    if (availableSlotsData.get(0).getAvailableStatus()) {
                        btn_slot_1.setTextColor(getContext().getColor(R.color.black));
                        btn_slot_1.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_not_available_button));
                    } else {
                        btn_slot_1.setTextColor(getContext().getColor(R.color.greyed_out));
                        btn_slot_1.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_not_available_button));
                    }
                }
                slot_time_id = Integer.parseInt(availableSlotsData.get(1).getId());
                if (availableSlotsData.get(1).getAvailableStatus()) {

                    btn_continue.setEnabled(true);
                    btn_continue.setBackgroundColor(getContext().getColor(R.color.primary_varient));
                    btn_continue.setText(R.string.btn_txt_continue);
                    btn_continue.setTag("1");
                    btn_continue.setTextColor(getContext().getColor(R.color.white));
                    btn_slot_2.setTextColor(getContext().getColor(R.color.white));
                    btn_slot_2.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_available_button));
                } else {

                    btn_continue.setEnabled(true);
                    btn_continue.setBackgroundColor(getContext().getColor(R.color.purple_profile));
                    btn_continue.setText(R.string.btn_txt_notify_me);
                    btn_continue.setTag("0");
                    btn_continue.setTextColor(getContext().getColor(R.color.white));
                    btn_slot_2.setTextColor(getContext().getColor(R.color.white));
                    btn_slot_2.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_notify_button));
                }
            }
        });

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                if (btn_continue.getTag().equals("1")) {
                    if (cityDataList.get(spCity.getSelectedItemPosition()).getWorkingCity().equals("1")) {
                        MainActivity.city = Integer.parseInt(cityDataList.get(spCity.getSelectedItemPosition()).getId());
                        Request1Fragment request1Fragment = new Request1Fragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, request1Fragment);
                        fragmentTransaction.commit();
                        Log.d("btn_continue", "continue" + slot_time_id + " " + MainActivity.city);
                    } else
                        CommonFunctions.shakeAnimation(tv_available_message, 1000);
                } else if (btn_continue.getTag().equals("0")) {
                    Log.d("btn_continue", "notify");
                    homeViewModel.sendNotifySlotAvailableRequest(sessionManager.getClientId(), getSelectedDate(cView.getDate()),String.valueOf(slot_time_id));
                }
                break;
            case R.id.btn_book_documentation:
                ns_home.fullScroll(View.FOCUS_UP);
                break;
            case R.id.iv_2d:
                setTriangle("2d");
                iv_2d.setBackground(getContext().getDrawable(R.drawable.ic_2d_drawing_dark));
                iv_360.setBackground(getContext().getDrawable(R.drawable.ic_360_photos_light));
                iv_3d.setBackground(getContext().getDrawable(R.drawable.ic_3d_drawing_light));
                tv_product_msg.setText(getString(R.string.two_d_drawing_product_msg));
                break;
            case R.id.iv_360:
                setTriangle("360");
                iv_2d.setBackground(getContext().getDrawable(R.drawable.ic_2d_drawing_light));
                iv_360.setBackground(getContext().getDrawable(R.drawable.ic_360_photos_dark));
                iv_3d.setBackground(getContext().getDrawable(R.drawable.ic_3d_drawing_light));
                tv_product_msg.setText(getString(R.string.threesixty_product_msg));
                break;
            case R.id.iv_3d:
                setTriangle("3d");
                iv_2d.setBackground(getContext().getDrawable(R.drawable.ic_2d_drawing_light));
                iv_360.setBackground(getContext().getDrawable(R.drawable.ic_360_photos_light));
                iv_3d.setBackground(getContext().getDrawable(R.drawable.ic_3d_drawing_dark));
                tv_product_msg.setText(getString(R.string.three_d_model_product_msg));
                break;

            case R.id.tv_support_mail:
                ll_vision_mission.setVisibility(View.GONE);
                ll_send_quick_msg.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_tc:
                ll_vision_mission.setVisibility(View.GONE);
                ll_terms_and_conditions.setVisibility(View.VISIBLE);
                homeViewModel.getTCData();
                break;
            case R.id.iv_close_quick_msg:
                ll_vision_mission.setVisibility(View.VISIBLE);
                ll_send_quick_msg.setVisibility(View.GONE);
                break;
            case R.id.iv_close_terms_condition:
                ll_vision_mission.setVisibility(View.VISIBLE);
                ll_terms_and_conditions.setVisibility(View.GONE);
                break;
            case R.id.btn_quick_msg_send:
                if (et_quick_msg.getText().toString().equals(""))
                    Snackbar.make(btn_quick_msg_send, "Please enter message to send", Snackbar.LENGTH_SHORT).show();
                else
                    homeViewModel.sendMessage(sessionManager.getClientId(), et_quick_msg.getText().toString());

                break;

        }
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
        i1 = i1 + 1;
        String date = String.valueOf(i2 + "-" + i1 + "-" + i);
        Log.d("calendarView ", i2 + "-" + i1 + "-" + i);
        //Log.d("calendarView ", getSelectedDate(calendarView.getDate()));
        homeViewModel.getAvailableSlots(date);
    }

    private String getSelectedDate(long date) {
        String dateString = DateFormat.format("dd-MM-yyyy", new Date(date)).toString();
        return dateString;
    }

    private void setTriangle(String prod) {
        if(prod.equals("2d")){
            iv_tri_2d.setVisibility(View.VISIBLE);
            iv_tri_360.setVisibility(View.INVISIBLE);
            iv_tri_3d.setVisibility(View.INVISIBLE);
        }
        if(prod.equals("360")){
            iv_tri_2d.setVisibility(View.INVISIBLE);
            iv_tri_360.setVisibility(View.VISIBLE);
            iv_tri_3d.setVisibility(View.INVISIBLE);
        }
        if(prod.equals("3d")){
            iv_tri_2d.setVisibility(View.INVISIBLE);
            iv_tri_360.setVisibility(View.INVISIBLE);
            iv_tri_3d.setVisibility(View.VISIBLE);
        }
    }

}