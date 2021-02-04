package com.pronin.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.pronin.weather.model.CurrentWeather;
import com.pronin.weather.presenter.MainActivityContract;
import com.pronin.weather.presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    private static final int PERMISSION_REQUEST_CODE = 1526;
    private MainActivityContract.Presenter presenter;
    private CurrentWeatherFragment currentWeatherFragment;
    private static final String PERMISSION_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final double DEFAULT_LATITUDE = 42.100526;
    private final double DEFAULT_LONGITUDE = 19.095654;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, PERMISSION_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            initPresenterAndFragment();
        } else ActivityCompat.requestPermissions(this,
                new String[]{ PERMISSION_FINE_LOCATION },
                PERMISSION_REQUEST_CODE);
    }

    private void initPresenterAndFragment(){
        presenter = new MainActivityPresenter();
        presenter.attachView(this);
        presenter.loadWeather(getResources().getString(R.string.api_key), getLocation());
        currentWeatherFragment = (CurrentWeatherFragment) getSupportFragmentManager().findFragmentById(R.id.cur_weather_frag);
    }
    //Code in this method is only for testing response
    @Override
    public void showWeather(CurrentWeather weather) {
        runOnUiThread(() -> {
            currentWeatherFragment.setCurrentWeather(weather.getCurrent(), weather.getTimezone(), weather.getHourly());
        });
    }

    @Override
    public void showError() {
        runOnUiThread(() -> {
            Toast errToast = Toast.makeText(this, getString(R.string.errWeather), Toast.LENGTH_LONG);
            errToast.show();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private com.pronin.weather.model.Location getLocation() {
        com.pronin.weather.model.Location result = null;
        if (ContextCompat.checkSelfPermission(this, PERMISSION_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager mng = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = mng.getLastKnownLocation(mng.getBestProvider(new Criteria(), false));
            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                result = new com.pronin.weather.model.Location(lat, lon);
            } else {
                Toast.makeText(this, getString(R.string.no_location), Toast.LENGTH_LONG).show();
                result = new com.pronin.weather.model.Location(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
            }
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initPresenterAndFragment();
                } else {
                    Toast.makeText(this, getString(R.string.no_permissions_msg), Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(this,
                            new String[]{  PERMISSION_FINE_LOCATION },
                            PERMISSION_REQUEST_CODE);
                }
            }
        }
    }
}