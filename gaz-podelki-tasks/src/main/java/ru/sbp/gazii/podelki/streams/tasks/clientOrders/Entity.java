package ru.sbp.gazii.podelki.streams.tasks.clientOrders;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
class Client {
    private Long id;
    private String name;
    private String email;
    private LocalDate registrationDate;
    private ClientCategory category;
}

@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
class Order implements Comparable<Order> {
    private Long id;
    private Client client;
    private LocalDateTime orderDate;
    private OrderStatus status; // PENDING, PROCESSING, COMPLETED, CANCELLED
    private List<OrderItem> items;
    private BigDecimal totalAmount;

    @Override
    public int compareTo(@NotNull Order order) {
        return this.orderDate.compareTo(order.orderDate);
    }
}
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
@Data
@AllArgsConstructor
class Product {
    private Long id;
    private String name;
    private ProductCategory category;
    private BigDecimal basePrice;
}
@Data
@AllArgsConstructor
class PurchaseStats {
    long totalOrders; //Общее количество заказов;
    BigDecimal averageOrderValue; //Средняя сумма заказа;
    Set<ProductCategory> purchasedCategories; //категории заказов
    Set<Month> activeMonths; // активные месяца заказов
}
@Data
@AllArgsConstructor
class ClientFunnel {
    Client client; // клиент
    List<ProductCategory> purchasePattern; //паттерн заказов
    boolean isUpgradeSpender; // перешёл от дешёвых к дорогим
    boolean isLostClient;     // нет заказов 3+ месяца
    LocalDateTime lastOrderDate; // дата последнего заказа
}
@Data
@AllArgsConstructor
class ProductAnalysis {
    Map<Product, List<Product>> frequentlyBoughtTogether; // Product -> список часто покупаемых с ним
    Set<Product> hubProducts; // товары, которые покупают с 3+ другими товарами
    Map<Client, List<Product>> recommendations; // персональные рекомендации
}

enum ClientCategory {
    STANDARD, VIP, PREMIUM
}

enum OrderStatus {
    PENDING, PROCESSING, COMPLETED, CANCELLED
}

enum ProductCategory {
    ELECTRONICS, CLOTHING, BOOKS, FOOD
}