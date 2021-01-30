package com.pronin.weather.presenter;

import com.pronin.weather.model.CurrentWeather;
import com.pronin.weather.model.Location;

public interface MainActivityContract {
    interface View {
        void showWeather(CurrentWeather weather);
        void showError();
    }

    interface Presenter {
        void loadWeather(String apikey, Location location);
        void attachView(MainActivityContract.View view);
        void detachView();
    }
}
