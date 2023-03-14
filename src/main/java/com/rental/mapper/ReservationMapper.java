package com.rental.mapper;

import com.rental.domain.Reservation;
import com.rental.domain.dto.ReservationDto;
import org.springframework.stereotype.Service;

@Service
public class ReservationMapper {

    public Reservation mapToReservation(final ReservationDto reservationDto) {
        return new Reservation(
                reservationDto.getStartDate(),
                reservationDto.getEndDate(),
                reservationDto.getEquipment(),
                reservationDto.getInsurance(),
                reservationDto.getUnpaid()
        );
    }

    public ReservationDto mapToReservationDto(final Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getEquipment(),
                reservation.getInsurance(),
                reservation.getUnpaid()
        );
    }
}
