package com.kevin.zee.x_api.service;


import com.kevin.zee.x_api.dto.response.CategoryResponse.MainCategoryResponse;
import com.kevin.zee.x_api.dto.response.CategoryResponse.SubCategoryResponse;
import com.kevin.zee.x_api.dto.response.CategoryResponse.MiniCategoryResponse;
import java.util.List;

public interface CategoryService {
    List<MainCategoryResponse> getAllMainCategories();
    List<SubCategoryResponse> getSubCategories(Long mainCategoryId);
    List<MiniCategoryResponse> getMiniCategories(Long subCategoryId);
}