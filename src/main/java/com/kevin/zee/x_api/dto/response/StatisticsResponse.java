package com.kevin.zee.x_api.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class StatisticsResponse {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;
    private List<CategoryStatistics> incomeStatistics;  // 收入统计
    private List<CategoryStatistics> expenseStatistics; // 支出统计

    @Data
    public static class CategoryStatistics {
        private String categoryName;
        private BigDecimal amount;
        private BigDecimal percentage;
    }
}