package com.university.simplestockmarket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LogItem(String type,
                      @JsonProperty("wallet_id") String walletId,
                      @JsonProperty("stock_name") String stockName) {
}

