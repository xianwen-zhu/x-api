package com.kevin.zee.x_api.service.impl;


import com.kevin.zee.x_api.dto.response.CategoryResponse.*;
import com.kevin.zee.x_api.model.bill.MainCategory;
import com.kevin.zee.x_api.model.bill.MiniCategory;
import com.kevin.zee.x_api.model.bill.SubCategory;
import com.kevin.zee.x_api.repository.bill.MainCategoryRepository;
import com.kevin.zee.x_api.repository.bill.MiniCategoryRepository;
import com.kevin.zee.x_api.repository.bill.SubCategoryRepository;
import com.kevin.zee.x_api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final MiniCategoryRepository miniCategoryRepository;

    @Override
    public List<MainCategoryResponse> getAllMainCategories() {
        return mainCategoryRepository.findAll().stream()
                .map(this::convertToMainCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubCategoryResponse> getSubCategories(Long mainCategoryId) {
        return subCategoryRepository.findByMainCategoryId(mainCategoryId).stream()
                .map(this::convertToSubCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MiniCategoryResponse> getMiniCategories(Long subCategoryId) {
        return miniCategoryRepository.findBySubCategoryId(subCategoryId).stream()
                .map(this::convertToMiniCategoryResponse)
                .collect(Collectors.toList());
    }

    private MainCategoryResponse convertToMainCategoryResponse(MainCategory category) {
        MainCategoryResponse response = new MainCategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }

    private SubCategoryResponse convertToSubCategoryResponse(SubCategory category) {
        SubCategoryResponse response = new SubCategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setMainCategoryId(category.getMainCategory().getId());
        response.setDescription(category.getDescription());
        return response;
    }

    private MiniCategoryResponse convertToMiniCategoryResponse(MiniCategory category) {
        MiniCategoryResponse response = new MiniCategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setMainCategoryId(category.getSubCategory().getMainCategory().getId());  // 设置大类ID
        response.setSubCategoryId(category.getSubCategory().getId());  // 设置父级分类ID
        return response;
    }
}