package com.pronin.weather.presenter;

import android.util.Log;

import com.pronin.weather.model.CurrentWeather;
import com.pronin.weather.model.Location;
import com.pronin.weather.network.NetworkWorker;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityPresenter implements MainActivityContract.Presenter{
    private final String TAG = "MainActivityPresenter";
    private MainActivityContract.View view;

//    public MainActivityPresenter(MainActivityContract.View view) {
//        this.view = view;
//    }

    @Override
    public void loadWeather(String apikey, Location location) {
//        if (view == null)
//            Log.e(TAG,"View is null!");
//        else {
//            Observable<CurrentWeather> cw = NetworkWorker.getCurrentWeather(apikey, location);
//            cw.subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(currentWeather -> {
//                        view.showWeather(currentWeather);
//                    }, throwable -> {
//                        view.showError();
//                        Log.e(TAG,"Rx error!");});
//        }
        NetworkWorker.getCurrentWeather(apikey, location, new NetworkWorker.GetCurrentWeatherListener() {
            @Override
            public void onGotCurrentWeather(CurrentWeather weather) {
                if (view == null)
                    Log.e(TAG,"View is null!");
                else
                    view.showWeather(weather);
            }

            @Override
            public void onError() {
                view.showError();
            }
        });
    }

    @Override
    public void attachView(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
