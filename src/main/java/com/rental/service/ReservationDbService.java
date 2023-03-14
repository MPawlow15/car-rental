package com.rental.service;

import com.rental.domain.Reservation;
import com.rental.exception.ReservationNotFoundException;
import com.rental.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationDbService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservation(final Long id) throws ReservationNotFoundException {
        return reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);
    }

    public Reservation saveReservation(final Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(final Long id, final Reservation newReservation) throws ReservationNotFoundException {
        Reservation reservation = getReservation(id);

        reservation.setStartDate(newReservation.getStartDate());
        reservation.setEndDate(newReservation.getEndDate());

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(final Long id) {
        reservationRepository.deleteById(id);
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void deleteUnpaidReservations() {
        List<Reservation> unpaidReservations = reservationRepository.findByUnpaidTrue();
        for (Reservation reservation : unpaidReservations) {
            if (reservation.getStartDate().before(new Date(System.currentTimeMillis()))) {
                reservationRepository.delete(reservation);
            }
        }
    }
}