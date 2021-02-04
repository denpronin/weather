package com.pronin.weather.model;

import java.util.List;

public class Daily {
    private long dt;
    private Temp temp;
    private List<Weather> weather = null;

    public long getDt() {
        return dt*CurrentWeather.DT_MULTIPLIER;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }



}
