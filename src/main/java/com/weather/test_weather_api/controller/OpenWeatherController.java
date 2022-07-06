package com.weather.test_weather_api.controller;

import com.weather.test_weather_api.service.WeatherService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpenWeatherController {

    private final WeatherService weatherService;

    public OpenWeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping(value = "/weather")
    public String getTempByCity(@RequestParam String city){
        return weatherService.getMethod(city);
    }

}
