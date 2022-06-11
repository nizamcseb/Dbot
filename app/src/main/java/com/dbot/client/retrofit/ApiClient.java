package com.dbot.client.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static boolean isTest = true;
    public static String BASE_TEST_URL = "http://apis.dbot.space/";
    public static String BASE_PROD_URL = "http://apis.dbot.space/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            if (isTest) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_TEST_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            } else {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_PROD_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }
}