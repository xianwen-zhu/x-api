package com.kevin.zee.x_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 关联用户

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type; // 收入 or 支出

    @Column(nullable = false)
    private BigDecimal amount; // 账单金额

    @Column(nullable = false)
    private String category; // 分类

    private String note; // 备注

    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now(); // 交易时间

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}