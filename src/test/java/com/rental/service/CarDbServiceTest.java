package com.rental.service;

import com.rental.domain.Car;
import com.rental.exception.CarNotFoundException;
import com.rental.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarDbServiceTest {

    private CarDbService carDbService;

    @Mock
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        carDbService = new CarDbService(carRepository);
    }

    @Test
    public void shouldReturnAllCars() {
        // given
        Car car1 = new Car(1L, "Ford", "Mustang", "Red", new BigDecimal("2500"));
        Car car2 = new Car(1L, "BMW", "X5", "Black", new BigDecimal("3000"));
        List<Car> cars = Arrays.asList(car1, car2);

        when(carRepository.findAll()).thenReturn(cars);

        // when
        List<Car> result = carDbService.getAllCars();

        // then
        assertEquals(cars, result);
        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnCarById() throws CarNotFoundException {
        // given
        Long id = 1L;
        Car car = new Car(1L, "Ford", "Mustang", "Red", new BigDecimal("2500"));

        when(carRepository.findById(id)).thenReturn(Optional.of(car));

        // when
        Car result = carDbService.getCar(id);

        // then
        assertEquals(car, result);
        verify(carRepository, times(1)).findById(id);
    }

    @Test
    public void shouldThrowExceptionWhenCarByIdNotFound() {
        // given
        Long id = 1L;

        when(carRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThrows(CarNotFoundException.class, () -> carDbService.getCar(id));
        verify(carRepository, times(1)).findById(id);
    }

    @Test
    public void shouldSaveCar() {
        // given
        Car car = new Car(1L, "Ford", "Mustang", "Red", new BigDecimal("2500"));

        when(carRepository.save(car)).thenReturn(car);

        // when
        Car result = carDbService.saveCar(car);

        // then
        assertEquals(car, result);
        verify(carRepository, times(1)).save(car);
    }

}