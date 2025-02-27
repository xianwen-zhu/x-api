package com.kevin.zee.x_api.service.impl;

import com.kevin.zee.x_api.dto.request.CustomMiniCategoryCreateRequest;
import com.kevin.zee.x_api.dto.request.CustomMiniCategoryUpdateRequest;
import com.kevin.zee.x_api.dto.response.CustomMiniCategoryResponse;
import com.kevin.zee.x_api.exception.BusinessException;
import com.kevin.zee.x_api.model.bill.SubCategory;
import com.kevin.zee.x_api.model.bill.UserCustomMiniCategory;
import com.kevin.zee.x_api.model.user.User;
import com.kevin.zee.x_api.repository.UserRepository;
import com.kevin.zee.x_api.repository.bill.SubCategoryRepository;
import com.kevin.zee.x_api.repository.bill.UserCustomMiniCategoryRepository;
import com.kevin.zee.x_api.service.CustomMiniCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomMiniCategoryServiceImpl implements CustomMiniCategoryService {
    private final UserCustomMiniCategoryRepository customCategoryRepository;
    private final UserRepository userRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    @Transactional
    public CustomMiniCategoryResponse createCustomCategory(Long userId, CustomMiniCategoryCreateRequest request) {
        // 验证用户
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found"));

        // 验证父级分类
        SubCategory parentCategory = subCategoryRepository.findById(request.getParentCategoryId())
                .orElseThrow(() -> new BusinessException("Parent category not found"));

        // 检查是否存在同名分类
        if (customCategoryRepository.existsByUserIdAndParentCategoryIdAndNameAndIsActiveTrue(
                userId, parentCategory.getId(), request.getName())) {
            throw new BusinessException("Category with the same name already exists");
        }

        // 创建自定义分类
        UserCustomMiniCategory category = new UserCustomMiniCategory();
        category.setUser(user);
        category.setParentCategory(parentCategory);
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        UserCustomMiniCategory savedCategory = customCategoryRepository.save(category);
        return convertToResponse(savedCategory);
    }


    @Override
    @Transactional
    public CustomMiniCategoryResponse updateCustomCategory(
            Long userId, Long categoryId, CustomMiniCategoryUpdateRequest request) {
        UserCustomMiniCategory category = customCategoryRepository
                .findByIdAndUserIdAndIsActiveTrue(categoryId, userId)
                .orElseThrow(() -> new BusinessException("Custom category not found"));

        // 检查新名称是否与其他分类冲突
        if (!category.getName().equals(request.getName()) &&
                customCategoryRepository.existsByUserIdAndParentCategoryIdAndNameAndIsActiveTrue(
                        userId, category.getParentCategory().getId(), request.getName())) {
            throw new BusinessException("Category with the same name already exists");
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        UserCustomMiniCategory updatedCategory = customCategoryRepository.save(category);
        return convertToResponse(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCustomCategory(Long userId, Long categoryId) {
        UserCustomMiniCategory category = customCategoryRepository
                .findByIdAndUserIdAndIsActiveTrue(categoryId, userId)
                .orElseThrow(() -> new BusinessException("Custom category not found"));

        // 软删除
        category.setIsActive(false);
        customCategoryRepository.save(category);
    }

    @Override
    public List<CustomMiniCategoryResponse> getUserCustomCategories(Long userId) {
        return customCategoryRepository.findByUserIdAndIsActiveTrue(userId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomMiniCategoryResponse> getUserCustomCategoriesByParent(Long userId, Long parentCategoryId) {
        return customCategoryRepository
                .findByUserIdAndParentCategoryIdAndIsActiveTrue(userId, parentCategoryId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private CustomMiniCategoryResponse convertToResponse(UserCustomMiniCategory category) {
        CustomMiniCategoryResponse response = new CustomMiniCategoryResponse();
        response.setId(category.getId());
        response.setParentCategoryId(category.getParentCategory().getId());
        response.setParentCategoryName(category.getParentCategory().getName());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());
        return response;
    }
}
