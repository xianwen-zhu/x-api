package com.kevin.zee.x_api.service;


import com.kevin.zee.x_api.dto.request.BillCreateRequest;
import com.kevin.zee.x_api.dto.request.BillQueryRequest;
import com.kevin.zee.x_api.dto.response.BillResponse;
import com.kevin.zee.x_api.dto.response.StatisticsResponse;
import java.util.List;

public interface BillService {
    // 创建账单
    BillResponse createBill(Long userId, BillCreateRequest request);

    // 获取用户账单列表
    List<BillResponse> getUserBills(Long userId, BillQueryRequest request);

    // 获取账单统计
    StatisticsResponse getStatistics(Long userId, int year, int month);

    // 删除账单
    void deleteBill(Long userId, String billNumber);

    // 更新账单
    BillResponse updateBill(Long userId, String billNumber, BillCreateRequest request);
}