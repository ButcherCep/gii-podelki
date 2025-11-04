package ru.sbp.gazii.podelki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sbp.gazii.podelki.entity.Client;
import ru.sbp.gazii.podelki.entity.ClientCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    // === Базовые операции поиска ===

    /**
     * Поиск клиента по email (уникальное поле)
     */
    Optional<Client> findByEmail(String email);

    /**
     * Проверка существования клиента по email
     */
    boolean existsByEmail(String email);

    /**
     * Поиск клиентов по категории
     */
    List<Client> findByCategory(ClientCategory category);

    /**
     * Поиск клиентов по дате регистрации (после указанной даты)
     */
    List<Client> findByRegistrationDateAfter(LocalDateTime date);

    /**
     * Поиск клиентов по дате регистрации (до указанной даты)
     */
    List<Client> findByRegistrationDateBefore(LocalDateTime date);

    /**
     * Поиск клиентов по диапазону дат регистрации
     */
    List<Client> findByRegistrationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // === Комбинированные запросы ===

    /**
     * Поиск клиентов по категории и дате регистрации
     */
    List<Client> findByCategoryAndRegistrationDateAfter(
            ClientCategory category,
            LocalDateTime date
    );

    /**
     * Поиск клиентов по имени (частичное совпадение)
     */
    List<Client> findByNameContainingIgnoreCase(String namePart);

    /**
     * Поиск клиентов по email домену
     */
    @Query("SELECT c FROM Client c WHERE c.email LIKE %:domain%")
    List<Client> findByEmailDomain(@Param("domain") String domain);

    // === Пакетные операции ===

    /**
     * Поиск существующих email из списка
     */
    @Query("SELECT c.email FROM Client c WHERE c.email IN :emails")
    List<String> findExistingEmails(@Param("emails") List<String> emails);

    /**
     * Поиск клиентов по списку ID
     */
    List<Client> findByIdIn(List<Long> ids);

    /**
     * Поиск клиентов по списку email
     */
    List<Client> findByEmailIn(List<String> emails);

    // === Статистические запросы ===

    /**
     * Количество клиентов по категории
     */
    long countByCategory(ClientCategory category);

    /**
     * Общее количество клиентов
     */
    @Query("SELECT COUNT(c) FROM Client c")
    long getTotalClientsCount();

    /**
     * Статистика по клиентам по категориям
     */
    @Query("SELECT c.category, COUNT(c) FROM Client c GROUP BY c.category")
    List<Object[]> getClientsCountByCategory();

    /**
     * Количество новых клиентов за период
     */
    @Query("SELECT COUNT(c) FROM Client c WHERE c.registrationDate BETWEEN :startDate AND :endDate")
    long countNewClientsInPeriod(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // === Сортировка и пагинация ===

    /**
     * Поиск клиентов с сортировкой по имени
     */
    List<Client> findAllByOrderByNameAsc();

    /**
     * Поиск клиентов с сортировкой по дате регистрации (новые сначала)
     */
    List<Client> findAllByOrderByRegistrationDateDesc();


    // === Производительные запросы (только нужные поля) ===

    /**
     * Получение только email клиентов (проекция)
     */
    @Query("SELECT c.email FROM Client c")
    List<String> findAllEmails();

    /**
     * Получение клиентов с количеством их заказов
     */
    @Query("SELECT c, COUNT(o) FROM Client c LEFT JOIN c.orders o GROUP BY c")
    List<Object[]> findClientsWithOrderCount();

    // === Кастомные сложные запросы ===

    /**
     * Поиск клиентов без заказов
     */
    @Query("SELECT c FROM Client c WHERE c.id NOT IN (SELECT DISTINCT o.client.id FROM Order o)")
    List<Client> findClientsWithoutOrders();

    /**
     * Поиск клиентов с заказами за период
     */
    @Query("SELECT DISTINCT c FROM Client c JOIN c.orders o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<Client> findClientsWithOrdersInPeriod(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * Поиск VIP клиентов с высокой средней суммой заказа
     */
    @Query("SELECT c FROM Client c WHERE c.category = 'VIP' AND " +
            "c.id IN (SELECT o.client.id FROM Order o WHERE o.status = 'COMPLETED' " +
            "GROUP BY o.client.id HAVING AVG(o.totalAmount) > :minAverageAmount)")
    List<Client> findVipClientsWithHighAverageOrder(@Param("minAverageAmount") BigDecimal minAverageAmount);

    /**
     * Поиск "потерянных" клиентов (без заказов более N месяцев)
     */
    @Query("SELECT c FROM Client c WHERE c.id NOT IN (" +
            "SELECT DISTINCT o.client.id FROM Order o WHERE o.orderDate > :cutoffDate)")
    List<Client> findLostClients(@Param("cutoffDate") LocalDateTime cutoffDate);

    // === Bulk операции ===

    /**
     * Обновление категории клиентов по списку ID
     */
    @Modifying
    @Query("UPDATE Client c SET c.category = :category WHERE c.id IN :ids")
    int updateCategoryForClients(
            @Param("category") ClientCategory category,
            @Param("ids") List<Long> ids
    );

    /**
     * Удаление клиентов по email
     */
    @Modifying
    @Query("DELETE FROM Client c WHERE c.email = :email")
    int deleteByEmail(@Param("email") String email);

    /**
     * Удаление клиентов без заказов
     */
    @Modifying
    @Query("DELETE FROM Client c WHERE c.id NOT IN (SELECT DISTINCT o.client.id FROM Order o)")
    int deleteClientsWithoutOrders();
}
