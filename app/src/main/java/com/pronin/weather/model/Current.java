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

    public String getWindDeg() {
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
