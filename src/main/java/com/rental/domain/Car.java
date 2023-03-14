package com.rental.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "daily_price", nullable = false)
    private BigDecimal dailyPrice;

    @OneToMany(mappedBy = "car")
    private List<Hire> hires = new ArrayList<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Equipment> equipmentList = new ArrayList<>();

    public Car(String brand, String model, String color, BigDecimal dailyPrice) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.dailyPrice = dailyPrice;
    }

    public Car(Long id, String brand, String model, String color, BigDecimal dailyPrice) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.dailyPrice = dailyPrice;
    }
}