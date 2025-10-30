package ru.sbp.gazii.podelki.streams.tasks.stockQote;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolutionStock {

    public static void main(String[] args) {
        List<StockQuote> data = new Data().quotes;
//        System.out.println(findMaxVolatilityWindows(data, 5));
        System.out.println(findConsecutiveDropsStream(data));
        calculateAutocorrelationWithoutCorrelation(data);
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
    static List<PriceDropPeriod> findConsecutiveDropsStream(List<StockQuote> quotes) {
        List<StockQuote> sortedQuotes = quotes.stream()
                .sorted(Comparator.comparing(StockQuote::getTimestamp))
                .collect(Collectors.toList());

        // Находим все максимальные периоды снижения
        List<List<StockQuote>> dropPeriods = findMaxDecreasingSequences(sortedQuotes);

        return dropPeriods.stream()
                .map(period -> {
                    BigDecimal startPrice = period.get(0).getPrice();
                    BigDecimal endPrice = period.get(period.size() - 1).getPrice();
                    BigDecimal totalDropPercent = startPrice.subtract(endPrice)
                            .divide(startPrice, 6, RoundingMode.HALF_UP)
                            .multiply(new BigDecimal("100"));

                    return new PriceDropPeriod(
                            period,
                            totalDropPercent,
                            period.get(0).getTimestamp(),
                            period.get(period.size() - 1).getTimestamp()
                    );
                })
                .filter(period -> period.getTotalDropPercent().compareTo(new BigDecimal("2")) > 0)
                .collect(Collectors.toList());
    }

    private static List<List<StockQuote>> findMaxDecreasingSequences(List<StockQuote> quotes) {
        List<List<StockQuote>> result = new ArrayList<>();
        int i = 0;

        // Проходим по всем котировкам
        while (i < quotes.size() - 1) {
            // Проверяем, началось ли снижение: текущая цена > следующей
            if (quotes.get(i).getPrice().compareTo(quotes.get(i + 1).getPrice()) > 0) {
                // Запоминаем начало периода снижения
                int start = i;

                // Двигаемся вперед, пока продолжается снижение
                while (i < quotes.size() - 1 &&
                        quotes.get(i).getPrice().compareTo(quotes.get(i + 1).getPrice()) > 0) {
                    i++;
                }

                // Теперь i указывает на последний элемент периода снижения
                int end = i;

                // Проверяем, что период содержит минимум 4 котировки (3+ падения)
                // start=0, end=3 → котировки [0,1,2,3] → 3 падения
                if (end - start >= 3) {
                    // Добавляем найденный период в результат
                    result.add(new ArrayList<>(quotes.subList(start, end + 1)));
                }
            } else {
                // Если нет снижения, переходим к следующей котировке
                i++;
            }
        }

        return result;
    }


    // Возвращает корреляцию между price(t) и price(t-1min) для каждого момента времени
    // Для точек, где нет данных с лагом 1 мин - пропускаем
    static List<AutocorrelationResult> calculateAutocorrelationWithoutCorrelation(List<StockQuote> quotes) {
        List<StockQuote> sorted = quotes.stream()
                .sorted(Comparator.comparing(StockQuote::getTimestamp))
                .collect(Collectors.toList());

        List<AutocorrelationResult> results = new ArrayList<>();
        Map<LocalDateTime, StockQuote> timeMap = sorted.stream()
                .collect(Collectors.toMap(StockQuote::getTimestamp, q -> q));

        for (int i = 0; i < sorted.size(); i++) {
            StockQuote current = sorted.get(i);
            LocalDateTime previousTime = current.getTimestamp().minusMinutes(1);

            BigDecimal laggedPrice = timeMap.containsKey(previousTime)
                    ? timeMap.get(previousTime).getPrice()
                    : null;

            if (laggedPrice != null) {
                results.add(new AutocorrelationResult(
                        current.getTimestamp(),
                        null,
                        current.getPrice(),
                        laggedPrice
                ));
            }
        }

        return results;
    }

}

