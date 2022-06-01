package com.dbot.client.common;

import android.animation.ObjectAnimator;
import android.view.View;

import com.dbot.client.login.model.CityData;

import java.util.List;

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
}
