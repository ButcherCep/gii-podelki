package ru.sbp.gazii.podelki.streams.tasks.clientOrders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static streams.clientOrders.Utils.*;


public class Solution {
    public static void main(String[] args) {

        Data orders = new Data();
        List<Order> orderList = orders.getClientOrderData();
        /// Задача: Напиши обработку потока заказов, где нужно:
        /// Сгруппировать заказы по клиентам
        /// Для каждого клиента найти 3 самых дорогих заказа
        /// Посчитать общую сумму этих заказов для каждого клиента
        /// Отфильтровать только клиентов с общей суммой > 10000
        /// Вернуть Map<Client, List<Order>>
        Map<Client, List<Order>> result = orderList.stream()
                .collect(Collectors.groupingBy(Order::getClient))              // 1. Группируем по клиентам
                .entrySet().stream()                                           // 2. Работаем с Entry<Client, List<Order>>
                .filter(entry -> {
                    BigDecimal total = entry.getValue().stream()               // 3. Для каждого клиента:
                            .sorted(Comparator.comparing(Order::getTotalAmount).reversed()) // Сортируем по убыванию
                            .limit(3)                                              // Берем 3 самых дорогих
                            .map(Order::getTotalAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);            // Суммируем
                    return total.compareTo(new BigDecimal("10000")) > 0;      // 4. Фильтруем по сумме
                })
                .collect(Collectors.toMap(                                    // 5. Собираем обратно в Map
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .sorted(Comparator.comparing(Order::getTotalAmount).reversed())
                                .limit(3)
                                .collect(Collectors.toList())
                ));
        /// Задача: Найти клиентов с наибольшей покупательской активностью:
        /// Клиентов, которые делали заказы в 3+ разных месяца
        /// Средний чек которых выше среднего чека по всем клиентам
        /// И которые покупали товары из минимум 2 разных категорий
        /// Вернуть: Map<Client, PurchaseStats>
        Map<Client, PurchaseStats> result2 = orderList.stream()
                .collect(Collectors.groupingBy(Order::getClient))
                .entrySet().stream()
                .filter(entry -> {
                    List<Order> clientOrders = entry.getValue();

                    // 1. Проверка: 3+ разных месяца
                    long distinctMonths = clientOrders.stream()
                            .map(order -> order.getOrderDate().getMonth())
                            .distinct()
                            .count();
                    if (distinctMonths < 3) return false;

                    // 2. Проверка: минимум 2 категории товаров
                    Set<ProductCategory> categories = productCategories(clientOrders);
                    if (categories.size() < 2) return false;

                    // 3. Проверка: средний чек клиента > общего среднего чека
                    BigDecimal clientTotal = clientOrders.stream()
                            .map(Order::getTotalAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal clientAverage = clientTotal.divide(
                            BigDecimal.valueOf(clientOrders.size()), 2, RoundingMode.HALF_UP);

                    // Расчет общего среднего чека
                    BigDecimal overallTotal = orders.getClientOrderData().stream()
                            .map(Order::getTotalAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal overallAverage = overallTotal.divide(
                            BigDecimal.valueOf(orders.getClientOrderData().size()), 2, RoundingMode.HALF_UP);

                    return clientAverage.compareTo(overallAverage) > 0;
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> createPurchaseStats(entry.getValue())
                ));


        /// Задача 3: Выявление воронки продаж
        /// Задача: Построить анализ последовательности покупок:
        /// Для каждого клиента найти самый частый паттерн категорий товаров
        /// (например: ELECTRONICS → CLOTHING → BOOKS)
        /// Выявить клиентов, которые начали с дешёвых покупок (< 1000) и перешли к дорогим (> 5000)
        /// Найти "потерянных" клиентов - тех, кто не делал заказов в последние 3 месяца
        List<ClientFunnel> result3 = orderList.stream()
                .collect(Collectors.groupingBy(Order::getClient))
                .entrySet().stream()
                .map(entry -> new ClientFunnel(
                        entry.getKey(),
                        listPurchsePattern(entry),
                        isUpgrade(entry),
                        isLostClient(entry),
                        lastDate(entry))
                ).toList();
        /// Задача 4: Кросс-сеLLный анализ
        /// Задача: Найти корреляции между товарами:
        /// Пары товаров, которые часто покупают вместе (в одном заказе)
        /// Товары-"хабы" - те, которые покупают с разными другими товарами
        /// Рекомендации "клиенты, которые покупали X, также покупали Y"
        List<ProductAnalysis> result4 = orderList.stream()
                .collect(Collectors.groupingBy(Order::getClient))
                .entrySet().stream()
                .map(entry -> new ProductAnalysis(
                        frequentlyBoughtTogether(orderList),
                        hubProducts(orderList),
                        recommendations(orderList))
                ).toList();
    }

    // найти общее товары
    private static Map<Product, List<Product>> frequentlyBoughtTogether(List<Order> orders) {
        Map<Product, List<Product>> result = new HashMap<>();

        for (Order order : orders) {
            if (order.getItems().size() < 2) continue;

            List<Product> products = order.getItems().stream()
                    .map(OrderItem::getProduct)
                    .toList();

            for (Product product : products) {
                List<Product> others = products.stream()
                        .filter(p -> !p.equals(product))
                        .toList();

                result.merge(product, others, (oldList, newList) -> {
                    List<Product> merged = new ArrayList<>(oldList);
                    merged.addAll(newList);
                    return merged;
                });
            }
        }

        // Дополнительно можно посчитать частоты и отфильтровать
        return result;
    }



    private static Set<Product> hubProducts(List<Order> orders) {
        return null;
    }

    private static Map<Client, List<Product>> recommendations(List<Order> orders) {
        return null;
    }
}

