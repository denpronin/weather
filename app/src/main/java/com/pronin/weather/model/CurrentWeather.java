package com.pronin.weather.model;

import java.util.List;

public class CurrentWeather {
    public static final double PRESSURE_COEFFICIENT = 0.75006375541921;
    public static final int DT_MULTIPLIER = 1000;
    private String timezone;
    private Current current;
    private List<Hourly> hourly = null;
    private List<Daily> daily = null;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

}
