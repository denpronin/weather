package com.pronin.weather.presenter;

import android.util.Log;

import com.pronin.weather.model.CurrentWeather;
import com.pronin.weather.model.Location;
import com.pronin.weather.network.NetworkWorker;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityPresenter implements MainActivityContract.Presenter{
    private final String TAG = "MainActivityPresenter";
    private MainActivityContract.View view;
    private Disposable disposableObserver;

    @Override
    public void loadWeather(String apikey, Location location) {
        Observable<CurrentWeather> cw = NetworkWorker.getCurrentWeather(apikey, location);
        disposableObserver = cw.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CurrentWeather>() {
                    @Override
                    public void onNext(@NonNull CurrentWeather weather) {
                        view.showWeather(weather);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG,"Error on update weather");
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"Completed");
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

    @Override
    public void onDestroy() {
        disposableObserver.dispose();
    }
}
