package com.weather.test_weather_api.connector.weather.dto;

public class OpenWeatherResponseDto {

    private Main main;

    public Main getMain() {
        return main;
    }

    public static class Main {

        private Double temp;

        public Double getTemp() {
            return temp;
        }

    }

}
