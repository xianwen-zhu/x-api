package com.kevin.zee.x_api.service;


import com.kevin.zee.x_api.dto.request.CustomMiniCategoryCreateRequest;
import com.kevin.zee.x_api.dto.request.CustomMiniCategoryUpdateRequest;
import com.kevin.zee.x_api.dto.response.CustomMiniCategoryResponse;
import java.util.List;


public interface CustomMiniCategoryService {
    CustomMiniCategoryResponse createCustomCategory(Long userId, CustomMiniCategoryCreateRequest request);
    CustomMiniCategoryResponse updateCustomCategory(Long userId, Long categoryId, CustomMiniCategoryUpdateRequest request);
    void deleteCustomCategory(Long userId, Long categoryId);
    List<CustomMiniCategoryResponse> getUserCustomCategories(Long userId);
    List<CustomMiniCategoryResponse> getUserCustomCategoriesByParent(Long userId, Long parentCategoryId);
}

