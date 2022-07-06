package com.weather.test_weather_api.service;

import com.weather.test_weather_api.TestWeatherApiApplication;
import com.weather.test_weather_api.dto.Coordinate;
import com.weather.test_weather_api.dto.MeteoBlueResponseDto;
import com.weather.test_weather_api.dto.OpenWeatherResponseDto;
import com.weather.test_weather_api.entity.Weather;
import com.weather.test_weather_api.repository.WeatherRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Map;


@EnableScheduling
@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public String getMethod(String city){
        return city;
    }

    @Scheduled(fixedRate = 5000)
    private void temperatureOnCity(){


        RestTemplate restTemplate = new RestTemplate();

        for (Map.Entry<String, Coordinate> coordinateEntry : citiesWithCoordinates.entrySet()) {
            String urlOpenWeather = "https://api.openweathermap.org/data/2.5/weather?lat=" + coordinateEntry.getValue().getLat()
                    + "&lon=" + coordinateEntry.getValue().getLon() + "&appid=5b9bcf19850e94e87b31de74b84b79cf";
            String urlMeteoBlue = "https://my.meteoblue.com/packages/current?apikey=wqkh9TiABrS0GLfn&lat=" + coordinateEntry.getValue().getLat()
                    +"&lon=" + coordinateEntry.getValue().getLon() + "&asl=219&format=json";

            Double openWeatherTemp = restTemplate.getForObject(urlOpenWeather, OpenWeatherResponseDto.class).getMain().getTemp();
            Double meteoBlueTemp = restTemplate.getForObject(urlMeteoBlue, MeteoBlueResponseDto.class).getDataCurrent().getTemperature() + 273.15;

            Double mean = (openWeatherTemp + meteoBlueTemp) / 2;

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            weatherRepository.save(new Weather(coordinateEntry.getKey(), mean, timestamp));

        }
    }

}
