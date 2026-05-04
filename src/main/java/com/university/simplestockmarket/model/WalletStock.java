package com.university.simplestockmarket.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WalletStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String walletId;
    private String stockName;
    private int quantity;

    public WalletStock() {}

    public WalletStock(String walletId, String stockName, int quantity) {
        this.walletId = walletId;
        this.stockName = stockName;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public String getWalletId() { return walletId; }
    public void setWalletId(String walletId) { this.walletId = walletId; }
    public String getStockName() { return stockName; }
    public void setStockName(String stockName) { this.stockName = stockName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
