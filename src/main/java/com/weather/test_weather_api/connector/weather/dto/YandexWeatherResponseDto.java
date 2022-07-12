package com.weather.test_weather_api.connector.weather.dto;

public class YandexWeatherResponseDto {

    private Fact fact;

    public Fact getFact() {
        return fact;
    }

    public static class Fact {
        private Double temp;

        public Double getTemp() {
            return temp;
        }
    }

}
