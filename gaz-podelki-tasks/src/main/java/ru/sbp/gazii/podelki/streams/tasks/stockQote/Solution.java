package ru.sbp.gazii.podelki.streams.tasks.stockQote;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        List<StockQuote> data = new Data().quotes;
        findMaxVolatilityWindows(data, 5);
        findConsecutiveDrops(data);
        calculateAutocorrelation(data);
    }

    // Возвращает топ-N 5-минутных интервалов с наибольшей волатильностью
    // Отсортировано по volatility DESC
    static List<VolatilityResult> findMaxVolatilityWindows(List<StockQuote> quotes, int topN) {
        List<VolatilityResult> results = new ArrayList<>();

        return results;
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
