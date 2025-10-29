package ru.sbp.gazii.podelki.streams.tasks.clientOrders;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
public class MockData {

    // Создаем тестовых клиентов
    Client mockClient1 = Client.builder()
            .id(1L)
            .name("Иван Иванов")
            .email("ivan@test.ru")
            .registrationDate(LocalDate.of(2023, 1, 15))
            .category(ClientCategory.VIP)
            .build();

    Client mockClient2 = Client.builder()
            .id(2L)
            .name("Петр Петров")
            .email("petr@test.ru")
            .registrationDate(LocalDate.of(2023, 2, 20))
            .category(ClientCategory.STANDARD)
            .build();

    Client mockClient3 = Client.builder()
            .id(3L)
            .name("Сидор Сидоров")
            .email("sidor@test.ru")
            .registrationDate(LocalDate.of(2023, 3, 10))
            .category(ClientCategory.PREMIUM)
            .build();

    Client mockClient4 = Client.builder()
            .id(4L)
            .name("Мария Иванова")
            .email("maria@test.ru")
            .registrationDate(LocalDate.of(2023, 4, 5))
            .category(ClientCategory.STANDARD)
            .build();


    // Создаем продукты
    Product product1 = new Product(1L, "iPhone", ProductCategory.ELECTRONICS, new BigDecimal("1000"));
    Product product2 = new Product(2L, "MacBook", ProductCategory.ELECTRONICS, new BigDecimal("2000"));
    Product product3 = new Product(3L, "Футболка", ProductCategory.CLOTHING, new BigDecimal("50"));
    Product product4 = new Product(4L, "Джинсы", ProductCategory.CLOTHING, new BigDecimal("100"));
    Product product5 = new Product(5L, "Книга", ProductCategory.BOOKS, new BigDecimal("30"));
    Product product6 = new Product(6L, "Журнал", ProductCategory.BOOKS, new BigDecimal("15"));
    Product product7 = new Product(7L, "Шоколад", ProductCategory.FOOD, new BigDecimal("5"));
    Product product8 = new Product(8L, "Кофе", ProductCategory.FOOD, new BigDecimal("10"));


    // Создаем тестовые заказы вручную (не через Data)
    @Getter
    List<Order> mockOrderList = List.of(
            Order.builder().id(1L).client(mockClient1).totalAmount(new BigDecimal("5000")).build(),
            Order.builder().id(2L).client(mockClient1).totalAmount(new BigDecimal("4000")).build(),
            Order.builder().id(3L).client(mockClient1).totalAmount(new BigDecimal("3000")).build(),
            Order.builder().id(4L).client(mockClient1).totalAmount(new BigDecimal("1000")).build(), // не войдет в топ-3
            Order.builder().id(5L).client(mockClient2).totalAmount(new BigDecimal("4000")).build(),
            Order.builder().id(6L).client(mockClient2).totalAmount(new BigDecimal("3000")).build(),
            Order.builder().id(7L).client(mockClient2).totalAmount(new BigDecimal("2000")).build(), // сумма = 9000 (<10000)
            Order.builder().id(8L).client(mockClient3).totalAmount(new BigDecimal("15000")).build(), // большой заказ
            Order.builder().id(9L).client(mockClient3).totalAmount(new BigDecimal("2000")).build(),
            Order.builder().id(10L).client(mockClient3).totalAmount(new BigDecimal("1000")).build()
    );

    @Getter
    List<Order> mockOrderList2 = List.of(
            // Клиент 1: проходит все условия (3+ месяцев, 2+ категорий, средний чек > общего)
            Order.builder().id(1L).client(mockClient1)
                    .orderDate(LocalDateTime.of(2024, 1, 15, 10, 30))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product1, 1, new BigDecimal("1000")),
                            new OrderItem(product5, 2, new BigDecimal("30"))
                    ))
                    .totalAmount(new BigDecimal("1060")) // 1000 + (30*2)
                    .build(),

            Order.builder().id(2L).client(mockClient1)
                    .orderDate(LocalDateTime.of(2024, 2, 20, 14, 15))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product2, 1, new BigDecimal("2000"))
                    ))
                    .totalAmount(new BigDecimal("2000"))
                    .build(),

            Order.builder().id(3L).client(mockClient1)
                    .orderDate(LocalDateTime.of(2024, 3, 10, 9, 0))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product3, 3, new BigDecimal("50")),
                            new OrderItem(product4, 1, new BigDecimal("100"))
                    ))
                    .totalAmount(new BigDecimal("250")) // (50*3) + 100
                    .build(),

            Order.builder().id(4L).client(mockClient1)
                    .orderDate(LocalDateTime.of(2024, 4, 5, 16, 45))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product6, 5, new BigDecimal("15"))
                    ))
                    .totalAmount(new BigDecimal("75")) // 15*5
                    .build(),

            // Клиент 2: НЕ проходит - только 2 разных месяца
            Order.builder().id(5L).client(mockClient2)
                    .orderDate(LocalDateTime.of(2024, 1, 10, 11, 20))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product1, 1, new BigDecimal("1000")),
                            new OrderItem(product3, 2, new BigDecimal("50"))
                    ))
                    .totalAmount(new BigDecimal("1100")) // 1000 + (50*2)
                    .build(),

            Order.builder().id(6L).client(mockClient2)
                    .orderDate(LocalDateTime.of(2024, 1, 25, 13, 10)) // тот же месяц
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product2, 1, new BigDecimal("2000"))
                    ))
                    .totalAmount(new BigDecimal("2000"))
                    .build(),

            Order.builder().id(7L).client(mockClient2)
                    .orderDate(LocalDateTime.of(2024, 2, 15, 15, 30))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product4, 2, new BigDecimal("100"))
                    ))
                    .totalAmount(new BigDecimal("200")) // 100*2
                    .build(),

            // Клиент 3: НЕ проходит - только 1 категория товаров
            Order.builder().id(8L).client(mockClient3)
                    .orderDate(LocalDateTime.of(2024, 1, 8, 8, 0))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product5, 3, new BigDecimal("30")),
                            new OrderItem(product6, 2, new BigDecimal("15"))
                    ))
                    .totalAmount(new BigDecimal("120")) // (30*3) + (15*2)
                    .build(),

            Order.builder().id(9L).client(mockClient3)
                    .orderDate(LocalDateTime.of(2024, 2, 12, 12, 0))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product5, 1, new BigDecimal("30"))
                    ))
                    .totalAmount(new BigDecimal("30"))
                    .build(),

            Order.builder().id(10L).client(mockClient3)
                    .orderDate(LocalDateTime.of(2024, 3, 18, 17, 45))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product6, 4, new BigDecimal("15"))
                    ))
                    .totalAmount(new BigDecimal("60")) // 15*4
                    .build(),

            // Клиент 4: НЕ проходит - средний чек < общего среднего
            Order.builder().id(11L).client(mockClient4)
                    .orderDate(LocalDateTime.of(2024, 1, 5, 9, 15))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product3, 1, new BigDecimal("50")),
                            new OrderItem(product5, 1, new BigDecimal("30"))
                    ))
                    .totalAmount(new BigDecimal("80")) // 50 + 30
                    .build(),

            Order.builder().id(12L).client(mockClient4)
                    .orderDate(LocalDateTime.of(2024, 2, 10, 10, 30))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product4, 1, new BigDecimal("100"))
                    ))
                    .totalAmount(new BigDecimal("100"))
                    .build(),

            Order.builder().id(13L).client(mockClient4)
                    .orderDate(LocalDateTime.of(2024, 3, 15, 14, 20))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product6, 2, new BigDecimal("15"))
                    ))
                    .totalAmount(new BigDecimal("30")) // 15*2
                    .build(),

            // Добавляем еще заказов для расчета общего среднего чека
            Order.builder().id(14L).client(mockClient1)
                    .orderDate(LocalDateTime.of(2024, 5, 1, 8, 0))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product7, 10, new BigDecimal("5")),
                            new OrderItem(product8, 5, new BigDecimal("10"))
                    ))
                    .totalAmount(new BigDecimal("100")) // (5*10) + (10*5)
                    .build(),

            Order.builder().id(15L).client(mockClient2)
                    .orderDate(LocalDateTime.of(2024, 3, 20, 16, 0))
                    .status(OrderStatus.COMPLETED)
                    .items(List.of(
                            new OrderItem(product3, 5, new BigDecimal("50"))
                    ))
                    .totalAmount(new BigDecimal("250")) // 50*5
                    .build()
    );

}
