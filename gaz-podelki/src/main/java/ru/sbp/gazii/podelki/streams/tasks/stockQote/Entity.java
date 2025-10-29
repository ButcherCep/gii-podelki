package ru.sbp.gazii.podelki.streams.tasks.stockQote;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// Основная сущность котировки
class StockQuote {
    private String symbol;          // тикер акции (AAPL, GOOGL, etc)
    private LocalDateTime timestamp;
    private BigDecimal price;
    private BigDecimal volume;

    public StockQuote(String symbol, LocalDateTime timestamp, BigDecimal price, BigDecimal volume) {
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.price = price;
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
    // объем торгов

    // constructors, getters
}

// DTO для временных интервалов
class TimeWindow {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<StockQuote> quotes;

    public TimeWindow(LocalDateTime startTime, LocalDateTime endTime, List<StockQuote> quotes) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.quotes = quotes;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<StockQuote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<StockQuote> quotes) {
        this.quotes = quotes;
    }
    // constructors, getters
}

// DTO для волатильности
class VolatilityResult {
    private TimeWindow window;
    private BigDecimal volatility;  // стандартное отклонение цен
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal priceRange;  // max - min

    public VolatilityResult(TimeWindow window, BigDecimal volatility, BigDecimal minPrice, BigDecimal maxPrice, BigDecimal priceRange) {
        this.window = window;
        this.volatility = volatility;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.priceRange = priceRange;
    }

// constructors, getters
}

// DTO для падения цены
class PriceDropPeriod {
    private List<StockQuote> consecutiveDrops; // 3 подряд падающие котировки
    private BigDecimal totalDropPercent;       // суммарное падение за период
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public PriceDropPeriod(List<StockQuote> consecutiveDrops, BigDecimal totalDropPercent, LocalDateTime startTime, LocalDateTime endTime) {
        this.consecutiveDrops = consecutiveDrops;
        this.totalDropPercent = totalDropPercent;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public List<StockQuote> getConsecutiveDrops() {
        return consecutiveDrops;
    }

    public void setConsecutiveDrops(List<StockQuote> consecutiveDrops) {
        this.consecutiveDrops = consecutiveDrops;
    }

    public BigDecimal getTotalDropPercent() {
        return totalDropPercent;
    }

    public void setTotalDropPercent(BigDecimal totalDropPercent) {
        this.totalDropPercent = totalDropPercent;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    // constructors, getters
}

// DTO для автокорреляции
class AutocorrelationResult {
    private LocalDateTime timestamp;
    private BigDecimal correlation;  // коэффициент корреляции
    private BigDecimal price;
    private BigDecimal laggedPrice;  // цена с лагом 1 минута

    public AutocorrelationResult(LocalDateTime timestamp, BigDecimal correlation, BigDecimal price, BigDecimal laggedPrice) {
        this.timestamp = timestamp;
        this.correlation = correlation;
        this.price = price;
        this.laggedPrice = laggedPrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getCorrelation() {
        return correlation;
    }

    public void setCorrelation(BigDecimal correlation) {
        this.correlation = correlation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getLaggedPrice() {
        return laggedPrice;
    }

    public void setLaggedPrice(BigDecimal laggedPrice) {
        this.laggedPrice = laggedPrice;
    }
    // constructors, getters
}
class StockAnalysisResult {
    private List<VolatilityResult> topVolatilityWindows;
    private List<PriceDropPeriod> consecutiveDropPeriods;
    private List<AutocorrelationResult> autocorrelationResults;
    private LocalDateTime analysisPeriodStart;
    private LocalDateTime analysisPeriodEnd;

    public StockAnalysisResult(List<VolatilityResult> topVolatilityWindows, List<PriceDropPeriod> consecutiveDropPeriods, List<AutocorrelationResult> autocorrelationResults, LocalDateTime analysisPeriodStart, LocalDateTime analysisPeriodEnd) {
        this.topVolatilityWindows = topVolatilityWindows;
        this.consecutiveDropPeriods = consecutiveDropPeriods;
        this.autocorrelationResults = autocorrelationResults;
        this.analysisPeriodStart = analysisPeriodStart;
        this.analysisPeriodEnd = analysisPeriodEnd;
    }

    public List<VolatilityResult> getTopVolatilityWindows() {
        return topVolatilityWindows;
    }

    public void setTopVolatilityWindows(List<VolatilityResult> topVolatilityWindows) {
        this.topVolatilityWindows = topVolatilityWindows;
    }

    public List<PriceDropPeriod> getConsecutiveDropPeriods() {
        return consecutiveDropPeriods;
    }

    public void setConsecutiveDropPeriods(List<PriceDropPeriod> consecutiveDropPeriods) {
        this.consecutiveDropPeriods = consecutiveDropPeriods;
    }

    public List<AutocorrelationResult> getAutocorrelationResults() {
        return autocorrelationResults;
    }

    public void setAutocorrelationResults(List<AutocorrelationResult> autocorrelationResults) {
        this.autocorrelationResults = autocorrelationResults;
    }

    public LocalDateTime getAnalysisPeriodStart() {
        return analysisPeriodStart;
    }

    public void setAnalysisPeriodStart(LocalDateTime analysisPeriodStart) {
        this.analysisPeriodStart = analysisPeriodStart;
    }

    public LocalDateTime getAnalysisPeriodEnd() {
        return analysisPeriodEnd;
    }

    public void setAnalysisPeriodEnd(LocalDateTime analysisPeriodEnd) {
        this.analysisPeriodEnd = analysisPeriodEnd;
    }
    // constructors, getters
}