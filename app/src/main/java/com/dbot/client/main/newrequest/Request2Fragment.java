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

public class Request2Fragment extends Fragment {

    private Request2ViewModel mViewModel;

    public static Request2Fragment newInstance() {
        return new Request2Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_request2, container, false);
        Button btn_req2_next = root.findViewById(R.id.btn_req2_next);
        btn_req2_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request3Fragment request3Fragment = new Request3Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, request3Fragment);
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Request2ViewModel.class);
        // TODO: Use the ViewModel
    }
}
