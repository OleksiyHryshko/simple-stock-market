package com.university.simplestockmarket.service;

import com.university.simplestockmarket.exception.BadRequestException;
import com.university.simplestockmarket.exception.ResourceNotFoundException;
import com.university.simplestockmarket.model.AuditLog;
import com.university.simplestockmarket.model.BankStock;
import com.university.simplestockmarket.model.WalletStock;
import com.university.simplestockmarket.repository.AuditLogRepository;
import com.university.simplestockmarket.repository.BankStockRepository;
import com.university.simplestockmarket.repository.WalletStockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TradeService {
    private final BankStockRepository bankRepo;
    private final WalletStockRepository walletRepo;
    private final AuditLogRepository logRepo;

    public TradeService(BankStockRepository bankRepo, WalletStockRepository walletRepo, AuditLogRepository logRepo) {
        this.bankRepo = bankRepo;
        this.walletRepo = walletRepo;
        this.logRepo = logRepo;
    }

    @Transactional
    public void executeTrade(String walletId, String stockName, String type) {
        BankStock bankStock = bankRepo.findByStockNameForUpdate(stockName)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found in bank"));

        if ("buy".equalsIgnoreCase(type)) {
            if (bankStock.getQuantity() <= 0) {
                throw new BadRequestException("No stock available in the bank");
            }
            bankStock.setQuantity(bankStock.getQuantity() - 1);

            WalletStock ws = walletRepo.findByWalletIdAndStockName(walletId, stockName)
                    .orElse(new WalletStock(walletId, stockName, 0));
            ws.setQuantity(ws.getQuantity() + 1);
            walletRepo.save(ws);
        } else if ("sell".equalsIgnoreCase(type)) {
            WalletStock ws = walletRepo.findByWalletIdAndStockName(walletId, stockName)
                    .orElseThrow(() -> new BadRequestException("No stock in wallet to sell"));

            if (ws.getQuantity() <= 0) {
                throw new BadRequestException("No stock in wallet to sell");
            }

            ws.setQuantity(ws.getQuantity() - 1);
            walletRepo.save(ws);
            bankStock.setQuantity(bankStock.getQuantity() + 1);
        } else {
            throw new BadRequestException("Invalid trade type");
        }

        bankRepo.save(bankStock);
        logRepo.save(new AuditLog(type, walletId, stockName));
    }
}

