package com.rental.service;

import com.rental.domain.dto.WeatherDto;
import com.rental.webclient.weather.WeatherClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    public WeatherDto getWeather() {
        String response = weatherClient.getWeatherForCity("warszawa");
        log.info(response);
        return null;
    }

}