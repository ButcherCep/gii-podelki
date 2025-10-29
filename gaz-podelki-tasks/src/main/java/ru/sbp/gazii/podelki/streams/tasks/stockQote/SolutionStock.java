package ru.sbp.gazii.podelki.streams.tasks.stockQote;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolutionStock {

    public static void main(String[] args) {
        List<StockQuote> data = new Data().quotes;
        System.out.println(findMaxVolatilityWindows(data, 5));
        findConsecutiveDrops(data);
        calculateAutocorrelation(data);
    }

    // Возвращает топ-N 5-минутных интервалов с наибольшей волатильностью
    // Отсортировано по volatility DESC
    static List<VolatilityResult> findMaxVolatilityWindows(List<StockQuote> quotes, int topN) {
        List<StockQuote> list = quotes.stream()
                .sorted(Comparator.comparing(StockQuote::getTimestamp)).toList();

        List<VolatilityResult> slideList = sliding(list, 5).stream()    // возвращаем лист скользящих окон с 5 мин интервалом [1,2,3,4,5], [6,7,8,9,10] ../
                .map(window -> {
                    List<BigDecimal> prices = window.stream()
                            .map(StockQuote::getPrice)
                            .collect(Collectors.toList());

                    // Правильный расчет волатильности (стандартное отклонение)
                    BigDecimal mean = prices.stream()
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .divide(new BigDecimal("5"), 6, RoundingMode.HALF_UP);

                    BigDecimal sumSquared = prices.stream()
                            .map(price -> price.subtract(mean).pow(2))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal variance = sumSquared.divide(new BigDecimal("4"), 6, RoundingMode.HALF_UP);
                    BigDecimal volatility = BigDecimal.valueOf(Math.sqrt(variance.doubleValue()))
                            .setScale(4, RoundingMode.HALF_UP);

                    // Правильный расчет min/max из всех цен
                    BigDecimal minPrice = prices.stream()
                            .min(Comparator.naturalOrder())
                            .orElse(BigDecimal.ZERO);
                    BigDecimal maxPrice = prices.stream()
                            .max(Comparator.naturalOrder())
                            .orElse(BigDecimal.ZERO);
                    BigDecimal priceRange = maxPrice.subtract(minPrice);

                    return new VolatilityResult(
                            new TimeWindow(
                                    window.get(0).getTimestamp(),
                                    window.get(4).getTimestamp(),
                                    window
                            ),
                            volatility,
                            minPrice,
                            maxPrice,
                            priceRange
                    );
                })
                .collect(Collectors.toList());
        return slideList.stream().sorted(Comparator.comparing(VolatilityResult::getVolatility).reversed()).limit(topN).collect(Collectors.toList());
    }

    public static <T> List<List<T>> sliding(List<T> list, int windowSize) {
        return IntStream.range(0, list.size() - windowSize + 1)
                .mapToObj(i -> list.subList(i, i + windowSize))
                .collect(Collectors.toList());
    }


    // Возвращает все периоды, где было 3+ последовательных падения > 2%
    // Каждый период содержит список котировок и суммарное падение
    static List<PriceDropPeriod> findConsecutiveDrops(List<StockQuote> quotes) {
        List<PriceDropPeriod> results = new ArrayList<>();


        return results;
    }


    // Возвращает корреляцию между price(t) и price(t-1min) для каждого момента времени
    // Для точек, где нет данных с лагом 1 мин - пропускаем
    static List<AutocorrelationResult> calculateAutocorrelation(List<StockQuote> quotes) {
        List<AutocorrelationResult> results = new ArrayList<>();

        return results;
    }
}
