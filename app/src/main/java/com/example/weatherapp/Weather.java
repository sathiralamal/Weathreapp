package com.example.weatherapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Weather {
    public String TAG="Weather";
    public String Data;

    //API related data
    private static String baseURL="https://api.weatherapi.com/v1/";
    private static String currentWeatherURL ="current.json";
    private static String forcastWeatherURL="forecast.json";
    //API authentication key
    private static String APIKEY="16c896924b644f89a4a174120200207";
    //input parameter


    public static String getBaseURL() {
        return baseURL;
    }

    public static String getCurrentWeatherURL() {
        return currentWeatherURL;
    }

    public static String getForcastWeatherURL() {
        return forcastWeatherURL;
    }

    public static String getAPIKEY() {
        return APIKEY;
    }
}
