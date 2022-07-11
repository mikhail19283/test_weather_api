package com.weather.test_weather_api.connector.coordinate.dto;

public class CityOpenWeatherResponseDto {

    private Coord coord;

    public Coord getCoord() {
        return coord;
    }

    public static class Coord {

        private Double lon;
        private Double lat;

        public Double getLon() {
            return lon;
        }

        public Double getLat() {
            return lat;
        }
    }

}
