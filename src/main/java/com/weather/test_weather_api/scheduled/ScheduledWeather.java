package com.weather.test_weather_api.scheduled;

import com.weather.test_weather_api.connector.weather.WeatherConnector;
import com.weather.test_weather_api.util.MathRound;
import com.weather.test_weather_api.entity.CityCoordinate;
import com.weather.test_weather_api.entity.Weather;
import com.weather.test_weather_api.repository.CityCoordinateRepository;
import com.weather.test_weather_api.repository.WeatherRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
public class ScheduledWeather {


    private List<CityCoordinate> citiesCoordinates = new ArrayList<>();

    private final CityCoordinateRepository cityCoordinateRepository;
    private final WeatherRepository weatherRepository;
    private final WeatherConnector openWeatherConnector;
    private final WeatherConnector yandexWeatherConnector;

    public ScheduledWeather(CityCoordinateRepository cityCoordinateRepository, WeatherRepository weatherRepository, WeatherConnector openWeatherConnector, WeatherConnector yandexWeatherConnector) {
        this.cityCoordinateRepository = cityCoordinateRepository;
        this.weatherRepository = weatherRepository;
        this.openWeatherConnector = openWeatherConnector;
        this.yandexWeatherConnector = yandexWeatherConnector;
    }


    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    private void temperatureOnCity() {

        if (citiesCoordinates.size() != cityCoordinateRepository.count()) {
                citiesCoordinates = cityCoordinateRepository.findAll();
        }

        for (CityCoordinate cityCoordinate : citiesCoordinates) {
            Double openWeatherTemp = openWeatherConnector.request(cityCoordinate);
            Double yandexWeatherTemp = yandexWeatherConnector.request(cityCoordinate);
            var mean = (openWeatherTemp + yandexWeatherTemp) / 2;
            System.out.println(yandexWeatherTemp);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            weatherRepository.save(new Weather(cityCoordinate.getCity(), MathRound.round(mean, 2), timestamp));
        }
    }
}
