package com.pronin.weather.network;

import com.pronin.weather.model.CurrentWeather;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CurrentWeatherApi {

    /*Options description.
    Option: appid - required!
    Your unique API key (you can always find it on your account page under the "API key" tab)

    Options: lat, lon - required!
    Geographical coordinates (latitude, longitude)

    Option: exclude - optional.
    By using this parameter you can exclude some parts of the weather data from the API response.
    It should be a comma-delimited list (without spaces).
    Available values: current, minutely, hourly, daily, alerts

    Option: units - optional.
    Units of measurement. standard, metric and imperial units are available.
    If you do not use the units parameter, standard units will be applied by default.

    Option: lang - optional.
    You can use the lang parameter to get the output in your language.
    */

    @GET("onecall?")
    Call<CurrentWeather> getWeather(@QueryMap Map<String, String> options);
}
