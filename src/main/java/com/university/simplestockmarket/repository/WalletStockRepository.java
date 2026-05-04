package com.university.simplestockmarket.repository;


import com.university.simplestockmarket.model.WalletStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletStockRepository extends JpaRepository<WalletStock, Long> {
    Optional<WalletStock> findByWalletIdAndStockName(String walletId, String stockName);
    List<WalletStock> findAllByWalletId(String walletId);
}