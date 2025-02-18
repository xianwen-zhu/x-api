package com.kevin.zee.x_api.controller;

import com.kevin.zee.x_api.dto.ApiResponse;
import com.kevin.zee.x_api.model.Transaction;
import com.kevin.zee.x_api.model.User;
import com.kevin.zee.x_api.repository.UserRepository;
import com.kevin.zee.x_api.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final UserRepository userRepository;

    // 获取当前用户的交易记录
    @PostMapping("/list")
    public ResponseEntity<ApiResponse<?>> getTransactions(Authentication authentication) {
        try {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<Transaction> transactions = transactionService.getTransactionsByUser(user.getId());
            return ResponseEntity.ok(new ApiResponse<>(200, "Transactions retrieved successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, "Error retrieving transactions: " + e.getMessage(), null));
        }
    }

    // 添加交易
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<?>> addTransaction(
            Authentication authentication,
            @RequestBody Transaction transaction) {
        try {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            transaction.setUser(user);
            Transaction savedTransaction = transactionService.createTransaction(transaction);
            return ResponseEntity.ok(new ApiResponse<>(200, "Transaction added successfully", savedTransaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, "Error adding transaction: " + e.getMessage(), null));
        }
    }

    // 删除交易
    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<?>> deleteTransaction(
            Authentication authentication,
            @RequestBody Long transactionId) {
        try {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            transactionService.deleteTransaction(transactionId, user.getId());
            return ResponseEntity.ok(new ApiResponse<>(200, "Transaction deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, "Error deleting transaction: " + e.getMessage(), null));
        }
    }
}