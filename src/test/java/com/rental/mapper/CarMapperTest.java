package com.rental.mapper;

import com.rental.domain.Car;
import com.rental.domain.dto.CarDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarMapperTest {
    private final CarMapper carMapper = new CarMapper();

    @Test
    public void shouldMapToCar() {
        // given
        CarDto carDto = new CarDto(null, "Toyota", "Corolla", "Red", BigDecimal.valueOf(100));

        // when
        Car actualCar = carMapper.mapToCar(carDto);

        // then
        assertEquals(carDto.getBrand(), actualCar.getBrand());
        assertEquals(carDto.getModel(), actualCar.getModel());
        assertEquals(carDto.getColor(), actualCar.getColor());
        assertEquals(carDto.getDailyPrice(), actualCar.getDailyPrice());
    }

    @Test
    public void shouldMapToCarDto() {
        // given
        Car car = new Car(1L, "Toyota", "Corolla", "Red", BigDecimal.valueOf(100));

        // when
        CarDto actualCarDto = carMapper.mapToCarDto(car);

        // then
        assertEquals(car.getId(), actualCarDto.getId());
        assertEquals(car.getBrand(), actualCarDto.getBrand());
        assertEquals(car.getModel(), actualCarDto.getModel());
        assertEquals(car.getColor(), actualCarDto.getColor());
        assertEquals(car.getDailyPrice(), actualCarDto.getDailyPrice());
    }

    @Test
    public void shouldMapToCarDtoList() {
        // given
        Car car1 = new Car(1L, "Toyota", "Corolla", "Red", BigDecimal.valueOf(100));
        Car car2 = new Car(2L, "Honda", "Civic", "Blue", BigDecimal.valueOf(90));
        List<Car> carList = new ArrayList<>();
        carList.add(car1);
        carList.add(car2);

        // when
        List<CarDto> actualCarDtoList = carMapper.mapToCarDtoList((ArrayList<Car>) carList);

        // then
        assertEquals(carList.size(), actualCarDtoList.size());
        assertEquals(carList.get(0).getId(), actualCarDtoList.get(0).getId());
        assertEquals(carList.get(0).getBrand(), actualCarDtoList.get(0).getBrand());
        assertEquals(carList.get(0).getModel(), actualCarDtoList.get(0).getModel());
        assertEquals(carList.get(0).getColor(), actualCarDtoList.get(0).getColor());
        assertEquals(carList.get(0).getDailyPrice(), actualCarDtoList.get(0).getDailyPrice());
        assertEquals(carList.get(1).getId(), actualCarDtoList.get(1).getId());
        assertEquals(carList.get(1).getBrand(), actualCarDtoList.get(1).getBrand());
        assertEquals(carList.get(1).getModel(), actualCarDtoList.get(1).getModel());
        assertEquals(carList.get(1).getColor(), actualCarDtoList.get(1).getColor());
        assertEquals(carList.get(1).getDailyPrice(), actualCarDtoList.get(1).getDailyPrice());
    }

}