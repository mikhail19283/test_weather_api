package com.weather.test_weather_api.repository;

import com.weather.test_weather_api.entity.CityCoordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityCoordinateRepository extends JpaRepository<CityCoordinate, Integer> {

    Optional<CityCoordinate> findByCity(String city);
}
