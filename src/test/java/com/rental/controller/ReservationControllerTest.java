package com.rental.controller;

import com.rental.domain.Reservation;
import com.rental.domain.dto.ReservationDto;
import com.rental.exception.ReservationNotFoundException;
import com.rental.mapper.ReservationMapper;
import com.rental.service.ReservationDbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationDbService reservationDbService;

    @Mock
    private ReservationMapper reservationMapper;

    Reservation reservation;
    ReservationDto reservationDto;

    @BeforeEach
    public void setUp() {
        reservation = new Reservation(new Date(2023, 1, 1),
                new Date(2023, 2, 2), true, true, true);
        reservationDto = new ReservationDto(1L, new Date(2023, 1, 1),
                new Date(2023, 2, 2), true, true, true);
    }

    @Test
    void shouldGetAllReservations() {
        // Given
        Reservation reservation2 = new Reservation(new Date(2023, 3, 1),
                new Date(2023, 4, 2), true, true, true);
        ReservationDto reservationDto2 = new ReservationDto(2L, new Date(2023, 3, 1),
                new Date(2023, 4, 2), true, true, true);
        when(reservationDbService.getAllReservations()).thenReturn(Arrays.asList(reservation, reservation2));
        when(reservationMapper.mapToReservationDto(reservation)).thenReturn(reservationDto);
        when(reservationMapper.mapToReservationDto(reservation2)).thenReturn(reservationDto2);

        // When
        ResponseEntity<List<ReservationDto>> response = reservationController.getAllReservations();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(reservationDto, response.getBody().get(0));
        assertEquals(reservationDto2, response.getBody().get(1));
    }

    @Test
    void shouldGetReservation() throws ReservationNotFoundException {
        // Given
        when(reservationDbService.getReservation(1L)).thenReturn(reservation);
        when(reservationMapper.mapToReservationDto(reservation)).thenReturn(reservationDto);

        // When
        ResponseEntity<ReservationDto> response = reservationController.getReservation(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservationDto, response.getBody());
    }

    @Test
    void shouldCreateReservation() {
        // Given
        when(reservationMapper.mapToReservation(reservationDto)).thenReturn(reservation);
        when(reservationDbService.saveReservation(reservation)).thenReturn(reservation);
        when(reservationMapper.mapToReservationDto(reservation)).thenReturn(reservationDto);

        // When
        ResponseEntity<ReservationDto> response = reservationController.createReservation(reservationDto);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(reservationDto, response.getBody());
    }

    @Test
    void shouldUpdateReservation() throws ReservationNotFoundException {
        // Given
        when(reservationMapper.mapToReservation(reservationDto)).thenReturn(reservation);
        when(reservationDbService.updateReservation(1L, reservation)).thenReturn(reservation);
        when(reservationMapper.mapToReservationDto(reservation)).thenReturn(reservationDto);

        // When
        ResponseEntity<ReservationDto> response = reservationController.updateReservation(1L, reservationDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservationDto, response.getBody());
        verify(reservationDbService, times(1)).updateReservation(1L, reservation);

    }

    @Test
    void shouldDeleteReservation(){
        // Given
        when(reservationMapper.mapToReservation(reservationDto)).thenReturn(reservation);
        when(reservationDbService.saveReservation(reservation)).thenReturn(reservation);
        when(reservationMapper.mapToReservationDto(reservation)).thenReturn(reservationDto);

        // When
        reservationController.createReservation(reservationDto);
        ResponseEntity<ReservationDto> response = reservationController.deleteReservation(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reservationDbService, times(1)).deleteReservation(1L);
    }
}