package com.dbot.client.main.newrequest;

import static com.dbot.client.main.MainActivity.mapRoomType;
import static com.dbot.client.main.MainActivity.totalrooms;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;

public class Request2aFragment extends Fragment implements View.OnClickListener {

    private Request2aViewModel mViewModel;
    View root;
    ImageButton reduceIB1, reduceIB2, reduceIB3, reduceIB4, reduceIB5, reduceIB6, reduceIB7,
            increaseIB1, increaseIB2, increaseIB3, increaseIB4, increaseIB5, increaseIB6, increaseIB7;
    TextView tvRoomType1, tvRoomType2, tvRoomType3, tvRoomType4, tvRoomType5, tvRoomType6, tvRoomType7, tvRoomTypeTotal;
    Button btn_req2a_prev, btn_req2a_next;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int roomtype1 = 1, roomtype2 = 1, roomtype3 = 1, roomtype4 = 1, roomtype5 = 1, roomtype6 = 1, roomtype7 = 1;

    public Request2aFragment() {
    }

    public static Request2aFragment newInstance() {
        return new Request2aFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_request2a, container, false);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvRoomType1 = root.findViewById(R.id.tvRoomType1);
        tvRoomType2 = root.findViewById(R.id.tvRoomType2);
        tvRoomType3 = root.findViewById(R.id.tvRoomType3);
        tvRoomType4 = root.findViewById(R.id.tvRoomType4);
        tvRoomType5 = root.findViewById(R.id.tvRoomType5);
        tvRoomType6 = root.findViewById(R.id.tvRoomType6);
        tvRoomType7 = root.findViewById(R.id.tvRoomType7);

        reduceIB1 = root.findViewById(R.id.reduceIB1);
        reduceIB2 = root.findViewById(R.id.reduceIB2);
        reduceIB3 = root.findViewById(R.id.reduceIB3);
        reduceIB4 = root.findViewById(R.id.reduceIB4);
        reduceIB5 = root.findViewById(R.id.reduceIB5);
        reduceIB6 = root.findViewById(R.id.reduceIB6);
        reduceIB7 = root.findViewById(R.id.reduceIB7);

        increaseIB1 = root.findViewById(R.id.increaseIB1);
        increaseIB2 = root.findViewById(R.id.increaseIB2);
        increaseIB3 = root.findViewById(R.id.increaseIB3);
        increaseIB4 = root.findViewById(R.id.increaseIB4);
        increaseIB5 = root.findViewById(R.id.increaseIB5);
        increaseIB6 = root.findViewById(R.id.increaseIB6);
        increaseIB7 = root.findViewById(R.id.increaseIB7);

        tvRoomTypeTotal = root.findViewById(R.id.tvRoomTypeTotal);

        btn_req2a_prev = root.findViewById(R.id.btn_req2a_prev);
        btn_req2a_next = root.findViewById(R.id.btn_req2a_next);

        reduceIB1.setOnClickListener(this);
        reduceIB2.setOnClickListener(this);
        reduceIB3.setOnClickListener(this);
        reduceIB4.setOnClickListener(this);
        reduceIB5.setOnClickListener(this);
        reduceIB6.setOnClickListener(this);
        reduceIB7.setOnClickListener(this);

        increaseIB1.setOnClickListener(this);
        increaseIB2.setOnClickListener(this);
        increaseIB3.setOnClickListener(this);
        increaseIB4.setOnClickListener(this);
        increaseIB5.setOnClickListener(this);
        increaseIB6.setOnClickListener(this);
        increaseIB7.setOnClickListener(this);

        btn_req2a_prev.setOnClickListener(this);
        btn_req2a_next.setOnClickListener(this);
        setData();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Request2aViewModel.class);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        // TODO: Use the ViewModel

    }

    private void setData() {

        tvRoomType1.setText(String.valueOf(mapRoomType.get("1")));
        tvRoomType2.setText(String.valueOf(mapRoomType.get("2")));
        tvRoomType3.setText(String.valueOf(mapRoomType.get("3")));
        tvRoomType4.setText(String.valueOf(mapRoomType.get("4")));
        tvRoomType5.setText(String.valueOf(mapRoomType.get("5")));
        tvRoomType6.setText(String.valueOf(mapRoomType.get("6")));
        tvRoomType7.setText(String.valueOf(mapRoomType.get("7")));

        setTotal();
    }

    private void updateRoomTypeData(String Key, int value) {

        mapRoomType.put(Key, value);

        tvRoomType1.setText(String.valueOf(mapRoomType.get("1")));
        tvRoomType2.setText(String.valueOf(mapRoomType.get("2")));
        tvRoomType3.setText(String.valueOf(mapRoomType.get("3")));
        tvRoomType4.setText(String.valueOf(mapRoomType.get("4")));
        tvRoomType5.setText(String.valueOf(mapRoomType.get("5")));
        tvRoomType6.setText(String.valueOf(mapRoomType.get("6")));
        tvRoomType7.setText(String.valueOf(mapRoomType.get("7")));
    }

    private void setTotal() {
        tvRoomTypeTotal.setText(String.valueOf(totalrooms));
    }

    private void setTotal(boolean isAddValue) {
        if (isAddValue)
            totalrooms++;
        else totalrooms--;
        tvRoomTypeTotal.setText(String.valueOf(totalrooms));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reduceIB1:
                if (roomtype1 > 1) {
                    roomtype1--;
                    updateRoomTypeData("1", roomtype1);
                    setTotal(false);
                }
                break;
            case R.id.reduceIB2:
                if (roomtype2 > 1) {
                    roomtype2--;
                    updateRoomTypeData("2", roomtype2);
                    setTotal(false);
                }
                break;
            case R.id.reduceIB3:
                if (roomtype3 > 1) {
                    roomtype3--;
                    updateRoomTypeData("3", roomtype3);
                    setTotal(false);
                }
                break;
            case R.id.reduceIB4:
                if (roomtype4 > 1) {
                    roomtype4--;
                    updateRoomTypeData("4", roomtype4);
                    setTotal(false);
                }
                break;
            case R.id.reduceIB5:
                if (roomtype5 > 1) {
                    roomtype5--;
                    updateRoomTypeData("5", roomtype5);
                    setTotal(false);
                }
                break;
            case R.id.reduceIB6:
                if (roomtype6 > 1) {
                    roomtype6--;
                    updateRoomTypeData("6", roomtype6);
                    setTotal(false);
                }
                break;
            case R.id.reduceIB7:
                if (roomtype7 > 1) {
                    roomtype7--;
                    updateRoomTypeData("7", roomtype7);
                    setTotal(false);
                }
                break;
            case R.id.increaseIB1:
                roomtype1++;
                updateRoomTypeData("1", roomtype1);
                setTotal(true);
                break;
            case R.id.increaseIB2:
                roomtype2++;
                updateRoomTypeData("2", roomtype2);
                setTotal(true);
                break;
            case R.id.increaseIB3:
                roomtype3++;
                updateRoomTypeData("3", roomtype3);
                setTotal(true);
                break;
            case R.id.increaseIB4:
                roomtype4++;
                updateRoomTypeData("4", roomtype4);
                setTotal(true);
                break;
            case R.id.increaseIB5:
                roomtype5++;
                updateRoomTypeData("5", roomtype5);
                setTotal(true);
                break;
            case R.id.increaseIB6:
                roomtype6++;
                updateRoomTypeData("6", roomtype6);
                setTotal(true);
                break;
            case R.id.increaseIB7:
                roomtype7++;
                updateRoomTypeData("7", roomtype7);
                setTotal(true);
                break;
            case R.id.btn_req2a_prev:
                Request2Fragment request2Fragment = new Request2Fragment();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, request2Fragment);
                fragmentTransaction.commit();
                break;
            case R.id.btn_req2a_next:
                Request3Fragment request3Fragment = new Request3Fragment();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, request3Fragment);
                fragmentTransaction.commit();
                break;

        }
    }
}
