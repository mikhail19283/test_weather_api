package com.weather.test_weather_api.connector.coordinate;

import com.weather.test_weather_api.entity.CityCoordinate;

public interface CoordinateConnector {
    CityCoordinate request(String url);
}
