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
public class Penalties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal penaltyForDamage;
    private BigDecimal penaltyForUntimelyReturn;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hire_id")
    private Hire hire;

    public Penalties(BigDecimal penaltyForDamage, BigDecimal penaltyForUntimelyReturn) {
        this.penaltyForDamage = penaltyForDamage;
        this.penaltyForUntimelyReturn = penaltyForUntimelyReturn;
    }
}