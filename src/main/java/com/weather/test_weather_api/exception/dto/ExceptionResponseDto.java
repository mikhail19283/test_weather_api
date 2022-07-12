package com.weather.test_weather_api.exception.dto;

public class ExceptionResponseDto {
    private final String message;
    private final String code;

    public ExceptionResponseDto(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

}
