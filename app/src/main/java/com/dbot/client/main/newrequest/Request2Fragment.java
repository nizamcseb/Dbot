package com.dbot.client.main.newrequest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.main.MainActivity;

public class Request2Fragment extends Fragment implements View.OnClickListener {

    private Request2ViewModel mViewModel;
    Button btn_req2_prev, btn_req2_next;
    EditText et_project_name, et_contact_person_name, et_contact_person_phone_number;
    SeekBar sb_size_of_property;
    TextView tv_size_of_property;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    View root;

    public Request2Fragment() {
    }

    public static Request2Fragment newInstance() {
        return new Request2Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_request2, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_project_name = root.findViewById(R.id.et_project_name);
        et_contact_person_name = root.findViewById(R.id.et_contact_person_name);
        et_contact_person_phone_number = root.findViewById(R.id.et_contact_person_phone_number);
        sb_size_of_property = root.findViewById(R.id.sb_size_of_property);
        tv_size_of_property = root.findViewById(R.id.tv_size_of_property);
        setData();
        if (sb_size_of_property != null) {
            sb_size_of_property.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if(seekBar.getProgress() == 0)
                        tv_size_of_property.setText("1BHK");
                    if(seekBar.getProgress() == 1)
                        tv_size_of_property.setText("2BHK");
                    if(seekBar.getProgress() == 2)
                        tv_size_of_property.setText("3BHK");
                    if(seekBar.getProgress() == 3)
                        tv_size_of_property.setText("4BHK");
                    if(seekBar.getProgress() == 4)
                        tv_size_of_property.setText("5BHK+");

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        btn_req2_prev = root.findViewById(R.id.btn_req2_prev);
        btn_req2_next = root.findViewById(R.id.btn_req2_next);
        btn_req2_prev.setOnClickListener(this::onClick);
        btn_req2_next.setOnClickListener(this::onClick);
    }

    private void setData() {
        if (MainActivity.project_name != null)
            et_project_name.setText(MainActivity.project_name);
        if (MainActivity.contact_person_name != null)
            et_contact_person_name.setText(MainActivity.contact_person_name);
        if (MainActivity.contact_person_phone != null)
            et_contact_person_phone_number.setText(MainActivity.contact_person_phone);
        if(MainActivity.property_size != 9)
            sb_size_of_property.setProgress(MainActivity.property_size);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Request2ViewModel.class);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        // TODO: Use the ViewModel
    }

    private boolean checkManditoryFields() {
        if (et_project_name.getText().toString().equals("")) {
            et_project_name.setError("Required");
            return false;
        }
        if (et_contact_person_name.getText().toString().equals("")) {
            et_contact_person_name.setError("Required");
            return false;
        }
        if (et_contact_person_phone_number.getText().toString().equals("")) {
            et_contact_person_phone_number.setError("Required");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_req2_prev:
                Request1Fragment request1Fragment = new Request1Fragment();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, request1Fragment);
                fragmentTransaction.commit();
                break;
            case R.id.btn_req2_next:
                if (checkManditoryFields()) {
                    MainActivity.project_name = et_project_name.getText().toString();
                    MainActivity.contact_person_name = et_contact_person_name.getText().toString();
                    MainActivity.contact_person_phone = et_contact_person_phone_number.getText().toString();
                    MainActivity.property_size = sb_size_of_property.getProgress();
                    Log.d("property_size",String.valueOf(MainActivity.property_size));
                    Request3Fragment request3Fragment = new Request3Fragment();
                    fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, request3Fragment);
                    fragmentTransaction.commit();
                }
                break;

        }
    }
}
