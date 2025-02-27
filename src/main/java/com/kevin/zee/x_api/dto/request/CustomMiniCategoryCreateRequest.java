package com.kevin.zee.x_api.dto.request;


import lombok.Data;
@Data
public class CustomMiniCategoryCreateRequest {
    private Long parentCategoryId;  // 父级分类ID
    private String name;
    private String description;
}
