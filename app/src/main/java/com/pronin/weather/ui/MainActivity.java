package com.pronin.weather.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import com.pronin.weather.R;
import com.pronin.weather.model.CurrentWeather;
import com.pronin.weather.presenter.MainActivityContract;
import com.pronin.weather.presenter.MainActivityPresenter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    private static final int PERMISSION_REQUEST_CODE = 1526;
    private MainActivityContract.Presenter presenter;
    private CurrentWeatherFragment currentWeatherFragment;
    private static final String PERMISSION_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final double DEFAULT_LATITUDE = 42.100526;
    private final double DEFAULT_LONGITUDE = 19.095654;
    private com.pronin.weather.model.Location location;
    private DailyWeatherAdapter dailyWeatherAdapter;

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
        RecyclerView recyclerView = findViewById(R.id.cur_daily);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dailyWeatherAdapter = new DailyWeatherAdapter();
        recyclerView.setAdapter(dailyWeatherAdapter);
        presenter = new MainActivityPresenter();
        presenter.attachView(this);
        presenter.loadWeather(getResources().getString(R.string.api_key), getLocation());
        currentWeatherFragment = (CurrentWeatherFragment) getSupportFragmentManager().findFragmentById(R.id.cur_weather_frag);
    }

    @Override
    public void showWeather(CurrentWeather weather) {
        currentWeatherFragment.setCurrentWeather(weather.getCurrent(),
                location.getAddress().equals("") ? weather.getTimezone() : location.getAddress(),
                weather.getHourly());
        dailyWeatherAdapter.setItems(weather.getDaily());
    }

    @Override
    public void showError() {
        Toast errToast = Toast.makeText(this, getString(R.string.errWeather), Toast.LENGTH_LONG);
        errToast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter.detachView();
    }

    private com.pronin.weather.model.Location getLocation() {
        if (ContextCompat.checkSelfPermission(this, PERMISSION_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager mng = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location curLocation = mng.getLastKnownLocation(mng.getBestProvider(new Criteria(), false));
            if (curLocation != null) {
                double lat = curLocation.getLatitude();
                double lon = curLocation.getLongitude();
                location = new com.pronin.weather.model.Location(lat, lon, getAddress(this, lat, lon));
            } else {
                Toast.makeText(this, getString(R.string.no_location), Toast.LENGTH_LONG).show();
                location = new com.pronin.weather.model.Location(DEFAULT_LATITUDE, DEFAULT_LONGITUDE, getAddress(this, DEFAULT_LATITUDE, DEFAULT_LONGITUDE));
            }
        }
        return location;
    }

    private String getAddress(Context context, double LATITUDE, double LONGITUDE) {
        String result = "";
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {
                String city = addresses.get(0).getLocality();
                String country = addresses.get(0).getCountryName();
                result = city + ", " + country;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to get name of location. Show the timezone", Toast.LENGTH_LONG).show();
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