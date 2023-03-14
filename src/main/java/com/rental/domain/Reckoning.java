package com.rental.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Entity
public class Reckoning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hire_id")
    private Hire hire;

    private BigDecimal fuelUsed;
    private BigDecimal equipmentPrice;
    private BigDecimal penaltiesPrice;
    private BigDecimal totalPrice;

    public Reckoning(BigDecimal fuelUsed, BigDecimal equipmentPrice, BigDecimal penaltiesPrice, BigDecimal totalPrice) {
        this.fuelUsed = fuelUsed;
        this.equipmentPrice = equipmentPrice;
        this.penaltiesPrice = penaltiesPrice;
        this.totalPrice = totalPrice;
    }
}