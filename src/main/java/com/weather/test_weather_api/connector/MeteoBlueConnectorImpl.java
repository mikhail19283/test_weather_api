package com.weather.test_weather_api.connector;

import com.weather.test_weather_api.dto.MeteoBlueResponseDto;
import com.weather.test_weather_api.entity.CityCoordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class MeteoBlueConnectorImpl implements WeatherConnector {

    @Value("${config.urlMeteoBlue}")
    private String url;

    @Value("${config.tokenMeteo}")
    private String token;

    private final RestTemplate restTemplate;

    public MeteoBlueConnectorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Double request(CityCoordinate cityCoordinate) {
        String fullUrl = url + token + "&lat=" + cityCoordinate.getLat() + "&lon=" + cityCoordinate.getLon() + "&asl=219&format=json";
        MeteoBlueResponseDto meteoBlue = restTemplate.getForObject(fullUrl, MeteoBlueResponseDto.class);
        if (meteoBlue == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return meteoBlue.getDataCurrent().getTemperature();
    }
}
