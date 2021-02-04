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
import com.pronin.weather.model.Hourly;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherHolder> {
    private final List<Hourly> hourlyList = new ArrayList<>();
    private View view;
    @NonNull
    @Override
    public HourlyWeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hourly, parent, false);
        return new HourlyWeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherHolder holder, int position) {
        holder.bind(hourlyList.get(position));
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }

    public void setItems (List<Hourly> hourlyListArg) {
        hourlyList.addAll(hourlyListArg);
        notifyDataSetChanged();
    }

    public void clearItems() {
        hourlyList.clear();
        notifyDataSetChanged();
    }

    class HourlyWeatherHolder extends RecyclerView.ViewHolder {
        TextView time;
        ImageView icon;
        TextView temp;
        public HourlyWeatherHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.hourly_time);
            icon = itemView.findViewById(R.id.hourly_icon);
            temp = itemView.findViewById(R.id.hourly_temp);
        }

        public void bind(Hourly hourly) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault(Locale.Category.FORMAT));
            String curDate = simpleDateFormat.format(new Date(hourly.getDt()));
            if (hourlyList.indexOf(hourly) == 0) {
                time.setText("сейчас");
            } else {
                time.setText(curDate);
            }
            temp.setText(String.format(Locale.getDefault(),"%.0f\u00B0", hourly.getTemp()));
            Glide.with(view)
                    .load(hourly.getWeather().get(0).getIcon())
                    .error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(icon);
            icon.setVisibility(hourly.getWeather().get(0).getIcon() != null ? View.VISIBLE : View.GONE);
        }
    }
}
