package com.dbot.client.main.home;

import static com.dbot.client.common.CommonFunctions.findCityPosition;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.common.SessionManager;
import com.dbot.client.login.CityAdapter;
import com.dbot.client.login.LoginViewModel;
import com.dbot.client.login.model.CityData;
import com.dbot.client.main.home.model.AvailableSlotsData;
import com.dbot.client.main.newrequest.Request1Fragment;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, CalendarView.OnDateChangeListener {
    SessionManager sessionManager;
    private HomeViewModel mViewModel;
    private LoginViewModel loginViewModel;
    ExpandableLayout expandableLayout;
    List<CityData> cityDataList;
    Spinner spCity;
    CalendarView cView;
    LinearLayout llAvailableSlots, ll_vision_mission, ll_send_quick_msg, ll_terms_and_conditions;
    Button btn_continue, btn_slot_1, btn_slot_2;
    TextView tv_available_message, tv_support_mail, tv_tc;
    ImageView iv_close_quick_msg, iv_close_terms_condition;
    boolean btn1Status = false, btn2Status = false;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        sessionManager = new SessionManager(getContext());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        spCity = root.findViewById(R.id.sp_city);
        tv_available_message = root.findViewById(R.id.tv_available_message);
        tv_available_message.setVisibility(View.INVISIBLE);
        cView = root.findViewById(R.id.calendarView);
        llAvailableSlots = root.findViewById(R.id.ll_available_slots);
        ll_vision_mission = root.findViewById(R.id.ll_vision_mission);
        ll_send_quick_msg = root.findViewById(R.id.ll_send_quick_msg);
        ll_terms_and_conditions = root.findViewById(R.id.ll_terms_and_conditions);
        iv_close_quick_msg = root.findViewById(R.id.iv_close_quick_msg);
        iv_close_terms_condition = root.findViewById(R.id.iv_close_terms_condition);
        tv_support_mail = root.findViewById(R.id.tv_support_mail);
        tv_tc = root.findViewById(R.id.tv_tc);
        btn_continue = root.findViewById(R.id.btn_continue);
        btn_continue.setEnabled(false);
        btn_slot_1 = root.findViewById(R.id.btn_slot_1);
        btn_slot_2 = root.findViewById(R.id.btn_slot_2);
        Log.d("calendarView ", getSelectedDate(cView.getDate()));
        cView.setOnDateChangeListener(this::onSelectedDayChange);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mViewModel.getAvailableSlots(getSelectedDate(cView.getDate()));
        mViewModel.getAvailableSlotsResult().observe(this, new Observer<List<AvailableSlotsData>>() {
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
                CityAdapter cityAdapter = new CityAdapter(getContext(), cityDataList);
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
        btn_continue.setOnClickListener(this::onClick);
        iv_close_quick_msg.setOnClickListener(this::onClick);
        iv_close_terms_condition.setOnClickListener(this::onClick);
        tv_support_mail.setOnClickListener(this::onClick);
        tv_tc.setOnClickListener(this::onClick);
        return root;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createAvailableSlots(List<AvailableSlotsData> availableSlotsData) {

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
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                if (btn_continue.getTag().equals("1")) {
                    Request1Fragment request1Fragment = new Request1Fragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, request1Fragment);
                    fragmentTransaction.commit();
                } else if (btn_continue.getTag().equals("0")) {
                    Log.d("btn_continue", "notify");
                }
                break;
            case R.id.tv_support_mail:
                ll_vision_mission.setVisibility(View.GONE);
                ll_send_quick_msg.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_tc:
                ll_vision_mission.setVisibility(View.GONE);
                ll_terms_and_conditions.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_close_quick_msg:
                ll_vision_mission.setVisibility(View.VISIBLE);
                ll_send_quick_msg.setVisibility(View.GONE);
                break;
            case R.id.iv_close_terms_condition:
                ll_vision_mission.setVisibility(View.VISIBLE);
                ll_terms_and_conditions.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
        i1 = i1 + 1;
        String date = String.valueOf(i2 + "-" + i1 + "-" + i);
        Log.d("calendarView ", i2 + "-" + i1 + "-" + i);
        //Log.d("calendarView ", getSelectedDate(calendarView.getDate()));
        mViewModel.getAvailableSlots(date);
    }

    private String getSelectedDate(long date) {
        String dateString = DateFormat.format("dd-MM-yyyy", new Date(date)).toString();
        return dateString;
    }
}