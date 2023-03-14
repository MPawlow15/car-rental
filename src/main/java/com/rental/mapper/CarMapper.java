package com.rental.mapper;

import com.rental.domain.Car;
import com.rental.domain.dto.CarDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CarMapper {

    public Car mapToCar(final CarDto carDto) {
        return new Car(
                carDto.getBrand(),
                carDto.getModel(),
                carDto.getColor(),
                carDto.getDailyPrice()
        );
    }

    public CarDto mapToCarDto(final Car car) {
        return new CarDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getColor(),
                car.getDailyPrice()
        );
    }

    public ArrayList<CarDto> mapToCarDtoList(final ArrayList<Car> carList) {
        ArrayList<CarDto> carDtoList = new ArrayList<>();
        for (Car car : carList) {
            carDtoList.add(mapToCarDto(car));
        }
        return carDtoList;
    }
}
