package ru.sbp.gazii.podelki.streams.tasks.stockQote;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
// Основная сущность котировки
class StockQuote {
    private String symbol;          // тикер акции (AAPL, GOOGL, etc)
    private LocalDateTime timestamp;
    private BigDecimal price;
    private BigDecimal volume;    // объем торгов
}
@Data
@AllArgsConstructor
// DTO для временных интервалов
class TimeWindow {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<StockQuote> quotes;
}
@Data
@AllArgsConstructor
// DTO для волатильности
class VolatilityResult {
    private TimeWindow window;
    private BigDecimal volatility;  // стандартное отклонение цен
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal priceRange;  // max - min

// constructors, getters
}
@Data
@AllArgsConstructor
// DTO для падения цены
class PriceDropPeriod {
    private List<StockQuote> consecutiveDrops; // 3 подряд падающие котировки
    private BigDecimal totalDropPercent;       // суммарное падение за период
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    // constructors, getters
}
@Data
@AllArgsConstructor
// DTO для автокорреляции
class AutocorrelationResult {
    private LocalDateTime timestamp;
    private BigDecimal correlation;  // коэффициент корреляции
    private BigDecimal price;
    private BigDecimal laggedPrice;  // цена с лагом 1 минута
}
@Data
@AllArgsConstructor
class StockAnalysisResult {
    private List<VolatilityResult> topVolatilityWindows;
    private List<PriceDropPeriod> consecutiveDropPeriods;
    private List<AutocorrelationResult> autocorrelationResults;
    private LocalDateTime analysisPeriodStart;
    private LocalDateTime analysisPeriodEnd;
    // constructors, getters
}