package com.university.simplestockmarket.repository;



import com.university.simplestockmarket.model.BankStock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankStockRepository extends JpaRepository<BankStock, String> {

    // The crucial lock for HA concurrency
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM BankStock b WHERE b.stockName = :stockName")
    Optional<BankStock> findByStockNameForUpdate(String stockName);
}
