package com.kevin.zee.x_api.controller;


import com.kevin.zee.x_api.config.resolver.CurrentUser;
import com.kevin.zee.x_api.dto.ApiResponse;
import com.kevin.zee.x_api.dto.response.CategoryResponse.MainCategoryResponse;
import com.kevin.zee.x_api.dto.response.CategoryResponse.SubCategoryResponse;
import com.kevin.zee.x_api.dto.response.CategoryResponse.MiniCategoryResponse;
import com.kevin.zee.x_api.model.user.User;
import com.kevin.zee.x_api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /// 获取所有大类
    @GetMapping("/main")
    public ResponseEntity<ApiResponse<List<MainCategoryResponse>>> getMainCategories(@CurrentUser User user) {
        List<MainCategoryResponse> categories = categoryService.getAllMainCategories(user.getId());
        return ResponseEntity.ok(new ApiResponse<>(200, "Categories retrieved successfully", categories));
    }

    /// 获取子类列表
    @GetMapping("/sub/{mainCategoryId}")
    public ResponseEntity<ApiResponse<List<SubCategoryResponse>>> getSubCategories(
            @CurrentUser User user,
            @PathVariable Long mainCategoryId) {
        List<SubCategoryResponse> categories = categoryService.getSubCategories(user.getId(), mainCategoryId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Categories retrieved successfully", categories));
    }

   /// 获取小类列表
    @GetMapping("/mini/{subCategoryId}")
    public ResponseEntity<ApiResponse<List<MiniCategoryResponse>>> getMiniCategories(
            @CurrentUser User user,
            @PathVariable Long subCategoryId) {
        List<MiniCategoryResponse> categories = categoryService.getMiniCategories(user.getId(),subCategoryId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Categories retrieved successfully", categories));
    }
}