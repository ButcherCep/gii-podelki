package ru.sbp.gazii.podelki.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Set;


@Data
@AllArgsConstructor
class PurchaseStats {
    long totalOrders; //Общее количество заказов;
    BigDecimal averageOrderValue; //Средняя сумма заказа;
    Set<ProductCategory> purchasedCategories; //категории заказов
    Set<Month> activeMonths; // активные месяца заказов
}



