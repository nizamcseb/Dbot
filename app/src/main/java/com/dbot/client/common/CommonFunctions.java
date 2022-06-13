package com.dbot.client.common;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dbot.client.R;
import com.dbot.client.login.city.CityData;
import com.dbot.client.main.MainActivity;
import com.dbot.client.main.newrequest.model.BookSlot;
import com.dbot.client.main.newrequest.model.BookSlotResponse;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonFunctions {
    public static int findCityPosition(List<CityData> cityData, String city) {
        for (int i = 0; i < cityData.size(); i++) {
            if (cityData.get(i).getId().equals(city) && cityData.get(i).getWorkingCity().equals("1"))
                return i;
            else if (cityData.get(i).getWorkingCity().equals("1"))
                return i;
        }
        return 0;
    }

    public static void shakeAnimation(View view, int duration) {
        ObjectAnimator
                .ofFloat(view, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0)
                .setDuration(duration)
                .start();
    }

    public static void postPaymentPurchaseDetails(BookSlot bookSlot, Context context, Activity activity, String title, String message, String status, String transaction_id) {

        //activity.startActivityForResult(intent,111);
        if (NetworkUtil.getConnectivityStatus(context) != 0) {
            Log.d("bookSlotInputdata", new GsonBuilder().setPrettyPrinting().create().toJson(bookSlot));
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<BookSlotResponse> call = apiInterface.bookSlot(bookSlot);
            call.enqueue(new Callback<BookSlotResponse>() {
                @Override
                public void onResponse(Call<BookSlotResponse> call, Response<BookSlotResponse> response) {
                    if (response.body().getStatus().getCode().equals(1031)) {
                        Log.d("BookSlotResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                        paymentAlert(context, activity, title, message + "\n Transation Id - " + transaction_id);
                    } else {
                        Snackbar.make(activity.getCurrentFocus(), response.body().getStatus().getCode(), Snackbar.LENGTH_SHORT).show();
                    }
                   /* activity.finish();
                    Intent intent = new Intent(context,MainActivity.class);
                    intent.putExtra(Tags.TAG_PAYMENT_TXN_ID,transaction_id);
                    activity.startActivityForResult(intent,111);*/
                }

                @Override
                public void onFailure(Call<BookSlotResponse> call, Throwable t) {
                    call.cancel();
                    t.printStackTrace();
                    Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
                }
            });
        } else
            netWorkErrorAlert(activity);
    }

    public static void paymentAlert(Context context, Activity activity, String titile, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //Setting message manually and performing action on button click
        builder.setTitle(titile);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //PayUActivity.startHomeActivity();
                        activity.finish();
                        //activity.startActivityForResult(new Intent(context,HomeActivity.class),2);

                        activity.startActivityForResult(new Intent(context, MainActivity.class), 111);
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void netWorkErrorAlert(Activity activity) {
        Snackbar.make(activity.getCurrentFocus().getRootView(), activity.getString(R.string.msg_network_failed), Snackbar.LENGTH_SHORT).show();
    }

    public static boolean checkEmptyValidatation(EditText editText) {
        if (editText.getText().toString().equals("") || editText.getText().toString().equals(null))
            return false;
        return true;
    }
}
