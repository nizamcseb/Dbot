package com.dbot.client.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.dbot.client.main.newrequest.Request1Fragment;

import java.util.List;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    SessionManager sessionManager;
    private HomeViewModel mViewModel;
    private LoginViewModel loginViewModel;
    ExpandableLayout expandableLayout;
    List<CityData> cityDataList;
    Spinner spCity;

    ViewPager viewPager;
    // images array
    int[] images = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4};

    BannerViewPagerAdapter bannerViewPagerAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        sessionManager = new SessionManager(getContext());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        spCity = root.findViewById(R.id.sp_city);
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
}