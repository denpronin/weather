package com.pronin.weather.model;

public class Weather {
    private String description;
    private String icon;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return "https://openweathermap.org/img/wn/" + icon + "@4x.png";
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
