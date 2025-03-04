package com.kevin.zee.x_api.service.impl;


import com.kevin.zee.x_api.dto.response.CategoryResponse.*;
import com.kevin.zee.x_api.model.bill.MainCategory;
import com.kevin.zee.x_api.model.bill.MiniCategory;
import com.kevin.zee.x_api.model.bill.SubCategory;
import com.kevin.zee.x_api.model.bill.UserCustomMiniCategory;
import com.kevin.zee.x_api.repository.bill.MainCategoryRepository;
import com.kevin.zee.x_api.repository.bill.MiniCategoryRepository;
import com.kevin.zee.x_api.repository.bill.SubCategoryRepository;
import com.kevin.zee.x_api.repository.bill.UserCustomMiniCategoryRepository;
import com.kevin.zee.x_api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final MiniCategoryRepository miniCategoryRepository;
    private final UserCustomMiniCategoryRepository userCustomMiniCategoryRepository;

    @Override
    public List<MainCategoryResponse> getAllMainCategories(Long userId) {
        return mainCategoryRepository.findAll().stream()
                .map(category -> convertToMainCategoryResponse(category, userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubCategoryResponse> getSubCategories(Long userId,Long mainCategoryId) {
        return subCategoryRepository.findByMainCategoryId(mainCategoryId).stream()
                .map(category -> convertToSubCategoryResponse(category, userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<MiniCategoryResponse> getMiniCategories(Long userId, Long subCategoryId) {
        // 1. 获取系统预设的三级分类
        List<MiniCategoryResponse> systemMiniCategories = miniCategoryRepository
                .findBySubCategoryId(subCategoryId).stream()
                .map(this::convertToMiniCategoryResponse)
                .toList();

        // 2. 获取用户自定义的三级分类
        List<MiniCategoryResponse> customMiniCategories = userCustomMiniCategoryRepository
                .findByUserIdAndParentCategoryIdAndIsActiveTrue(userId, subCategoryId).stream()
                .map(this::convertCustomToMiniCategoryResponse)
                .toList();

        // 3. 合并两个列表
        List<MiniCategoryResponse> allMiniCategories = new ArrayList<>();
        allMiniCategories.addAll(systemMiniCategories);
        allMiniCategories.addAll(customMiniCategories);

        return allMiniCategories;
    }

    private MainCategoryResponse convertToMainCategoryResponse(MainCategory category,Long userId) {
        MainCategoryResponse response = new MainCategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setSubCategories(category.getSubCategories().stream()
                .map(subCategory -> convertToSubCategoryResponse(subCategory, userId))
                .collect(Collectors.toList()));

        return response;
    }

    private SubCategoryResponse convertToSubCategoryResponse(SubCategory category,Long userId) {
        SubCategoryResponse response = new SubCategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setMainCategoryId(category.getMainCategory().getId());
        response.setDescription(category.getDescription());
        List<MiniCategoryResponse> systemMiniCategories = category.getMiniCategories().stream()
                .map(this::convertToMiniCategoryResponse)
                .collect(Collectors.toList());

        List<MiniCategoryResponse> customMiniCategories = userCustomMiniCategoryRepository
                .findByUserIdAndParentCategoryIdAndIsActiveTrue(userId, category.getId()).stream()
                .map(this::convertCustomToMiniCategoryResponse)
                .collect(Collectors.toList());

        List<MiniCategoryResponse> allMiniCategories = new ArrayList<>();
        allMiniCategories.addAll(systemMiniCategories);
        allMiniCategories.addAll(customMiniCategories);

        response.setMiniCategories(allMiniCategories);
        return response;
    }

    private MiniCategoryResponse convertToMiniCategoryResponse(MiniCategory category) {
        MiniCategoryResponse response = new MiniCategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setMainCategoryId(category.getSubCategory().getMainCategory().getId());  // 设置大类ID
        response.setSubCategoryId(category.getSubCategory().getId());  // 设置父级分类ID
        response.setIsCustom(false);
        return response;
    }

    private MiniCategoryResponse convertCustomToMiniCategoryResponse(UserCustomMiniCategory customCategory) {
        MiniCategoryResponse response = new MiniCategoryResponse();
        response.setId(customCategory.getId());
        response.setName(customCategory.getName());
        response.setDescription(customCategory.getDescription());
        response.setSubCategoryId(customCategory.getParentCategory().getId());
        response.setMainCategoryId(customCategory.getParentCategory().getMainCategory().getId());
        response.setIsCustom(true);  // 标记为用户自定义分类
        return response;
    }
}