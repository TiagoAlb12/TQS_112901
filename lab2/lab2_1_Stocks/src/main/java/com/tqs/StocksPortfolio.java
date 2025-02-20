package com.tqs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StocksPortfolio {

        private IStockmarketService stockMarket;
        private List<Stock> stocks;

        public StocksPortfolio(IStockmarketService stockMarket) {
            this.stockMarket = stockMarket;
            this.stocks = new ArrayList<>();
        }

        public void addStock(Stock stock) {
            stocks.add(stock);
        }

        public double totalValue() {
            return stocks.stream()
                    .mapToDouble(stock -> stock.getQuantity() * stockMarket.lookUpPrice(stock.getName()))
                    .sum();
        }

        // alinea e)
        public List<Stock> mostValuableStocks(int topN) {
            return stocks.stream()
                    .sorted((s1, s2) -> Double.compare(
                            s2.getQuantity() * stockMarket.lookUpPrice(s2.getName()),
                            s1.getQuantity() * stockMarket.lookUpPrice(s1.getName())
                    ))
                    .limit(topN)
                    .collect(Collectors.toList());
        }
}
