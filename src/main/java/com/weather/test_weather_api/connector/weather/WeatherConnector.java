package com.weather.test_weather_api.connector.weather;

import com.weather.test_weather_api.entity.CityCoordinate;

public interface WeatherConnector {
    Double request(CityCoordinate cityCoordinate);
}
