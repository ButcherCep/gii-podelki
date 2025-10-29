package ru.sbp.gazii.podelki.streams.tasks.stockQote;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Data {

    List<StockQuote> quotes = Arrays.asList(
            // 9:30 - 9:34 (восходящий тренд)
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 30), new BigDecimal("150.00"), new BigDecimal("100000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 31), new BigDecimal("150.25"), new BigDecimal("120000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 32), new BigDecimal("150.75"), new BigDecimal("95000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 33), new BigDecimal("151.20"), new BigDecimal("110000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 34), new BigDecimal("151.50"), new BigDecimal("105000")),

            // 9:35 - 9:39 (высокая волатильность)
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 35), new BigDecimal("152.00"), new BigDecimal("130000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 36), new BigDecimal("149.80"), new BigDecimal("200000")), // резкое падение
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 37), new BigDecimal("153.50"), new BigDecimal("180000")), // резкий рост
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 38), new BigDecimal("148.90"), new BigDecimal("220000")), // снова падение
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 39), new BigDecimal("152.80"), new BigDecimal("160000")), // восстановление

            // 9:40 - 9:44 (низкая волатильность)
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 40), new BigDecimal("152.70"), new BigDecimal("90000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 41), new BigDecimal("152.65"), new BigDecimal("85000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 42), new BigDecimal("152.80"), new BigDecimal("92000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 43), new BigDecimal("152.60"), new BigDecimal("88000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 44), new BigDecimal("152.75"), new BigDecimal("95000")),

            // 9:45 - 9:49 (три падения подряд > 2%)
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 45), new BigDecimal("152.70"), new BigDecimal("100000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 46), new BigDecimal("149.50"), new BigDecimal("150000")), // -2.1%
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 47), new BigDecimal("146.20"), new BigDecimal("170000")), // -2.2%
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 48), new BigDecimal("142.90"), new BigDecimal("190000")), // -2.3%
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 49), new BigDecimal("145.00"), new BigDecimal("120000")), // восстановление

            // 9:50 - 9:54 (еще одна высокая волатильность)
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 50), new BigDecimal("147.50"), new BigDecimal("140000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 51), new BigDecimal("144.80"), new BigDecimal("160000")), // падение
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 52), new BigDecimal("149.20"), new BigDecimal("180000")), // рост
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 53), new BigDecimal("142.50"), new BigDecimal("200000")), // падение
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 9, 54), new BigDecimal("148.80"), new BigDecimal("170000")), // рост

            // 10:00+ (для автокорреляции с лагом 1 минута)
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 10, 0), new BigDecimal("148.50"), new BigDecimal("110000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 10, 1), new BigDecimal("148.30"), new BigDecimal("115000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 10, 2), new BigDecimal("148.80"), new BigDecimal("105000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 10, 3), new BigDecimal("148.20"), new BigDecimal("120000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 15, 10, 4), new BigDecimal("148.70"), new BigDecimal("100000")),

            // Данные для другого дня (проверка границ дней)
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 16, 9, 30), new BigDecimal("155.00"), new BigDecimal("130000")),
            new StockQuote("AAPL", LocalDateTime.of(2024, 1, 16, 9, 31), new BigDecimal("154.80"), new BigDecimal("125000"))
    );
}
