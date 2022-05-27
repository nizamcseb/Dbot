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

public class Request3Fragment extends Fragment {

    private Request3ViewModel mViewModel;

    public static Request3Fragment newInstance() {
        return new Request3Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_request3, container, false);
        Button btn_req3_pay = root.findViewById(R.id.btn_req3_pay);
        btn_req3_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestCompletedFragment requestCompletedFragment = new RequestCompletedFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, requestCompletedFragment);
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Request3ViewModel.class);
        // TODO: Use the ViewModel
    }
}
