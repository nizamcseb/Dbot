package com.dbot.client.main.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbot.client.R;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    ExpandableLayout expandableLayout;

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
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = root.findViewById(R.id.vp_home_banner);
        bannerViewPagerAdapter = new BannerViewPagerAdapter(getContext(), images);
        viewPager.setAdapter(bannerViewPagerAdapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}