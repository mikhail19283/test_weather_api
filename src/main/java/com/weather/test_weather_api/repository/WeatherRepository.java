package com.weather.test_weather_api.repository;

import com.weather.test_weather_api.entity.Weather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Integer> {
    List<Weather> findByCityOrderByDate(String city);
}
