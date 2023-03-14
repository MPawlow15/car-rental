package com.rental.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Hire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Temporal(TemporalType.DATE)
    private Date dateOfRental;

    @Temporal(TemporalType.DATE)
    private Date dateOfReturn;

    private BigDecimal penalty;

    @OneToOne(mappedBy = "hire")
    private Reckoning reckoning;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @OneToOne(mappedBy = "hire", cascade = CascadeType.ALL)
    private Penalties penalties;

    public Hire(Date dateOfRental, Date dateOfReturn, BigDecimal penalty) {
        this.dateOfRental = dateOfRental;
        this.dateOfReturn = dateOfReturn;
        this.penalty = penalty;
    }
}