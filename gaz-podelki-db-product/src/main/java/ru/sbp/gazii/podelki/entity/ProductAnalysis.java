package ru.sbp.gazii.podelki.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Data
@AllArgsConstructor
class ProductAnalysis {
    Map<Product, List<Product>> frequentlyBoughtTogether; // Product -> список часто покупаемых с ним
    Set<Product> hubProducts; // товары, которые покупают с 3+ другими товарами
    Map<Client, List<Product>> recommendations; // персональные рекомендации
}


