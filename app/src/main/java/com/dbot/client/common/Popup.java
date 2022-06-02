package com.dbot.client.common;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dbot.client.R;
import com.dbot.client.main.projects.model.CancelRequestResponse;
import com.dbot.client.main.projects.model.RefundAmount;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Popup {
    public Popup() {

    }


    public void showNotifyRequestPopupWindow(final View view) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_notify_me, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = false;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button btn_back_to_home = popupView.findViewById(R.id.btn_back_to_home);
        btn_back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        //Handler for clicking on the inactive zone of the window

       /* popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                //popupWindow.dismiss();
                return true;
            }
        });*/
    }

    public void showCityNotAvailablePopupWindow(final View view) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_city_not_available, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = false;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button btn_popup_exit = popupView.findViewById(R.id.btn_popup_exit);
        btn_popup_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        Button btn_popup_save = popupView.findViewById(R.id.btn_popup_save);
        btn_popup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public void showCancelRequestConfirmationPopupWindow(String booking_id, RefundAmount refundAmount, final View view) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_cancel_request_confirmation, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = false;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        ImageView iv_popup_crc_close = popupView.findViewById(R.id.iv_popup_crc_close);
        iv_popup_crc_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        LinearLayout ll_popup_crc_end = popupView.findViewById(R.id.ll_popup_crc_end);
        LinearLayout ll_popup_crc = popupView.findViewById(R.id.ll_popup_crc);
        TextView tv_popup_crc_refund_amount_with_req_id = popupView.findViewById(R.id.tv_popup_crc_refund_amount_with_req_id);
        TextView tv_popup_crc_refund_amount_with_msg = popupView.findViewById(R.id.tv_popup_crc_refund_amount_with_msg);
        tv_popup_crc_refund_amount_with_msg.setText("You will be eligible for a refund of Rs." + refundAmount.getRefundAmount());
        Button btn_popup_crc_exit = popupView.findViewById(R.id.btn_popup_crc_exit);
        btn_popup_crc_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        Button btn_popup_crc_cancel = popupView.findViewById(R.id.btn_popup_crc_cancel);
        btn_popup_crc_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelRequest(popupWindow,tv_popup_crc_refund_amount_with_req_id,ll_popup_crc, ll_popup_crc_end, booking_id);
            }
        });
    }

    public void cancelRequest(PopupWindow popupWindow, TextView tv_popup_crc_refund_amount_with_req_id, LinearLayout ll_popup_crc, LinearLayout ll_popup_crc_end, String booking_id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CancelRequestResponse> call = apiInterface.cancelRequest(booking_id);
        call.enqueue(new Callback<CancelRequestResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<CancelRequestResponse> call, Response<CancelRequestResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("CancelRequestResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    //if (response.body() !=null)
                    ll_popup_crc.setVisibility(View.GONE);
                    ll_popup_crc_end.setVisibility(View.VISIBLE);
                    tv_popup_crc_refund_amount_with_req_id.setText("Your request" +"#"+booking_id+" has been cancelled."+"\n\n"+"As per Cancellation and Refund Policy, a refund of Rs."+response.body().getRefundAmount().getRefundAmount()+" has been initiated."+"\n\n"+"Track your refunds under Refund Status");

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<CancelRequestResponse> call, Throwable t) {
                Log.e("CancelRequestResponse error", t.getMessage());

            }
        });
    }


}
