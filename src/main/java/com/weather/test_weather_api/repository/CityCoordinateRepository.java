package com.weather.test_weather_api.repository;

import com.weather.test_weather_api.entity.CityCoordinate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityCoordinateRepository extends CrudRepository<CityCoordinate, Integer> {
    @Override
    List<CityCoordinate> findAll();

    Optional<CityCoordinate> findByCity(String city);
}
