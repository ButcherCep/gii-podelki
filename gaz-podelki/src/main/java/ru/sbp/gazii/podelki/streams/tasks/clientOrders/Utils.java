package ru.sbp.gazii.podelki.streams.tasks.clientOrders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    protected static boolean isLostClient(Map.Entry<Client, List<Order>> entry) {
        Optional<LocalDateTime> lastOrderDate = entry.getValue().stream()
                .map(Order::getOrderDate)
                .max(LocalDateTime::compareTo);

        return lastOrderDate.map(date ->
                date.isBefore(LocalDateTime.now().minusMonths(3))
        ).orElse(false);
    }

    protected static boolean isUpgrade(Map.Entry<Client, List<Order>> entry) {
        List<Order> sortedOrders = entry.getValue().stream()
                .sorted(Comparator.comparing(Order::getOrderDate))
                .toList();

        Order first = sortedOrders.get(0);
        Order last = sortedOrders.get(sortedOrders.size() - 1);

        BigDecimal cheapThreshold = new BigDecimal("1000");
        BigDecimal expensiveThreshold = new BigDecimal("5000");

        return first.getTotalAmount().compareTo(cheapThreshold) < 0 &&
                last.getTotalAmount().compareTo(expensiveThreshold) > 0;
    }

    protected static LocalDateTime lastDate(Map.Entry<Client, List<Order>> entry) {
        return entry.getValue().stream()
                .map(Order::getOrderDate)
                .max(LocalDateTime::compareTo)
                .orElse(null);
    }

    protected static List<ProductCategory> listPurchsePattern(Map.Entry<Client, List<Order>> entry) {
        return entry.getValue().stream()
                .sorted(Comparator.comparing(Order::getOrderDate))
                .flatMap(order -> order.getItems().stream())
                .map(orderItem -> orderItem.getProduct().getCategory())
                .distinct()
                .toList();
    }


    protected static PurchaseStats createPurchaseStats(List<Order> orders) {
        BigDecimal totalSpent = orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageOrderValue = totalSpent.divide(
                BigDecimal.valueOf(orders.size()), 2, RoundingMode.HALF_UP);

        return new PurchaseStats(
                orders.size(),
                averageOrderValue,
                productCategories(orders),
                activeMonth(orders)
        );
    }

    protected static Set<Month> activeMonth(List<Order> value) {
        return value.stream()
                .map(order -> order.getOrderDate().getMonth())
                .collect(Collectors.toSet());
    }

    protected static Set<ProductCategory> productCategories(List<Order> value) {
        return value.stream()
                .flatMap(order -> order.getItems().stream())
                .map(orderItem -> orderItem.getProduct().getCategory())
                .collect(Collectors.toSet());
    }
}
