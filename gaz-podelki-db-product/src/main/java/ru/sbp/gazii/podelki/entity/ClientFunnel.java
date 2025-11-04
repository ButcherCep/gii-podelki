package ru.sbp.gazii.podelki.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
class ClientFunnel {
    Client client; // клиент
    List<ProductCategory> purchasePattern; //паттерн заказов
    boolean isUpgradeSpender; // перешёл от дешёвых к дорогим
    boolean isLostClient;     // нет заказов 3+ месяца
    LocalDateTime lastOrderDate; // дата последнего заказа
}



