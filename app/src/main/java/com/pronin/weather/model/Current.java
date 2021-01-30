package com.pronin.weather.model;

import java.util.List;

public class Current {

    private long dt;
    private long sunrise;
    private long sunset;
    private double temp;
    private long pressure;
    private long humidity;
    private double windSpeed;
    private long windDeg;
    private List<Weather> weather = null;

    public long getDt() {
        return dt*CurrentWeather.DT_MULTIPLIER;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getSunrise() {
        return sunrise*CurrentWeather.DT_MULTIPLIER;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset*CurrentWeather.DT_MULTIPLIER;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public long getPressure() {
        return (long) (pressure * CurrentWeather.PRESSURE_COEFFICIENT);
    }

    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public long getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(long windDeg) {
        this.windDeg = windDeg;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

}
