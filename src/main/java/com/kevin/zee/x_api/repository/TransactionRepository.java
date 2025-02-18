package com.kevin.zee.x_api.repository;

import com.kevin.zee.x_api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // **查找当前用户的所有交易**
    List<Transaction> findByUserId(Long userId);
}