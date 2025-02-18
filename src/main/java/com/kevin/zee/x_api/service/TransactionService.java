package com.kevin.zee.x_api.service;

import com.kevin.zee.x_api.model.Transaction;
import com.kevin.zee.x_api.repository.TransactionRepository;
import com.kevin.zee.x_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    // **1️⃣ 获取用户的所有交易**
    public List<Transaction> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    // **2️⃣ 创建交易**
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // **3️⃣ 删除交易**
    public void deleteTransaction(Long transactionId, Long userId) {
        // 1️⃣ 先查找交易
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("交易不存在"));

        // 2️⃣ 确保当前用户只能删除自己的交易
        if (!transaction.getUser().getId().equals(userId)) {
            throw new RuntimeException("权限不足，无法删除他人的交易");
        }

        // 3️⃣ 执行删除
        transactionRepository.delete(transaction);
    }
}