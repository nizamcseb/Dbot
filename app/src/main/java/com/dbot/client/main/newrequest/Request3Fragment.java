package com.dbot.client.main.newrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;

public class Request3Fragment extends Fragment implements View.OnClickListener {

    private Request3ViewModel mViewModel;
    View root;
    Button btn_req3_prev,btn_req3_pay;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public static Request3Fragment newInstance() {
        return new Request3Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_request3, container, false);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_req3_prev = root.findViewById(R.id.btn_req3_prev);
        btn_req3_pay = root.findViewById(R.id.btn_req3_pay);
        btn_req3_prev.setOnClickListener(this::onClick);
        btn_req3_pay.setOnClickListener(this::onClick);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Request3ViewModel.class);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_req3_prev:
                Request2Fragment request2Fragment = new Request2Fragment();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, request2Fragment);
                fragmentTransaction.commit();
                break;
            case R.id.btn_req3_pay:
                RequestCompletedFragment requestCompletedFragment = new RequestCompletedFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, requestCompletedFragment);
                fragmentTransaction.commit();
                break;

        }
    }
}
