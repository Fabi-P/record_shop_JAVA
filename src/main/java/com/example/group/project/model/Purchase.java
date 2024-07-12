package com.example.group.project.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "Purchases")
@Data
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id", nullable = false)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customer;

    @Column(name = "item_id", nullable = false)
    private int item;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate date;

}
