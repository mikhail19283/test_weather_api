package com.weather.test_weather_api.service;

import com.weather.test_weather_api.connector.coordinate.CoordinateConnector;
import com.weather.test_weather_api.dto.WeatherDto;
import com.weather.test_weather_api.entity.CityCoordinate;
import com.weather.test_weather_api.entity.Weather;
import com.weather.test_weather_api.exception.CityException;
import com.weather.test_weather_api.exception.CityNotFoundException;
import com.weather.test_weather_api.repository.CityCoordinateRepository;
import com.weather.test_weather_api.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final CityCoordinateRepository cityCoordinateRepository;
    private final CoordinateConnector coordinateConnector;

    public WeatherService(WeatherRepository weatherRepository, CityCoordinateRepository cityCoordinateRepository, CoordinateConnector coordinateConnector) {
        this.weatherRepository = weatherRepository;
        this.cityCoordinateRepository = cityCoordinateRepository;
        this.coordinateConnector = coordinateConnector;
    }

    public List<WeatherDto> getListWeatherDto(String city) {
        List<Weather> weather = weatherRepository.findByCityOrderByDate(city.toUpperCase());
        if (weather.size() == 0) {
            throw new CityNotFoundException();
        }
        return weather.stream().map(w -> new WeatherDto(w.getCity(), w.getTemperature(), w.getDate())).collect(Collectors.toList());
    }

    public void addCityToCityCoordinate(String city) {
        cityCoordinateRepository.findByCity(city).orElseThrow(CityException::new);
        CityCoordinate cityCoordinate = coordinateConnector.request(city);
        cityCoordinateRepository.save(cityCoordinate);
    }

}
