package com.rental.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class EquipmentDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
