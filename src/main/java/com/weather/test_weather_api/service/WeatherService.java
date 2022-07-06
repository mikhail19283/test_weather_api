package com.weather.test_weather_api.service;

import com.weather.test_weather_api.dto.CityOpenWeatherResponseDto;
import com.weather.test_weather_api.dto.MeteoBlueResponseDto;
import com.weather.test_weather_api.dto.OpenWeatherResponseDto;
import com.weather.test_weather_api.dto.WeatherDto;
import com.weather.test_weather_api.entity.CityCoordinate;
import com.weather.test_weather_api.entity.Weather;
import com.weather.test_weather_api.repository.CityCoordinateRepository;
import com.weather.test_weather_api.repository.WeatherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@EnableScheduling
public class WeatherService {

    private List<CityCoordinate> citiesCoordinates = new ArrayList<>();

    private final WeatherRepository weatherRepository;
    private final CityCoordinateRepository cityCoordinateRepository;

    public WeatherService(WeatherRepository weatherRepository, CityCoordinateRepository cityCoordinateRepository) {
        this.weatherRepository = weatherRepository;
        this.cityCoordinateRepository = cityCoordinateRepository;
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

    @Scheduled(fixedRate = 300000)
    private void temperatureOnCity() {

        if (citiesCoordinates.size() != cityCoordinateRepository.count()) {
            citiesCoordinates = cityCoordinateRepository.findAll();
        }

        RestTemplate restTemplate = new RestTemplate();

        for (CityCoordinate cityCoordinate : citiesCoordinates) {
            String urlOpenWeather = "https://api.openweathermap.org/data/2.5/weather?lat=" + cityCoordinate.getLat()
                    + "&lon=" + cityCoordinate.getLon() + "&appid=5b9bcf19850e94e87b31de74b84b79cf&units=metric";
            String urlMeteoBlue = "https://my.meteoblue.com/packages/current?apikey=wqkh9TiABrS0GLfn&lat=" + cityCoordinate.getLat()
                    + "&lon=" + cityCoordinate.getLon() + "&asl=219&format=json";

            OpenWeatherResponseDto openWeather = restTemplate.getForObject(urlOpenWeather, OpenWeatherResponseDto.class);
            MeteoBlueResponseDto meteoBlue = restTemplate.getForObject(urlMeteoBlue, MeteoBlueResponseDto.class);

            Double mean = (openWeather.getMain().getTemp() + meteoBlue.getDataCurrent().getTemperature()) / 2;

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            weatherRepository.save(new Weather(cityCoordinate.getCity(), round(mean, 2), timestamp));
        }
    }

    public ResponseEntity<Void> searchCoordinate(String city) {
        if (cityCoordinateRepository.findByCity(city).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RestTemplate restTemplate = new RestTemplate();
        String currentUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=5b9bcf19850e94e87b31de74b84b79cf";
        CityOpenWeatherResponseDto cityOpenWeather = restTemplate.getForObject(currentUrl, CityOpenWeatherResponseDto.class);
        cityCoordinateRepository.save(new CityCoordinate(city, cityOpenWeather.getCoord().getLat(), cityOpenWeather.getCoord().getLon()));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
