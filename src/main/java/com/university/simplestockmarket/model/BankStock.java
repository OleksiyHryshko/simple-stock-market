package com.university.simplestockmarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BankStock {
    @Id
    private String stockName;
    private int quantity;

    public BankStock() {}

    public BankStock(String stockName, int quantity) {
        this.stockName = stockName;
        this.quantity = quantity;
    }

    public String getStockName() { return stockName; }
    public void setStockName(String stockName) { this.stockName = stockName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}