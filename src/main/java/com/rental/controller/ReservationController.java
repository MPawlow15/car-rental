package com.rental.controller;

import com.rental.domain.Reservation;
import com.rental.domain.dto.ReservationDto;
import com.rental.exception.ReservationNotFoundException;
import com.rental.mapper.ReservationMapper;
import com.rental.service.ReservationDbService;
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

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.mapToReservation(reservationDto);
        reservationDbService.saveReservation(reservation);
        return new ResponseEntity<>(reservationMapper.mapToReservationDto(reservation), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<Reservation> reservations = reservationDbService.getAllReservations();
        List<ReservationDto> reservationDtoList = reservations.stream()
                .map(reservationMapper::mapToReservationDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(reservationDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id) throws ReservationNotFoundException {
        Reservation reservation = reservationDbService.getReservation(id);
        return new ResponseEntity<>(reservationMapper.mapToReservationDto(reservation), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Long id, @RequestBody ReservationDto reservationDto) throws ReservationNotFoundException {
        Reservation reservation = reservationMapper.mapToReservation(reservationDto);
        Reservation updatedReservation = reservationDbService.updateReservation(id, reservation);
        return new ResponseEntity<>(reservationMapper.mapToReservationDto(updatedReservation), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationDto> deleteReservation(@PathVariable Long id) {
        reservationDbService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}