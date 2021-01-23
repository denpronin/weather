package com.pronin.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.pronin.weather.model.CurrentWeather;
import com.pronin.weather.presenter.MainActivityContract;
import com.pronin.weather.presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    private MainActivityContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this);
        presenter.loadWeather(getResources().getString(R.string.api_key));
    }

    //Code in this method is only for testing response
    @Override
    public void showWeather(CurrentWeather weather) {
        runOnUiThread(() -> {
            TextView textView = findViewById(R.id.textview);
            String sb = weather.getCurrent().toString();
            textView.setText(sb);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}