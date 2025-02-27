package com.kevin.zee.x_api.service.impl;


import com.kevin.zee.x_api.dto.request.BillCreateRequest;
import com.kevin.zee.x_api.dto.request.BillQueryRequest;
import com.kevin.zee.x_api.dto.response.BillResponse;
import com.kevin.zee.x_api.dto.response.StatisticsResponse;
import com.kevin.zee.x_api.exception.BusinessException;
import com.kevin.zee.x_api.model.bill.*;
import com.kevin.zee.x_api.model.user.User;
import com.kevin.zee.x_api.repository.*;
import com.kevin.zee.x_api.repository.bill.BillRepository;
import com.kevin.zee.x_api.repository.bill.MainCategoryRepository;
import com.kevin.zee.x_api.repository.bill.MiniCategoryRepository;
import com.kevin.zee.x_api.repository.bill.SubCategoryRepository;
import com.kevin.zee.x_api.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final MiniCategoryRepository miniCategoryRepository;

    @Override
    @Transactional
    public BillResponse createBill(Long userId, BillCreateRequest request) {
        // 1. 验证用户
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found"));

        // 2. 验证分类
        MainCategory mainCategory = mainCategoryRepository.findById(request.getMainCategoryId())
                .orElseThrow(() -> new BusinessException("Main category not found"));

        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(() -> new BusinessException("Sub category not found"));

        MiniCategory miniCategory = miniCategoryRepository.findById(request.getMiniCategoryId())
                .orElseThrow(() -> new BusinessException("Mini category not found"));

        // 3. 创建账单
        Bill bill = new Bill();
        bill.setUser(user);
        bill.setMainCategory(mainCategory);
        bill.setSubCategory(subCategory);
        bill.setMiniCategory(miniCategory);
        bill.setAmount(request.getAmount());
        bill.setType(TransactionType.valueOf(request.getType()));
        bill.setNote(request.getNote());
        bill.setTransactionTime(request.getTransactionTime());
        bill.setBillNumber(generateBillNumber());
        bill.setCreatedAt(LocalDateTime.now());

        // 4. 保存账单
        Bill savedBill = billRepository.save(bill);

        // 5. 转换为响应 DTO
        return convertToResponse(savedBill);
    }

    @Override
    public List<BillResponse> getUserBills(Long userId, BillQueryRequest request) {
        // 使用条件查询获取账单列表
        return billRepository.findByUserId(userId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StatisticsResponse getStatistics(Long userId, int year, int month) {
        List<Bill> bills = billRepository.findByUserAndYearAndMonth(userId, year, month);

        // 计算收入和支出总额
        BigDecimal totalIncome = bills.stream()
                .filter(bill -> bill.getType() == TransactionType.INCOME)
                .map(Bill::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = bills.stream()
                .filter(bill -> bill.getType() == TransactionType.EXPENSE)
                .map(Bill::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 计算收入类别统计
        List<StatisticsResponse.CategoryStatistics> incomeStats =
                calculateCategoryStatistics(
                        bills.stream()
                                .filter(bill -> bill.getType() == TransactionType.INCOME)
                                .collect(Collectors.toList()),
                        totalIncome
                );

        // 计算支出类别统计
        List<StatisticsResponse.CategoryStatistics> expenseStats =
                calculateCategoryStatistics(
                        bills.stream()
                                .filter(bill -> bill.getType() == TransactionType.EXPENSE)
                                .collect(Collectors.toList()),
                        totalExpense
                );

        StatisticsResponse response = new StatisticsResponse();
        response.setTotalIncome(totalIncome);
        response.setTotalExpense(totalExpense);
        response.setBalance(totalIncome.subtract(totalExpense));
        response.setIncomeStatistics(incomeStats);
        response.setExpenseStatistics(expenseStats);

        return response;
    }



    @Override
    @Transactional
    public void deleteBill(Long userId, String billNumber) {
        Bill bill = billRepository.findByBillNumber(billNumber)
                .orElseThrow(() -> new BusinessException("Bill not found"));

        if (!bill.getUser().getId().equals(userId)) {
            throw new BusinessException("Unauthorized to delete this bill");
        }

        billRepository.delete(bill);
    }

    @Override
    @Transactional
    public BillResponse updateBill(Long userId, String billNumber, BillCreateRequest request) {
        Bill bill = billRepository.findByBillNumber(billNumber)
                .orElseThrow(() -> new BusinessException("Bill not found"));

        if (!bill.getUser().getId().equals(userId)) {
            throw new BusinessException("Unauthorized to update this bill");
        }

        // 更新账单信息
        updateBillFromRequest(bill, request);

        Bill updatedBill = billRepository.save(bill);
        return convertToResponse(updatedBill);
    }

    // 私有辅助方法
    private String generateBillNumber() {
        return "BILL-" + System.currentTimeMillis();
    }

    private BillResponse convertToResponse(Bill bill) {
        BillResponse response = new BillResponse();
        response.setId(bill.getId());

        // 设置分类 ID
        response.setMainCategoryId(bill.getMainCategory().getId());
        response.setSubCategoryId(bill.getSubCategory().getId());
        response.setMiniCategoryId(bill.getMiniCategory().getId());

        response.setBillNumber(bill.getBillNumber());
        response.setMainCategoryName(bill.getMainCategory().getName());
        response.setSubCategoryName(bill.getSubCategory().getName());
        response.setMiniCategoryName(bill.getMiniCategory().getName());
        response.setAmount(bill.getAmount());
        response.setType(bill.getType().name());
        response.setTransactionTime(bill.getTransactionTime());
        response.setNote(bill.getNote());
        response.setCreatedAt(bill.getCreatedAt());
        return response;
    }

    private List<StatisticsResponse.CategoryStatistics> calculateCategoryStatistics(
            List<Bill> bills, BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            return new ArrayList<>();
        }

        return bills.stream()
                .collect(Collectors.groupingBy(
                        bill -> bill.getSubCategory().getName(),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Bill::getAmount,
                                BigDecimal::add
                        )
                ))
                .entrySet().stream()
                .map(entry -> {
                    StatisticsResponse.CategoryStatistics stats =
                            new StatisticsResponse.CategoryStatistics();
                    stats.setCategoryName(entry.getKey());
                    stats.setAmount(entry.getValue());

                    // 修改这里：计算百分比并取整
                    stats.setPercentage(
                            entry.getValue()
                                    .divide(total, 2, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal("100"))
                                    .setScale(0, RoundingMode.HALF_UP)  // 设置为0位小数，即整数
                    );
                    return stats;
                })
                .sorted((a, b) -> b.getAmount().compareTo(a.getAmount()))
                .collect(Collectors.toList());
    }

    private void updateBillFromRequest(Bill bill, BillCreateRequest request) {
        if (request.getMainCategoryId() != null) {
            MainCategory mainCategory = mainCategoryRepository
                    .findById(request.getMainCategoryId())
                    .orElseThrow(() -> new BusinessException("Main category not found"));
            bill.setMainCategory(mainCategory);
        }

        if (request.getSubCategoryId() != null) {
            SubCategory subCategory = subCategoryRepository
                    .findById(request.getSubCategoryId())
                    .orElseThrow(() -> new BusinessException("Sub category not found"));
            bill.setSubCategory(subCategory);
        }

        if (request.getMiniCategoryId() != null) {
            MiniCategory miniCategory = miniCategoryRepository
                    .findById(request.getMiniCategoryId())
                    .orElseThrow(() -> new BusinessException("Mini category not found"));
            bill.setMiniCategory(miniCategory);
        }

        if (request.getAmount() != null) {
            bill.setAmount(request.getAmount());
        }

        if (request.getType() != null) {
            bill.setType(TransactionType.valueOf(request.getType()));
        }

        if (request.getNote() != null) {
            bill.setNote(request.getNote());
        }

        if (request.getTransactionTime() != null) {
            bill.setTransactionTime(request.getTransactionTime());
        }
    }
}