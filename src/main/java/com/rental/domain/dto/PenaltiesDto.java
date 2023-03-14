package com.rental.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class PenaltiesDto {
    private Long id;
    private BigDecimal penaltyForDamage;
    private BigDecimal penaltyForUntimelyReturn;
}
