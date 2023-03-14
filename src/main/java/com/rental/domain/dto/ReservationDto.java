package com.rental.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class ReservationDto {

    private Long id;
    private Date startDate;
    private Date endDate;
    private Boolean equipment;
    private Boolean insurance;
    private Boolean unpaid;
}
