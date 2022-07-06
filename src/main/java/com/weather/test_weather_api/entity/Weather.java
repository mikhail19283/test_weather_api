package com.weather.test_weather_api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String city;
    private Double temperature;
    private Timestamp date;

    public Weather() {
    }

    public Weather(String city, Double temperature, Timestamp date) {
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

    public Timestamp getDate() {
        return date;
    }
}
