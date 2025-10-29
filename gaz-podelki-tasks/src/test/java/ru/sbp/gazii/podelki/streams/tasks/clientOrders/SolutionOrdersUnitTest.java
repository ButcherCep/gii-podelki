package ru.sbp.gazii.podelki.streams.tasks.clientOrders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SolutionOrdersUnitTest {

    private SolutionMethods methods;
    private MockData mockData;
    private List<Order> orderList, orderList2;
    private Client client1, client2, client3;

    @BeforeEach
    void setUp() {
        methods = new SolutionMethods();
        mockData = new MockData();
        client1 = mockData.mockClient1;
        client2 = mockData.mockClient2;
        client3 = mockData.mockClient3;
        orderList = mockData.getMockOrderList();
        orderList2 = mockData.getMockOrderList2();
    }
    @Test
    void testTop3MostExpensiveOrdersPerClient() {
        // When
        Map<Client, List<Order>> result = methods.processTask1(orderList);

        // Then - проверяем что берутся именно 3 самых дорогих заказа
        List<Order> client1Orders = result.get(client1);
        assertEquals(3, client1Orders.size());

        // Проверяем порядок (по убыванию стоимости)
        assertEquals(new BigDecimal("5000"), client1Orders.get(0).getTotalAmount());
        assertEquals(new BigDecimal("4000"), client1Orders.get(1).getTotalAmount());
        assertEquals(new BigDecimal("3000"), client1Orders.get(2).getTotalAmount());

        // Проверяем что заказ с 1000 не попал в результат
        assertTrue(client1Orders.stream()
                .noneMatch(order -> order.getTotalAmount().equals(new BigDecimal("1000"))));
    }

    @Test
    void testFindMaxPurchaseStats() {

        Map<Client, PurchaseStats> result = methods.processTask2(orderList2);
        assertEquals(2, result.size());
        assertEquals(result.values().stream().filter(stats->stats.getPurchasedCategories().size()>1).toList().size(), result.size());
    }
}
