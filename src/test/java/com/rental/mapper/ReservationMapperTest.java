package com.rental.mapper;

import com.rental.domain.Reservation;
import com.rental.domain.dto.ReservationDto;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationMapperTest {

    private final ReservationMapper reservationMapper = new ReservationMapper();

    @Test
    void mapToReservation() {
        ReservationDto reservationDto = new ReservationDto(1L, Date.valueOf("2022-05-01"), Date.valueOf("2022-05-08")
                , true, true, false);

        // when
        Reservation reservation = reservationMapper.mapToReservation(reservationDto);

        // then
        assertEquals(reservation.getStartDate(), reservationDto.getStartDate());
        assertEquals(reservation.getEndDate(), reservationDto.getEndDate());
        assertEquals(reservation.getEquipment(), reservationDto.getEquipment());
        assertEquals(reservation.getInsurance(), reservationDto.getInsurance());
        assertEquals(reservation.getUnpaid(), reservationDto.getUnpaid());
    }

    @Test
    void mapToReservationDto() {
        // given
        Reservation reservation = new Reservation(Date.valueOf("2022-05-01"), Date.valueOf("2022-05-08"), true, true,
                false);

        // when
        ReservationDto reservationDto = reservationMapper.mapToReservationDto(reservation);

        // then
        assertEquals(reservationDto.getStartDate(), reservation.getStartDate());
        assertEquals(reservationDto.getEndDate(), reservation.getEndDate());
        assertEquals(reservationDto.getEquipment(), reservation.getEquipment());
        assertEquals(reservationDto.getInsurance(), reservation.getInsurance());
        assertEquals(reservationDto.getUnpaid(), reservation.getUnpaid());
    }
}