package com.university.simplestockmarket.controller;

import com.university.simplestockmarket.dto.BankData;
import com.university.simplestockmarket.dto.LogItem;
import com.university.simplestockmarket.dto.LogResponse;
import com.university.simplestockmarket.dto.StockItem;
import com.university.simplestockmarket.dto.TradeRequest;
import com.university.simplestockmarket.dto.WalletResponse;
import com.university.simplestockmarket.model.BankStock;
import com.university.simplestockmarket.repository.AuditLogRepository;
import com.university.simplestockmarket.repository.BankStockRepository;
import com.university.simplestockmarket.repository.WalletStockRepository;
import com.university.simplestockmarket.service.TradeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MarketController {

    private final TradeService tradeService;
    private final BankStockRepository bankRepo;
    private final WalletStockRepository walletRepo;
    private final AuditLogRepository logRepo;

    public MarketController(TradeService tradeService, BankStockRepository bankRepo, WalletStockRepository walletRepo, AuditLogRepository logRepo) {
        this.tradeService = tradeService;
        this.bankRepo = bankRepo;
        this.walletRepo = walletRepo;
        this.logRepo = logRepo;
    }

    @PostMapping("/wallets/{wallet_id}/stocks/{stock_name}")
    public void trade(@PathVariable("wallet_id") String walletId,
                      @PathVariable("stock_name") String stockName,
                      @RequestBody TradeRequest request) {
        tradeService.executeTrade(walletId, stockName, request.type());
    }

    @GetMapping("/wallets/{wallet_id}")
    public WalletResponse getWallet(@PathVariable("wallet_id") String walletId) {
        List<StockItem> stocks = walletRepo.findAllByWalletId(walletId).stream()
                .map(ws -> new StockItem(ws.getStockName(), ws.getQuantity()))
                .collect(Collectors.toList());
        return new WalletResponse(walletId, stocks);
    }

    @GetMapping("/wallets/{wallet_id}/stocks/{stock_name}")
    public int getWalletStockQuantity(@PathVariable("wallet_id") String walletId,
                                      @PathVariable("stock_name") String stockName) {
        return walletRepo.findByWalletIdAndStockName(walletId, stockName)
                .map(ws -> ws.getQuantity())
                .orElse(0);
    }

    @GetMapping("/stocks")
    public BankData getBankState() {
        List<StockItem> stocks = bankRepo.findAll().stream()
                .map(bs -> new StockItem(bs.getStockName(), bs.getQuantity()))
                .collect(Collectors.toList());
        return new BankData(stocks);
    }

    @PostMapping("/stocks")
    public void setBankState(@RequestBody BankData request) {
        bankRepo.deleteAll();
        List<BankStock> newStocks = request.stocks().stream()
                .map(item -> new BankStock(item.name(), item.quantity()))
                .collect(Collectors.toList());
        bankRepo.saveAll(newStocks);
    }

    @GetMapping("/log")
    public LogResponse getLog() {
        List<LogItem> logs = logRepo.findAll().stream()
                .map(log -> new LogItem(log.getType(), log.getWalletId(), log.getStockName()))
                .collect(Collectors.toList());
        return new LogResponse(logs);
    }

    @PostMapping("/chaos")
    public void triggerChaos() {
        System.out.println("Chaos Monkey triggered. System exiting...");
        System.exit(1);
    }
}
