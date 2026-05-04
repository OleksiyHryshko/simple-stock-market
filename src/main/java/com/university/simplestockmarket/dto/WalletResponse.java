package com.university.simplestockmarket.dto;

import java.util.List;

public record WalletResponse(String id, List<StockItem> stocks) {
}

