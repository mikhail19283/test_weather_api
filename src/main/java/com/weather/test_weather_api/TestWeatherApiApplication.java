package com.weather.test_weather_api;

import com.weather.test_weather_api.dto.CityOpenWeatherResponseDto;
import com.weather.test_weather_api.dto.Coordinate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class TestWeatherApiApplication {

    private final static List<String> cities = new ArrayList<>(List.of("ЧЕЛЯБИНСК", "МОСКВА", "САНКТ-ПЕТЕРБУРГ", "КАЗАНЬ","НИЖНИЙ НОВГОРОД", "САМАРА", "ВЛАДИВОСТОК", "ЕКАТЕРИНБУРГ"));
    private final static Map<String, Coordinate> citiesWithCoordinates = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(TestWeatherApiApplication.class, args);
        searchCoordinate(cities);

    }

    private static void searchCoordinate(List<String> cities){
        RestTemplate restTemplate = new RestTemplate();
        for (String city : cities) {
            String currentUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=5b9bcf19850e94e87b31de74b84b79cf";
            CityOpenWeatherResponseDto cityOpenWeather = restTemplate.getForObject(currentUrl, CityOpenWeatherResponseDto.class);
            Coordinate coordinate = new Coordinate(cityOpenWeather.getCoord().getLat(), cityOpenWeather.getCoord().getLon());
            citiesWithCoordinates.put(city, coordinate);
        }
    }

}
