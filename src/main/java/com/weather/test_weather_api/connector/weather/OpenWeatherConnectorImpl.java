package com.weather.test_weather_api.connector.weather;

import com.weather.test_weather_api.connector.weather.dto.OpenWeatherResponseDto;
import com.weather.test_weather_api.entity.CityCoordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class OpenWeatherConnectorImpl implements WeatherConnector {

    @Value("${config.urlOpenWeather}")
    private String url;

    @Value("${config.tokenOWA}")
    private String token;

    private final RestTemplate restTemplate;

    public OpenWeatherConnectorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Double request(CityCoordinate cityCoordinate) {
        String fullUrl = url + "?lat=" + cityCoordinate.getLat() + "&lon=" + cityCoordinate.getLon() + "&appid=" + token + "&units=metric";
        OpenWeatherResponseDto openWeather = restTemplate.getForObject(fullUrl, OpenWeatherResponseDto.class);
        if (openWeather == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return openWeather.getMain().getTemp();
    }
}
