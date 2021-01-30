package com.pronin.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pronin.weather.model.Current;
import com.pronin.weather.model.Hourly;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CurrentWeatherFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    HourlyWeatherAdapter hourlyListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_weather, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        view = getView();
        initHourlyList();
    }
    private void initHourlyList() {
        recyclerView = view.findViewById(R.id.cur_hourly);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        if (hourlyListAdapter == null) {
            hourlyListAdapter = new HourlyWeatherAdapter();
        }
        recyclerView.setAdapter(hourlyListAdapter);
    }

    public void setCurrentWeather(Current current, String argPlace, List<Hourly> hourly) {
        TextView place = view.findViewById(R.id.place);
        TextView date = view.findViewById(R.id.date);
        TextView sunDay = view.findViewById(R.id.sun_day);
        TextView temp = view.findViewById(R.id.cur_temp);
        TextView description = view.findViewById(R.id.cur_description);
        TextView wind = view.findViewById(R.id.cur_wind);
        TextView pressure = view.findViewById(R.id.cur_pressure);
        TextView humidity = view.findViewById(R.id.cur_humidity);
        ImageView icon = view.findViewById(R.id.cur_icon);
        place.setText(argPlace);
        String curDate = DateFormat.getDateInstance().format(new Date(current.getDt()));
        date.setText(curDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault(Locale.Category.FORMAT));
        String sun = "\u25B2" + simpleDateFormat.format(new Date(current.getSunrise())) +
                " \u25BC" + simpleDateFormat.format(new Date(current.getSunset()));
        sunDay.setText(sun);
        temp.setText(String.format(Locale.getDefault(),"%.0f\u00B0", current.getTemp()));
        description.setText(current.getWeather().get(0).getDescription());
        wind.setText(String.format(Locale.getDefault(),"%.0f м/с, ",current.getWindSpeed()) + setWindDeg(current.getWindDeg()));
        pressure.setText(String.format(Locale.getDefault(), "%d мм рт.ст.", current.getPressure()));
        humidity.setText(String.format(Locale.getDefault(), "%d ", current.getHumidity()) + "%");
        Glide.with(this)
                .load(current.getWeather().get(0).getIcon())
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(icon);
        hourlyListAdapter.setItems(hourly);
    }

    private String setWindDeg(long windDeg) {
        String result = "no data";
        if ((windDeg > 350 && windDeg <= 360) || (windDeg >= 0 && windDeg <= 10)) {
            result = "С\u21D3";
        }
        if (windDeg > 10 && windDeg <= 80) {
            result = "СВ\u21D9";
        }
        if (windDeg > 80 && windDeg <= 100) {
            result = "В\u21D0";
        }
        if (windDeg > 100 && windDeg <= 170) {
            result = "ЮВ\u21D6";
        }
        if (windDeg > 170 && windDeg <= 190) {
            result = "Ю\u21D1";
        }
        if (windDeg > 190 && windDeg <= 260) {
            result = "ЮЗ\u21D7";
        }
        if (windDeg > 260 && windDeg <= 280) {
            result = "З\u21D2";
        }
        if (windDeg > 280 && windDeg <= 350) {
            result = "СЗ\u21D8";
        }
        return result;
    }
}