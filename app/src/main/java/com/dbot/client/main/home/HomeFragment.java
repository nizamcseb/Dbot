package com.dbot.client.main.home;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

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

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, CalendarView.OnDateChangeListener {
    SessionManager sessionManager;
    private HomeViewModel mViewModel;
    private LoginViewModel loginViewModel;
    ExpandableLayout expandableLayout;
    List<CityData> cityDataList;
    Spinner spCity;
    CalendarView cView;
    LinearLayout llAvailableSlots;
    Button btn_continue,btn_slot_1, btn_slot_2;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        sessionManager = new SessionManager(getContext());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        spCity = root.findViewById(R.id.sp_city);
        cView = root.findViewById(R.id.calendarView);
        llAvailableSlots = root.findViewById(R.id.ll_available_slots);
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
                int position = findCityPosition(sessionManager.getCity());
                spCity.setSelection(position);
                //spCity.setOnItemSelectedListener(this);
            }
        });
        Button btnContinue = root.findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(this::onClick);
        return root;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createAvailableSlots(List<AvailableSlotsData> availableSlotsData) {


        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (availableSlotsData.size() == 2) {
            btn_slot_1.setVisibility(View.VISIBLE);
            btn_slot_2.setVisibility(View.VISIBLE);
            btn_slot_1.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_not_available_button));
            btn_slot_2.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_not_available_button));
            btn_continue.setEnabled(false);
            btn_continue.setBackgroundColor(getContext().getColor(R.color.greyed_out));
            btn_continue.setText("Continue");
            btn_continue.setTextColor(getContext().getColor(R.color.white));
            btn_slot_1.setText(availableSlotsData.get(0).getSlotTime());
            btn_slot_2.setText(availableSlotsData.get(1).getSlotTime());
            if(availableSlotsData.get(0).getAvailableStatus()) {
                btn_slot_1.setTextColor(getContext().getColor(R.color.black));
                btn_slot_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_continue.setEnabled(true);
                        btn_continue.setBackgroundColor(getContext().getColor(R.color.primary_varient));
                        btn_continue.setText("Continue");
                        btn_continue.setTextColor(getContext().getColor(R.color.white));
                        btn_slot_1.setTextColor(getContext().getColor(R.color.white));
                        btn_slot_1.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_available_button));
                    }
                });
            }else {
                btn_slot_1.setTextColor(getContext().getColor(R.color.greyed_out));
                btn_slot_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_continue.setEnabled(true);
                        btn_continue.setBackgroundColor(getContext().getColor(R.color.purple_profile));
                        btn_continue.setText("Notify Me");
                        btn_continue.setTextColor(getContext().getColor(R.color.white));
                        btn_slot_1.setTextColor(getContext().getColor(R.color.white));
                        btn_slot_1.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_notify_button));
                    }
                });
            }
            if(availableSlotsData.get(1).getAvailableStatus()) {
                btn_slot_2.setTextColor(getContext().getColor(R.color.black));
                btn_slot_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_continue.setEnabled(true);
                        btn_continue.setBackgroundColor(getContext().getColor(R.color.primary_varient));
                        btn_continue.setText("Continue");
                        btn_continue.setTextColor(getContext().getColor(R.color.white));
                        btn_slot_2.setTextColor(getContext().getColor(R.color.white));
                        btn_slot_2.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_available_button));
                    }
                });
            } else {
                btn_slot_2.setTextColor(getContext().getColor(R.color.greyed_out));
                btn_slot_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_continue.setEnabled(true);
                        btn_continue.setBackgroundColor(getContext().getColor(R.color.purple_profile));
                        btn_continue.setText("Notify Me");
                        btn_continue.setTextColor(getContext().getColor(R.color.white));
                        btn_slot_2.setTextColor(getContext().getColor(R.color.white));
                        btn_slot_2.setBackground(getContext().getDrawable(R.drawable.bg_preferred_slot_notify_button));
                    }
                });
            }

        }
       /* //Create four
        for (int j = 0; j <= availableSlotsData.size() - 1; j++) {

            // Create Button
            final Button btn = new Button(getContext());
            // Give button an ID
            btn.setId(j + 1);
            btn.setText(availableSlotsData.get(j).getSlotTime());
            // set the layoutParams on the button
            btn.setLayoutParams(params);

            final int index = j;
            // Set click listener for button
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Log.i("TAG", "index :" + index);

                    Toast.makeText(getContext(),
                            "Clicked Button Index :" + index,
                            Toast.LENGTH_LONG).show();

                }
            });

            //Add button to LinearLayout
            //ll.addView(btn);
            //Add button to LinearLayout defined in XML
            llAvailableSlots.addView(btn);
        }*/
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    private int findCityPosition(String city) {
        for (int i = 0; i < cityDataList.size(); i++) {
            if (cityDataList.get(i).getId().equals(city))
                return i;
        }
        return 0;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                Request1Fragment request1Fragment = new Request1Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, request1Fragment);
                fragmentTransaction.commit();
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