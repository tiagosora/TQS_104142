package org.stocks;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private List<Stock> stocks;
    private IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarket){
        this.stocks = new ArrayList<Stock>();
        this.stockmarket = stockmarket;
    }
    public void addStock(Stock stock){
        this.stocks.add(stock);
    }
    public double getTotalValue(){
        double value = 0.0;
        for (Stock stock : stocks){
            value = value + stockmarket.lookUpPrice(stock.getLabel());
        }
        return value;
    }
}
