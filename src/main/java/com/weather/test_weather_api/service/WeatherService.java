package com.weather.test_weather_api.service;

import com.weather.test_weather_api.connector.CoordinateConnectorImpl;
import com.weather.test_weather_api.dto.WeatherDto;
import com.weather.test_weather_api.entity.CityCoordinate;
import com.weather.test_weather_api.entity.Weather;
import com.weather.test_weather_api.repository.CityCoordinateRepository;
import com.weather.test_weather_api.repository.WeatherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


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

    public List<WeatherDto> getMethod(String city) {
        List<Weather> weather = weatherRepository.findByCityOrderByDate(city);
        List<WeatherDto> weatherDtos = new ArrayList<>();
        if (weather != null) {
            for (Weather w : weather){
                weatherDtos.add(new WeatherDto(w.getCity(), w.getTemperature(), w.getDate()));
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Город в базе не найден");
        }
        return weatherDtos;
    }


    public void searchCoordinate(String city) {
        if  (cityCoordinateRepository.findByCity(city).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        CityCoordinate cityCoordinate = coordinateConnector.request(city);
        cityCoordinateRepository.save(cityCoordinate);
    }


}
