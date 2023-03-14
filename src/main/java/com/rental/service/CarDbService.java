package com.rental.service;

import com.rental.domain.Car;
import com.rental.exception.CarNotFoundException;
import com.rental.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarDbService {

    private final CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCar(final Long id) throws CarNotFoundException {
        return carRepository.findById(id).orElseThrow(CarNotFoundException::new);
    }

    public Car saveCar(final Car car) {
        return carRepository.save(car);
    }

    public void deleteCar(final Long id) {
        carRepository.deleteById(id);
    }
}
