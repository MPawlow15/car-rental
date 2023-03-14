package com.rental.service;

import com.rental.domain.Reservation;
import com.rental.exception.ReservationNotFoundException;
import com.rental.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ReservationDbServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationDbService reservationDbService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllReservations() {
        // given
        Reservation reservation1 = new Reservation(1L, null, new Date(1220227200000L), new Date(1220400000000L), true
                , false, false);
        Reservation reservation2 = new Reservation(2L, null, new Date(1220400000000L), new Date(1220572800000L),
                false, true, false);
        List<Reservation> expectedReservations = Arrays.asList(reservation1, reservation2);
        when(reservationRepository.findAll()).thenReturn(expectedReservations);

        // when
        List<Reservation> actualReservations = reservationDbService.getAllReservations();

        // then
        assertEquals(expectedReservations, actualReservations);
    }

    @Test
    void shouldReturnReservationById() throws ReservationNotFoundException {
        // given
        Reservation expectedReservation = new Reservation(1L, null, new Date(1220227200000L),
                new Date(1220400000000L), true, false, false);
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(expectedReservation));

        // when
        Reservation actualReservation = reservationDbService.getReservation(1L);

        // then
        assertEquals(expectedReservation, actualReservation);
    }

    @Test
    void shouldThrowReservationNotFoundExceptionWhenReservationNotFound() {
        // given
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when + then
        assertThrows(ReservationNotFoundException.class, () -> reservationDbService.getReservation(1L));
    }

    @Test
    void shouldSaveReservation() {
        // given
        Reservation reservation = new Reservation(null, null, new Date(1220227200000L), new Date(1220400000000L),
                true, false, false);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // when
        Reservation savedReservation = reservationDbService.saveReservation(reservation);

        // then
        assertEquals(reservation, savedReservation);
    }
    @Test
    void shouldUpdateReservation() throws ReservationNotFoundException {
        // given
        Reservation existingReservation = new Reservation(1L, null, new Date(1220227200000L),
                new Date(1220400000000L), true, false, false);
        Reservation updatedReservation = new Reservation(null, null, new Date(1220227200000L),
                new Date(1220400000000L), false, true, false);
        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(existingReservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(updatedReservation);

        // when
        Reservation actualReservation = reservationDbService.updateReservation(1L, updatedReservation);

        // then
        assertEquals(updatedReservation, actualReservation);
        assertEquals(existingReservation.getStartDate(), actualReservation.getStartDate());
        assertEquals(existingReservation.getEndDate(), actualReservation.getEndDate());
    }
}