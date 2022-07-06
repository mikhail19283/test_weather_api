package com.weather.test_weather_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MeteoBlueResponseDto implements Serializable {

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
