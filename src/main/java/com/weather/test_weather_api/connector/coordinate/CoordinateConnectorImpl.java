package com.weather.test_weather_api.connector.coordinate;

import com.weather.test_weather_api.connector.coordinate.dto.CityOpenWeatherResponseDto;
import com.weather.test_weather_api.entity.CityCoordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CoordinateConnectorImpl implements CoordinateConnector {

    private final RestTemplate restTemplate;

    @Value("${config.urlOpenWeather}")
    private String url;

    @Value("${config.tokenOWA}")
    private String token;

    public CoordinateConnectorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CityCoordinate request(String city) {
        String currentUrl = url + "?q=" + city + "&appid=" + token;
        CityOpenWeatherResponseDto cityOpenWeather = restTemplate.getForObject(currentUrl, CityOpenWeatherResponseDto.class);
        return new CityCoordinate(city, cityOpenWeather.getCoord().getLat(), cityOpenWeather.getCoord().getLon());
    }
}
