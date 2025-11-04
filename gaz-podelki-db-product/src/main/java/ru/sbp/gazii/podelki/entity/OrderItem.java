package ru.sbp.gazii.podelki.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
class OrderItem {
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;
    public BigDecimal getItemTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}