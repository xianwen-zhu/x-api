package com.kevin.zee.x_api.controller;

import com.kevin.zee.x_api.config.resolver.CurrentUser;
import com.kevin.zee.x_api.dto.ApiResponse;
import com.kevin.zee.x_api.dto.request.BillCreateRequest;
import com.kevin.zee.x_api.dto.request.BillQueryRequest;
import com.kevin.zee.x_api.dto.response.BillResponse;
import com.kevin.zee.x_api.dto.response.StatisticsResponse;
import com.kevin.zee.x_api.model.user.User;
import com.kevin.zee.x_api.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    /// 创建账单
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BillResponse>> createBill(
            @CurrentUser User user,
            @RequestBody BillCreateRequest request) {
        BillResponse response = billService.createBill(user.getId(), request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Bill created successfully", response));
    }

    /// 获取账单列表
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<BillResponse>>> getBills(
            @CurrentUser User user,
            BillQueryRequest request) {
        List<BillResponse> bills = billService.getUserBills(user.getId(), request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Bills retrieved successfully", bills));
    }

    /// 获取账单统计
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<StatisticsResponse>> getStatistics(
            @CurrentUser User user,
            @RequestParam int year,
            @RequestParam int month) {
        StatisticsResponse statistics = billService.getStatistics(user.getId(), year, month);
        return ResponseEntity.ok(new ApiResponse<>(200, "Statistics retrieved successfully", statistics));
    }

    /// 更新账单
    @PutMapping("/update/{billNumber}")
    public ResponseEntity<ApiResponse<BillResponse>> updateBill(
            @CurrentUser User user,
            @PathVariable String billNumber,
            @RequestBody BillCreateRequest request) {
        BillResponse response = billService.updateBill(user.getId(), billNumber, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Bill updated successfully", response));
    }

    /// 删除账单
    @DeleteMapping("/delete/{billNumber}")
    public ResponseEntity<ApiResponse<Void>> deleteBill(
            @CurrentUser User user,
            @PathVariable String billNumber) {
        billService.deleteBill(user.getId(), billNumber);
        return ResponseEntity.ok(new ApiResponse<>(200, "Bill deleted successfully", null));
    }
}