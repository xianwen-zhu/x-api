package com.kevin.zee.x_api.controller;


import com.kevin.zee.x_api.config.resolver.CurrentUser;
import com.kevin.zee.x_api.dto.ApiResponse;
import com.kevin.zee.x_api.dto.request.CustomMiniCategoryCreateRequest;
import com.kevin.zee.x_api.dto.request.CustomMiniCategoryUpdateRequest;
import com.kevin.zee.x_api.dto.response.CustomMiniCategoryResponse;
import com.kevin.zee.x_api.model.user.User;
import com.kevin.zee.x_api.service.CustomMiniCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/custom-categories")
@RequiredArgsConstructor
public class CustomMiniCategoryController {
    private final CustomMiniCategoryService customCategoryService;

    /// 创建自定义分类
    @PostMapping
    public ResponseEntity<ApiResponse<CustomMiniCategoryResponse>> createCustomCategory(
            @CurrentUser User user,
            @RequestBody CustomMiniCategoryCreateRequest request) {
        CustomMiniCategoryResponse response = customCategoryService.createCustomCategory(user.getId(), request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Custom category created successfully", response));
    }
    /// 更新自定义分类
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CustomMiniCategoryResponse>> updateCustomCategory(
            @CurrentUser User user,
            @PathVariable Long categoryId,
            @RequestBody CustomMiniCategoryUpdateRequest request) {
        CustomMiniCategoryResponse response = customCategoryService.updateCustomCategory(
                user.getId(), categoryId, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Custom category updated successfully", response));
    }
    /// 删除自定义分类
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomCategory(
            @CurrentUser User user,
            @PathVariable Long categoryId) {
        customCategoryService.deleteCustomCategory(user.getId(), categoryId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Custom category deleted successfully", null));
    }
    /// 获取自定义分类列表
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomMiniCategoryResponse>>> getUserCustomCategories(
            @CurrentUser User user) {
        List<CustomMiniCategoryResponse> categories = customCategoryService.getUserCustomCategories(user.getId());
        return ResponseEntity.ok(new ApiResponse<>(200, "Custom categories retrieved successfully", categories));
    }
    /// 获取自定义分类列表
    @GetMapping("/parent/{parentCategoryId}")
    public ResponseEntity<ApiResponse<List<CustomMiniCategoryResponse>>> getUserCustomCategoriesByParent(
            @CurrentUser User user,
            @PathVariable Long parentCategoryId) {
        List<CustomMiniCategoryResponse> categories = customCategoryService
                .getUserCustomCategoriesByParent(user.getId(), parentCategoryId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Custom categories retrieved successfully", categories));
    }
}