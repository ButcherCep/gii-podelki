package ru.sbp.gazii.podelki.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
class Product {
    private Long id;
    private String name;
    private ProductCategory category;
    private BigDecimal basePrice;
}


