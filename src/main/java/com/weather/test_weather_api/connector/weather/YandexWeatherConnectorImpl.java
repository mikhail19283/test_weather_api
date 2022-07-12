package com.weather.test_weather_api.connector.weather;

import com.weather.test_weather_api.connector.weather.dto.YandexWeatherResponseDto;
import com.weather.test_weather_api.entity.CityCoordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class YandexWeatherConnectorImpl implements WeatherConnector {

    @Value("${config.urlYandexWeather}")
    private String url;

    @Value("${config.tokenYandexWeather}")
    private String token;

    private final RestTemplate restTemplate;

    public YandexWeatherConnectorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Double request(CityCoordinate cityCoordinate) {
        String fullUrl = url + "?lat=" + cityCoordinate.getLat() + "&lon=" + cityCoordinate.getLon();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Yandex-API-Key", token);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<YandexWeatherResponseDto> yandexWeather = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, YandexWeatherResponseDto.class);

        return yandexWeather.getBody().getFact().getTemp();
    }
}
