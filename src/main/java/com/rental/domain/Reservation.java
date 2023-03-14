package com.rental.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "reservation")
    private List<Hire> hires = new ArrayList<>();

    @Column(name = "start_date", columnDefinition = "DATE")
    private java.sql.Date startDate;

    @Column(name = "end_date", columnDefinition = "DATE")
    private java.sql.Date endDate;;

    @Column(name = "equipment")
    private Boolean equipment;

    @Column(name = "insurance")
    private Boolean insurance;

    @Column(name = "unpaid")
    private Boolean unpaid;

    public Reservation(Date startDate, Date endDate, boolean equipment, boolean insurance, boolean unpaid) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.equipment = equipment;
        this.insurance = insurance;
        this.unpaid = unpaid;
    }

}