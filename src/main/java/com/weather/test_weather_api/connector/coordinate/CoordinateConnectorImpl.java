package com.weather.test_weather_api.connector.coordinate;

import com.weather.test_weather_api.connector.coordinate.dto.CityOpenWeatherResponseDto;
import com.weather.test_weather_api.entity.CityCoordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

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
          if (cityOpenWeather == null) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
          }
          return new CityCoordinate(city.toUpperCase(), cityOpenWeather.getCoord().getLat(), cityOpenWeather.getCoord().getLon());
     }
}
