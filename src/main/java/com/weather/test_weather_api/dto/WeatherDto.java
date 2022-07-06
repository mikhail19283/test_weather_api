package com.weather.test_weather_api.dto;

import java.sql.Timestamp;

public class WeatherDto {

    private String city;
    private Double temperature;
    private Timestamp date;

    public WeatherDto(String city, Double temperature, Timestamp date) {
        this.city = city;
        this.temperature = temperature;
        this.date = date;
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
