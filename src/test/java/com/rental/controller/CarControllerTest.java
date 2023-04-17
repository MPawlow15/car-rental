package com.rental.controller;

import com.rental.domain.Car;
import com.rental.domain.dto.CarDto;
import com.rental.exception.CarNotFoundException;
import com.rental.mapper.CarMapper;
import com.rental.service.CarDbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarDbService carDbService;

    @Mock
    private CarMapper carMapper;

    private Car car;
    private CarDto carDto;

    @BeforeEach
    public void setUp() {
        car = new Car("Audi", "Make1", "Model1", BigDecimal.valueOf(100));
        carDto = new CarDto(1L,"Audi", "Make1", "Model1", BigDecimal.valueOf(100));
    }

    @Test
    void shouldGetAllCars() {
        // given
        Car car2 = new Car("BMW", "Make2", "Model2", BigDecimal.valueOf(150));
        CarDto carDto2 = new CarDto(2L,"BMW", "Make2", "Model2", BigDecimal.valueOf(150));
        when(carDbService.getAllCars()).thenReturn(Arrays.asList(car, car2));
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);
        when(carMapper.mapToCarDto(car2)).thenReturn(carDto2);

        // when
        ResponseEntity<List<CarDto>> response = carController.getAllCars();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(carDto, response.getBody().get(0));
        assertEquals(carDto2, response.getBody().get(1));
    }

    @Test
    void shouldCreateCar() {
        // given
        when(carMapper.mapToCar(carDto)).thenReturn(car);
        when(carDbService.saveCar(car)).thenReturn(car);
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        // when
        ResponseEntity<CarDto> response = carController.createCar(carDto);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(carDto, response.getBody());
    }

    @Test
    void shouldGetCar() throws CarNotFoundException {
        // given
        when(carDbService.getCar(1L)).thenReturn(car);
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        // when
        ResponseEntity<CarDto> response = carController.getCar(1L);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(carDto, response.getBody());
    }

    @Test
    void shouldDeleteCar() {
        // given
        when(carMapper.mapToCar(carDto)).thenReturn(car);
        when(carDbService.saveCar(car)).thenReturn(car);
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        // when
        carController.createCar(carDto);
        ResponseEntity<CarDto> responseEntity = carController.deleteCar(1L);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(carDbService, times(1)).deleteCar(1L);
    }

    @Test
    void shouldUpdateCar() {
        // given
        Car updatedCar = new Car("", "Audi", "Model2", BigDecimal.valueOf(150));
        CarDto updatedCarDto = new CarDto(1L,"", "Audi", "Model2", BigDecimal.valueOf(150));

        when(carMapper.mapToCar(carDto)).thenReturn(car);
        when(carDbService.saveCar(car)).thenReturn(car);
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        when(carMapper.mapToCar(updatedCarDto)).thenReturn(updatedCar);
        when(carDbService.saveCar(updatedCar)).thenReturn(updatedCar);
        when(carMapper.mapToCarDto(updatedCar)).thenReturn(updatedCarDto);

        // when
        carController.createCar(carDto);
        ResponseEntity<CarDto> updateResponse = carController.updateCar(1L, updatedCarDto);

        // then
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertEquals(updatedCarDto, updateResponse.getBody());
        verify(carDbService, times(1)).saveCar(updatedCar);
    }

}