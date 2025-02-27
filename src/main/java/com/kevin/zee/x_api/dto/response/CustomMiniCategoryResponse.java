package com.kevin.zee.x_api.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CustomMiniCategoryResponse {
    private Long id;                    // 自定义分类的ID
    private Long parentCategoryId;      // 父级分类(子类)的ID
    private String parentCategoryName;  // 父级分类(子类)的名称
    private String name;                // 自定义分类的名称
    private String description;         // 自定义分类的描述
    private LocalDateTime createdAt;    // 创建时间
    private LocalDateTime updatedAt;    // 最后更新时间
}
