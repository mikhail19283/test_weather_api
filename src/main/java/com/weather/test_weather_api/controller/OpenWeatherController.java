package com.weather.test_weather_api.controller;

import com.weather.test_weather_api.dto.CityDto;
import com.weather.test_weather_api.dto.WeatherDto;
import com.weather.test_weather_api.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpenWeatherController {

    private final WeatherService weatherService;

    public OpenWeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping(value = "/weather")
    public List<WeatherDto> getTempByCity(@RequestParam String city){
        return weatherService.getMethod(city);
    }

    @PostMapping(value = "/city")
    public ResponseEntity<Void> addCity(@RequestBody CityDto city){
        weatherService.searchCoordinate(city.getCity());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
