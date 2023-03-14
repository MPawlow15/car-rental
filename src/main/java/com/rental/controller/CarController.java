package com.rental.controller;

import com.rental.domain.Car;
import com.rental.domain.dto.CarDto;
import com.rental.exception.CarNotFoundException;
import com.rental.mapper.CarMapper;
import com.rental.service.CarDbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarDbService carDbService;
    private final CarMapper carMapper;

    public CarController(CarDbService carDbService, CarMapper carMapper) {
        this.carDbService = carDbService;
        this.carMapper = carMapper;
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        Car car = carMapper.mapToCar(carDto);
        carDbService.saveCar(car);
        return new ResponseEntity<>(carMapper.mapToCarDto(car), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<Car> cars = carDbService.getAllCars();
        List<CarDto> carDtoList = cars.stream()
                .map(carMapper::mapToCarDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(carDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable Long id) throws CarNotFoundException {
        Car car = carDbService.getCar(id);
        return new ResponseEntity<>(carMapper.mapToCarDto(car), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id, @RequestBody CarDto carDto) {
        Car car = carMapper.mapToCar(carDto);
        carDbService.saveCar(car);
        return new ResponseEntity<>(carMapper.mapToCarDto(car), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarDto> deleteCar(@PathVariable Long id) {
        carDbService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
