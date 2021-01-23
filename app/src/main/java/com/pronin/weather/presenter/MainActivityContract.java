package com.pronin.weather.presenter;

import com.pronin.weather.model.CurrentWeather;

public interface MainActivityContract {
    interface View {
        void showWeather(CurrentWeather weather);
    }

    interface Presenter {
        void loadWeather(String apikey);
        void onDestroy();
    }
}
