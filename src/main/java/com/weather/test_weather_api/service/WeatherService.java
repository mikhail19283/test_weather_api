package com.weather.test_weather_api.service;

import com.weather.test_weather_api.connector.coordinate.CoordinateConnectorImpl;
import com.weather.test_weather_api.dto.WeatherDto;
import com.weather.test_weather_api.entity.CityCoordinate;
import com.weather.test_weather_api.entity.Weather;
import com.weather.test_weather_api.repository.CityCoordinateRepository;
import com.weather.test_weather_api.repository.WeatherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final CityCoordinateRepository cityCoordinateRepository;
    private final CoordinateConnectorImpl coordinateConnector;

    public WeatherService(WeatherRepository weatherRepository, CityCoordinateRepository cityCoordinateRepository, CoordinateConnectorImpl coordinateConnector) {
        this.weatherRepository = weatherRepository;
        this.cityCoordinateRepository = cityCoordinateRepository;
        this.coordinateConnector = coordinateConnector;
    }

    public List<WeatherDto> getListWeatherDto(String city) {
        List<Weather> weather = weatherRepository.findByCityOrderByDate(city);
        return weather.stream().map(WeatherDto::new).collect(Collectors.toList());
    }

    public void addCityToCityCoordinate(String city) {
        if  (cityCoordinateRepository.findByCity(city).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        CityCoordinate cityCoordinate = coordinateConnector.request(city);
        cityCoordinateRepository.save(cityCoordinate);
    }

}
