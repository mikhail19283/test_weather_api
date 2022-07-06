package com.weather.test_weather_api.repository;

import com.weather.test_weather_api.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    List<Weather> findByCityOrderByDate(String city);
}
