package com.kevin.zee.x_api.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BillResponse {
    private Long id;
    private String billNumber;

    // 增加分类ID
    private Long mainCategoryId;
    private Long subCategoryId;
    private Long miniCategoryId;

    private String mainCategoryName;
    private String subCategoryName;
    private String miniCategoryName;
    private BigDecimal amount;
    private String type;
    private LocalDateTime transactionTime;
    private String note;
    private LocalDateTime createdAt;

    // 新增预算相关信息
    private BigDecimal budgetAmount;
    private BigDecimal budgetRemaining;
    private Boolean isOverBudget;
    private BigDecimal overBudgetAmount;
}