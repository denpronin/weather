package com.pronin.weather.presenter;

import com.pronin.weather.model.CurrentWeather;
import com.pronin.weather.network.NetworkService;

public class MainActivityPresenter implements MainActivityContract.Presenter{

    private MainActivityContract.View view;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void loadWeather(String apikey) {
        NetworkService.getCurrentWeather(apikey, new NetworkService.GetCurrentWeatherListener() {
            @Override
            public void onGotCurrentWeather(CurrentWeather weather) {
                view.showWeather(weather);
            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
