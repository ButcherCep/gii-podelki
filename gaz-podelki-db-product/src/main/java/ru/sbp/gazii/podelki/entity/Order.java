package ru.sbp.gazii.podelki.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


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



