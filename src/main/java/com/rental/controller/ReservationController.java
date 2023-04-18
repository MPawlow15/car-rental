package com.rental.controller;

import com.rental.domain.Reservation;
import com.rental.domain.dto.ReservationDto;
import com.rental.exception.ReservationNotFoundException;
import com.rental.mapper.ReservationMapper;
import com.rental.service.ReservationDbService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDbService reservationDbService;
    private final ReservationMapper reservationMapper;

    public ReservationController(ReservationDbService reservationDbService, ReservationMapper reservationMapper) {
        this.reservationDbService = reservationDbService;
        this.reservationMapper = reservationMapper;
    }

    @ApiOperation(value = "Create a new reservation")
    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.mapToReservation(reservationDto);
        reservationDbService.saveReservation(reservation);
        return new ResponseEntity<>(reservationMapper.mapToReservationDto(reservation), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get all reservations")
    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<Reservation> reservations = reservationDbService.getAllReservations();
        List<ReservationDto> reservationDtoList = reservations.stream()
                .map(reservationMapper::mapToReservationDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(reservationDtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a reservation by id")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id) throws ReservationNotFoundException {
        Reservation reservation = reservationDbService.getReservation(id);
        return new ResponseEntity<>(reservationMapper.mapToReservationDto(reservation), HttpStatus.OK);
    }

    @ApiOperation(value = "Update a reservation")
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Long id, @RequestBody ReservationDto reservationDto) throws ReservationNotFoundException {
        Reservation reservation = reservationMapper.mapToReservation(reservationDto);
        reservationDbService.updateReservation(id, reservation);
        return new ResponseEntity<>(reservationMapper.mapToReservationDto(reservation), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a reservation")
    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationDto> deleteReservation(@PathVariable Long id) {
        reservationDbService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}