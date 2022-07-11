package com.weather.test_weather_api.connector.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeteoBlueResponseDto {

    @JsonProperty("data_current")
    private Main dataCurrent;

    public Main getDataCurrent() {
        return dataCurrent;
    }

    public static class Main {

        private Double temperature;

        public Double getTemperature() {
            return temperature;
        }
    }

}
