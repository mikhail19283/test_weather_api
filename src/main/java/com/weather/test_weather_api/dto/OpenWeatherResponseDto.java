package com.weather.test_weather_api.dto;

import java.io.Serializable;

public class OpenWeatherResponseDto implements Serializable {

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
