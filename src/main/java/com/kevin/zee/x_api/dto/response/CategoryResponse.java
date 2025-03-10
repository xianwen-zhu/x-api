package com.kevin.zee.x_api.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class CategoryResponse {
    // 大类
    @Data
    public static class MainCategoryResponse {
        private Long id;
        private String name;
        private String description;
        private List<SubCategoryResponse> subCategories;
    }

    // 子类
    @Data
    public static class SubCategoryResponse {
        private Long id;
        private String name;
        private Long mainCategoryId;  // 添加父级分类ID
        private String description;
        private List<MiniCategoryResponse> miniCategories;
    }

    // 小类
    @Data
    public static class MiniCategoryResponse {
        private Long id;
        private String name;
        private String description;
        private Long mainCategoryId;  // 添加大类ID
        private Long subCategoryId;   // 添加父级分类ID
        private Boolean isCustom = false;

    }
}