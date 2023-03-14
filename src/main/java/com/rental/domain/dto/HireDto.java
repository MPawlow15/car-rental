package com.rental.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@AllArgsConstructor
public class HireDto {
    private Long id;
    private Date dateOfRental;
    private Date dateOfReturn;
    private BigDecimal penalty;

}
