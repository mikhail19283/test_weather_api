package com.weather.test_weather_api.exception;

import com.weather.test_weather_api.exception.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleException(CityNotFoundException e) {
        ExceptionResponseDto response = new ExceptionResponseDto(e.getMessage(), "404");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
