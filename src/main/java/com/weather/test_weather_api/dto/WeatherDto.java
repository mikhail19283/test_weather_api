package com.weather.test_weather_api.dto;

import com.weather.test_weather_api.entity.Weather;

import java.sql.Timestamp;

public class WeatherDto {

    private final String city;
    private final Double temperature;
    private final Timestamp date;

    public WeatherDto(String city, Double temperature, Timestamp date) {
        this.city = city;
        this.temperature = temperature;
        this.date = date;
    }
    public WeatherDto(Weather weather){
        this.city = weather.getCity();
        this.temperature = weather.getTemperature();
        this.date = weather.getDate();
    }

    public String getCity() {
        return city;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Timestamp getDate() {
        return date;
    }
}
