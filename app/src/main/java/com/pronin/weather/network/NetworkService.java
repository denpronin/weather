package com.pronin.weather.network;


import com.pronin.weather.model.CurrentWeather;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService instance;
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private final Retrofit retrofit;

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    private NetworkService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public CurrentWeatherApi getApi() {
        return retrofit.create(CurrentWeatherApi.class);
    }

    public static void getCurrentWeather(String apikey, GetCurrentWeatherListener callback) {
        Thread thread = new Thread(() -> {
            Map<String, String> options = new HashMap<>();
            options.put("appid", apikey);
            options.put("lat", "55");
            options.put("lon", "26");
            options.put("units", "metric");
            options.put("exclude", "minutely,alerts");
            options.put("lang", "ru");
            Call<CurrentWeather> currentWeather = getInstance().getApi()
                    .getWeather(options);
            try {
                Response<CurrentWeather> currentWeatherResponse = currentWeather.execute();
                callback.onGotCurrentWeather(currentWeatherResponse.body());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        thread.start();
    }

    public interface GetCurrentWeatherListener {
        void onGotCurrentWeather(CurrentWeather weather);
    }
}
