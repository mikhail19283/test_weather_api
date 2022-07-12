package com.weather.test_weather_api.dto;

import java.time.LocalDateTime;

public class WeatherDto {

    private final String city;
    private final Double temperature;
    private final LocalDateTime date;

    public WeatherDto(String city, Double temperature, LocalDateTime date) {
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

    public LocalDateTime getDate() {
        return date;
    }
}
