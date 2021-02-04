package com.pronin.weather.network;


import com.pronin.weather.model.CurrentWeather;
import com.pronin.weather.model.Location;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkWorker {
    private static NetworkWorker instance;
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private final Retrofit retrofit;

    public static NetworkWorker getInstance() {
        if (instance == null) {
            instance = new NetworkWorker();
        }
        return instance;
    }

    private NetworkWorker() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    public CurrentWeatherApi getApi() {
        return retrofit.create(CurrentWeatherApi.class);
    }

    public static void getCurrentWeather(String apikey, Location location, GetCurrentWeatherListener callback) {
        Thread thread = new Thread(() -> {
            if (!isOnline()) {
                callback.onError();
            } else {
                Map<String, String> options = new HashMap<>();
                options.put("appid", apikey);
                options.put("lat", Double.toString(location.getLatitude()));
                options.put("lon", Double.toString(location.getLongitude()));
                options.put("units", "metric");
                options.put("exclude", "minutely,alerts");
                options.put("lang", "ru");
                //CurrentWeatherApi api = getInstance().getApi();
                //return api.getWeather(options);


                Call<CurrentWeather> currentWeather = getInstance().getApi()
                        .getWeather(options);
                try {
                    Response<CurrentWeather> currentWeatherResponse = currentWeather.execute();
                    callback.onGotCurrentWeather(currentWeatherResponse.body());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    callback.onError();
                }
            }
        });
        thread.start();
    }

    private static boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) { return false; }
    }

    public interface GetCurrentWeatherListener {
        void onGotCurrentWeather(CurrentWeather weather);
        void onError();
    }

}
