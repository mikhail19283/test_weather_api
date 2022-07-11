package com.weather.test_weather_api.scheduled;

import com.weather.test_weather_api.util.MathRound;
import com.weather.test_weather_api.entity.CityCoordinate;
import com.weather.test_weather_api.entity.Weather;
import com.weather.test_weather_api.repository.CityCoordinateRepository;
import com.weather.test_weather_api.repository.WeatherRepository;
import com.weather.test_weather_api.connector.weather.MeteoBlueConnectorImpl;
import com.weather.test_weather_api.connector.weather.OpenWeatherConnectorImpl;
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
    private final OpenWeatherConnectorImpl openWeatherConnector;
    private final MeteoBlueConnectorImpl meteoBlueConnector;

    public ScheduledWeather(CityCoordinateRepository cityCoordinateRepository, WeatherRepository weatherRepository, OpenWeatherConnectorImpl openWeatherConnector, MeteoBlueConnectorImpl meteoBlueConnector) {
        this.cityCoordinateRepository = cityCoordinateRepository;
        this.weatherRepository = weatherRepository;
        this.openWeatherConnector = openWeatherConnector;
        this.meteoBlueConnector = meteoBlueConnector;
    }


    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    private void temperatureOnCity() {

        if (citiesCoordinates.size() != cityCoordinateRepository.count()) {
            citiesCoordinates = cityCoordinateRepository.findAll();
        }

        for (CityCoordinate cityCoordinate : citiesCoordinates) {

            Double openWeatherTemp = openWeatherConnector.request(cityCoordinate);
            Double meteoBlueTemp = meteoBlueConnector.request(cityCoordinate);

            var mean = (openWeatherTemp + meteoBlueTemp) / 2;

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            weatherRepository.save(new Weather(cityCoordinate.getCity(), MathRound.round(mean, 2), timestamp));
        }
    }
}
