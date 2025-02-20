package com.kevin.zee.x_api.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BillCreateRequest {
    private Long mainCategoryId;
    private Long subCategoryId;
    private Long miniCategoryId;
    private BigDecimal amount;
    private String type;  // "INCOME" or "EXPENSE"
    private String note;
    private LocalDateTime transactionTime;
}