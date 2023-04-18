package com.rental.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Reckoning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal fuelUsed;
    private BigDecimal equipmentPrice;
    private BigDecimal penaltiesPrice;
    private BigDecimal totalPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hire_id")
    private Hire hire;

    public Reckoning(BigDecimal fuelUsed, BigDecimal equipmentPrice, BigDecimal penaltiesPrice, BigDecimal totalPrice) {
        this.fuelUsed = fuelUsed;
        this.equipmentPrice = equipmentPrice;
        this.penaltiesPrice = penaltiesPrice;
        this.totalPrice = totalPrice;
    }
}