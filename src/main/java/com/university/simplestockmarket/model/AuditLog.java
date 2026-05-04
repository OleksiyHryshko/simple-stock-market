package com.university.simplestockmarket.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // "buy" or "sell"
    private String walletId;
    private String stockName;

    public AuditLog() {}

    public AuditLog(String type, String walletId, String stockName) {
        this.type = type;
        this.walletId = walletId;
        this.stockName = stockName;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public String getWalletId() { return walletId; }
    public String getStockName() { return stockName; }
}