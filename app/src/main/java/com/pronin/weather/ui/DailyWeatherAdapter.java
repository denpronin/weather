package com.pronin.weather.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pronin.weather.R;
import com.pronin.weather.model.Daily;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherHolder> {
    private final List<Daily> dailyList = new ArrayList<>();
    private View view;
    @NonNull
    @Override
    public DailyWeatherAdapter.DailyWeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent, false);
        return new DailyWeatherAdapter.DailyWeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyWeatherAdapter.DailyWeatherHolder holder, int position) {
        holder.bind(dailyList.get(position));
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }

    public void setItems (List<Daily> dailyListArg) {
        dailyList.addAll(dailyListArg);
        notifyDataSetChanged();
    }

    public void clearItems() {
        dailyList.clear();
        notifyDataSetChanged();
    }

    class DailyWeatherHolder extends RecyclerView.ViewHolder {
        TextView dt;
        TextView day;
        ImageView icon;
        TextView tempMin;
        TextView tempMax;

        public DailyWeatherHolder(@NonNull View itemView) {
            super(itemView);
            dt = itemView.findViewById(R.id.daily_dt);
            day = itemView.findViewById(R.id.daily_day);
            icon = itemView.findViewById(R.id.daily_icon);
            tempMin = itemView.findViewById(R.id.daily_temp_min);
            tempMax = itemView.findViewById(R.id.daily_temp_max);
        }

        public void bind(Daily daily) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMM", Locale.getDefault(Locale.Category.FORMAT));
            String date = simpleDateFormat.format(new Date(daily.getDt()));
            if (dailyList.indexOf(daily) == 0) {
                dt.setText("сегодня");
            } else if (dailyList.indexOf(daily) == 1) {
                dt.setText("завтра");
            } else {
                dt.setText(date);
            }
            simpleDateFormat = new SimpleDateFormat("EEEE", Locale.getDefault(Locale.Category.FORMAT));
            String dayName = simpleDateFormat.format(new Date(daily.getDt()));
            day.setText(dayName);
            tempMin.setText(String.format(Locale.getDefault(),"%.0f\u00B0", daily.getTemp().getMin()));
            tempMax.setText(String.format(Locale.getDefault(),"%.0f\u00B0", daily.getTemp().getMax()));
            Glide.with(view)
                    .load(daily.getWeather().get(0).getIcon())
                    .error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(icon);
            icon.setVisibility(daily.getWeather().get(0).getIcon() != null ? View.VISIBLE : View.GONE);
        }
    }
}
