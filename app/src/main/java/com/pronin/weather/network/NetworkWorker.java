package com.pronin.weather.network;


import com.pronin.weather.model.CurrentWeather;
import com.pronin.weather.model.Location;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    public CurrentWeatherApi getApi() {
        return retrofit.create(CurrentWeatherApi.class);
    }

    public static Observable<CurrentWeather> getCurrentWeather(String apikey, Location location) {
        Map<String, String> options = new HashMap<>();
        options.put("appid", apikey);
        options.put("lat", Double.toString(location.getLatitude()));
        options.put("lon", Double.toString(location.getLongitude()));
        options.put("units", "metric");
        options.put("exclude", "minutely,alerts");
        options.put("lang", "ru");
        CurrentWeatherApi api = getInstance().getApi();
        return api.getWeather(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
