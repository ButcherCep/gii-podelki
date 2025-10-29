package ru.sbp.gazii.podelki.streams.tasks.stockQote;

import lombok.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
// Основная сущность котировки
class StockQuote {
    private String symbol;          // тикер акции (AAPL, GOOGL, etc)
    private LocalDateTime timestamp; //время тикета
    private BigDecimal price;   //стоимость
    private BigDecimal volume;    // объем торгов

    @Override
    public String toString() {
        return "\nStockQuote: " +symbol +": "+"$=" + price +
                "    volume=" + volume;
    }
}
@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
// DTO для временных интервалов
class TimeWindow {
    @Override
    public String toString() {
        return  "TimeWindow:   \n" +
                "           startTime=" + startTime +"\n"+
                "           endTime=" + endTime +"\n"+
                "           quotes=" + quotes;
    }

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<StockQuote> quotes;  //пара котировок
}
@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
// DTO для волатильности
class VolatilityResult {
    private TimeWindow window;
    private BigDecimal volatility;  // стандартное отклонение цен
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal priceRange;  // max - min

    @Override
    public String toString() {
//        String graph = ASCIIGraph
        return  "\n______________________________________________________________________________________________________________________________________" +
                "\n  VolatilityResult:   \n" +
                "window=" + window +"\n"+
                "            volatility=" + volatility +"\n"+
                "\n______________________________________________________________________________________________________________________________________\n"+
                "            minPrice=" + minPrice +"\n"+
                "            maxPrice=" + maxPrice +"\n"+
                "            priceRange=" + priceRange +
                "\n______________________________________________________________________________________________________________________________________\n";
    }

// constructors, getters
}
@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
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
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
// DTO для автокорреляции
class AutocorrelationResult {
    private LocalDateTime timestamp;
    private BigDecimal correlation;  // коэффициент корреляции
    private BigDecimal price;
    private BigDecimal laggedPrice;  // цена с лагом 1 минута
}
@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
class StockAnalysisResult {
    private List<VolatilityResult> topVolatilityWindows;
    private List<PriceDropPeriod> consecutiveDropPeriods;
    private List<AutocorrelationResult> autocorrelationResults;
    private LocalDateTime analysisPeriodStart;
    private LocalDateTime analysisPeriodEnd;
    // constructors, getters
}