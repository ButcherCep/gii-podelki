package ru.sbp.gazii.podelki.streams.tasks.clientOrders;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
@Getter
public class Data {

    Client client1 = new Client(1L, "Иван Петров", "ivan@mail.com",
            LocalDate.of(2022, 1, 15), ClientCategory.VIP);

    Client client2 = new Client(2L, "Мария Сидорова", "maria@mail.com",
            LocalDate.of(2023, 3, 20), ClientCategory.STANDARD);

    Client client3 = new Client(3L, "Алексей Козлов", "alex@mail.com",
            LocalDate.of(2021, 11, 5), ClientCategory.PREMIUM);

    Product product1 = new Product(101L, "iPhone 15", ProductCategory.ELECTRONICS, new BigDecimal("999.99"));
    Product product2 = new Product(102L, "MacBook Pro", ProductCategory.ELECTRONICS, new BigDecimal("2499.99"));
    Product product3 = new Product(201L, "Футболка", ProductCategory.CLOTHING, new BigDecimal("29.99"));
    Product product4 = new Product(202L, "Джинсы", ProductCategory.CLOTHING, new BigDecimal("79.99"));
    Product product5 = new Product(301L, "Java Concurrency", ProductCategory.BOOKS, new BigDecimal("49.99"));

    List<Order> orders = Arrays.asList(
            // Заказы клиента 1
            new Order(1001L, client1, LocalDateTime.of(2024, 1, 10, 14, 30), OrderStatus.COMPLETED,
                    Arrays.asList(
                            new OrderItem(product1, 1, new BigDecimal("999.99")),
                            new OrderItem(product3, 2, new BigDecimal("29.99"))
                    ), new BigDecimal("1059.97")), // 999.99 + (29.99 * 2)

            new Order(1002L, client1, LocalDateTime.of(2024, 1, 15, 10, 15), OrderStatus.COMPLETED,
                    Arrays.asList(
                            new OrderItem(product2, 1, new BigDecimal("2499.99"))
                    ), new BigDecimal("2499.99")),

            new Order(1003L, client1, LocalDateTime.of(2024, 1, 20, 16, 45), OrderStatus.COMPLETED,
                    Arrays.asList(
                            new OrderItem(product4, 1, new BigDecimal("79.99")),
                            new OrderItem(product5, 3, new BigDecimal("49.99"))
                    ), new BigDecimal("229.96")), // 79.99 + (49.99 * 3)

            // Заказы клиента 2
            new Order(1004L, client2, LocalDateTime.of(2024, 1, 12, 9, 0), OrderStatus.COMPLETED,
                    Arrays.asList(
                            new OrderItem(product3, 1, new BigDecimal("29.99")),
                            new OrderItem(product4, 1, new BigDecimal("79.99"))
                    ), new BigDecimal("109.98")),

            new Order(1005L, client2, LocalDateTime.of(2024, 1, 18, 11, 30), OrderStatus.COMPLETED,
                    Arrays.asList(
                            new OrderItem(product1, 1, new BigDecimal("999.99"))
                    ), new BigDecimal("999.99")),

            // Заказы клиента 3
            new Order(1006L, client3, LocalDateTime.of(2024, 1, 5, 13, 20), OrderStatus.COMPLETED,
                    Arrays.asList(
                            new OrderItem(product2, 2, new BigDecimal("2499.99")),
                            new OrderItem(product5, 1, new BigDecimal("49.99"))
                    ), new BigDecimal("5049.97")), // (2499.99 * 2) + 49.99

            new Order(1007L, client3, LocalDateTime.of(2024, 1, 25, 15, 10), OrderStatus.COMPLETED,
                    Arrays.asList(
                            new OrderItem(product1, 1, new BigDecimal("999.99")),
                            new OrderItem(product2, 1, new BigDecimal("2499.99")),
                            new OrderItem(product4, 2, new BigDecimal("79.99"))
                    ), new BigDecimal("4549.96")) // 999.99 + 2499.99 + (79.99 * 2)
    );

    public List<Order> getClientOrderData() {
        return orders;
    }
}
