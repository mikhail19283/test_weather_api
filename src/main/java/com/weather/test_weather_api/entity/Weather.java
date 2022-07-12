package com.weather.test_weather_api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String city;
    private Double temperature;
    private LocalDateTime date;

    public Weather() {
    }

    public Weather(String city, Double temperature, LocalDateTime date) {
        this.city = city;
        this.temperature = temperature;
        this.date = date;
    }

    public Integer getId() {
        return id;
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
