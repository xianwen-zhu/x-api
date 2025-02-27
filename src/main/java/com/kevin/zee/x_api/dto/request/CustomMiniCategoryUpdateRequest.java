package com.kevin.zee.x_api.dto.request;
import lombok.Data;
@Data
public class CustomMiniCategoryUpdateRequest {
    private String name;        // 要更新的分类名称
    private String description; // 要更新的分类描述
}
