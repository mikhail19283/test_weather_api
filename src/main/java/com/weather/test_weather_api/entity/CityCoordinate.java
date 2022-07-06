package com.weather.test_weather_api.entity;

import javax.persistence.*;

@Entity
public class CityCoordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String city;
    private Double lat;
    private Double lon;

    public CityCoordinate() {
    }

    public CityCoordinate(String city, Double lat, Double lon) {
        this.city = city;
        this.lat = lat;
        this.lon = lon;
    }

    public Integer getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
