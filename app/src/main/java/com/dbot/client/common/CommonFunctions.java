package com.dbot.client.common;

import com.dbot.client.login.model.CityData;

import java.util.List;

public class CommonFunctions {
    public static int findCityPosition(List<CityData> cityData, String city) {
        for (int i = 0; i < cityData.size(); i++) {
            if (cityData.get(i).getId().equals(city))
                return i;
        }
        return 0;
    }

}
