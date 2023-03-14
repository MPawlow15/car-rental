package com.rental.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ReckoningDto {
    private Long id;
    private BigDecimal fuelUsed;
    private BigDecimal equipmentPrice;
    private BigDecimal penaltiesPrice;
    private BigDecimal totalPrice;
}
