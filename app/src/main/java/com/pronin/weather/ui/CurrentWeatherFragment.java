package com.pronin.weather.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pronin.weather.R;
import com.pronin.weather.model.Current;
import com.pronin.weather.model.Hourly;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    @SuppressLint("SetTextI18n")
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
        wind.setText(String.format(Locale.getDefault(),"%.0f м/с, ",current.getWindSpeed()) + current.getWindDeg());
        pressure.setText(String.format(Locale.getDefault(), "%d мм рт.ст.", current.getPressure()));
        humidity.setText(String.format(Locale.getDefault(), "%d ", current.getHumidity()) + "%");
        Glide.with(this)
                .load(current.getWeather().get(0).getIcon())
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(icon);
        hourlyListAdapter.setItems(hourly);
    }
}