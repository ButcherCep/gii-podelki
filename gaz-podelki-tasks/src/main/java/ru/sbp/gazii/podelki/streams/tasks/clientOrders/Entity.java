package ru.sbp.gazii.podelki.streams.tasks.clientOrders;


import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Client {
    private Long id;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                ", category=" + category +
                '}';
    }

    private String name;
    private String email;
    private LocalDate registrationDate;
    private ClientCategory category;

    public Client(Long id, String name, String email, LocalDate registrationDate, ClientCategory category) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registrationDate = registrationDate;
        this.category = category;
    }
}

class Order implements Comparable<Order> {
    private Long id;
    private Client client;
    private LocalDateTime orderDate;
    private OrderStatus status; // PENDING, PROCESSING, COMPLETED, CANCELLED
    private List<OrderItem> items;
    private BigDecimal totalAmount;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                '}';
    }

    public Order(Long id, Client client, LocalDateTime orderDate, OrderStatus status, List<OrderItem> items, BigDecimal totalAmount) {
        this.id = id;
        this.client = client;
        this.orderDate = orderDate;
        this.status = status;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public int compareTo(@NotNull Order order) {
        return this.orderDate.compareTo(order.orderDate);
    }
}

class OrderItem {
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;

    @Override
    public String toString() {
        return "OrderItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderItem(Product product, Integer quantity, BigDecimal unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // constructors, getters
    public BigDecimal getItemTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}

class Product {
    private Long id;
    private String name;
    private ProductCategory category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", basePrice=" + basePrice +
                '}';
    }

    private BigDecimal basePrice;

    public Product(Long id, String name, ProductCategory category, BigDecimal basePrice) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.basePrice = basePrice;
    }

    // constructors, getters
}
class PurchaseStats {
    long totalOrders; //Общее количество заказов;
    BigDecimal averageOrderValue; //Средняя сумма заказа;
    Set<ProductCategory> purchasedCategories; //категории заказов
    Set<Month> activeMonths; // активные месяца заказов

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public BigDecimal getAverageOrderValue() {
        return averageOrderValue;
    }

    public void setAverageOrderValue(BigDecimal averageOrderValue) {
        this.averageOrderValue = averageOrderValue;
    }

    public Set<ProductCategory> getPurchasedCategories() {
        return purchasedCategories;
    }

    public void setPurchasedCategories(Set<ProductCategory> purchasedCategories) {
        this.purchasedCategories = purchasedCategories;
    }

    public Set<Month> getActiveMonths() {
        return activeMonths;
    }

    public void setActiveMonths(Set<Month> activeMonths) {
        this.activeMonths = activeMonths;
    }

    public PurchaseStats(long totalOrders, BigDecimal averageOrderValue, Set<ProductCategory> purchasedCategories, Set<Month> activeMonths) {
        this.totalOrders = totalOrders;
        this.averageOrderValue = averageOrderValue;
        this.purchasedCategories = purchasedCategories;
        this.activeMonths = activeMonths;
    }
}

class ClientFunnel {
    Client client; // клиент
    List<ProductCategory> purchasePattern; //паттерн заказов
    boolean isUpgradeSpender; // перешёл от дешёвых к дорогим
    boolean isLostClient;     // нет заказов 3+ месяца
    LocalDateTime lastOrderDate; // дата последнего заказа

    public ClientFunnel(Client client, List<ProductCategory> purchasePattern, boolean isUpgradeSpender, boolean isLostClient, LocalDateTime lastOrderDate) {
        this.client = client;
        this.purchasePattern = purchasePattern;
        this.isUpgradeSpender = isUpgradeSpender;
        this.isLostClient = isLostClient;
        this.lastOrderDate = lastOrderDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<ProductCategory> getPurchasePattern() {
        return purchasePattern;
    }

    public void setPurchasePattern(List<ProductCategory> purchasePattern) {
        this.purchasePattern = purchasePattern;
    }

    public boolean isUpgradeSpender() {
        return isUpgradeSpender;
    }

    public void setUpgradeSpender(boolean upgradeSpender) {
        isUpgradeSpender = upgradeSpender;
    }

    public boolean isLostClient() {
        return isLostClient;
    }

    public void setLostClient(boolean lostClient) {
        isLostClient = lostClient;
    }

    public LocalDateTime getLastOrderDate() {
        return lastOrderDate;
    }

    public void setLastOrderDate(LocalDateTime lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }// последний заказ
}
class ProductAnalysis {
    public ProductAnalysis(Map<Product, List<Product>> frequentlyBoughtTogether, Set<Product> hubProducts, Map<Client, List<Product>> recommendations) {
        this.frequentlyBoughtTogether = frequentlyBoughtTogether;
        this.hubProducts = hubProducts;
        this.recommendations = recommendations;
    }
    Map<Product, List<Product>> frequentlyBoughtTogether; // Product -> список часто покупаемых с ним
    Set<Product> hubProducts; // товары, которые покупают с 3+ другими товарами
    Map<Client, List<Product>> recommendations; // персональные рекомендации
    public Map<Product, List<Product>> getFrequentlyBoughtTogether() {
        return frequentlyBoughtTogether;
    }

    public void setFrequentlyBoughtTogether(Map<Product, List<Product>> frequentlyBoughtTogether) {
        this.frequentlyBoughtTogether = frequentlyBoughtTogether;
    }

    public Set<Product> getHubProducts() {
        return hubProducts;
    }

    public void setHubProducts(Set<Product> hubProducts) {
        this.hubProducts = hubProducts;
    }

    public Map<Client, List<Product>> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Map<Client, List<Product>> recommendations) {
        this.recommendations = recommendations;
    }


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