package com.kevin.zee.x_api.repository.bill; /// 自定义小类别仓库

import com.kevin.zee.x_api.model.bill.UserCustomMiniCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCustomMiniCategoryRepository extends JpaRepository<UserCustomMiniCategory, Long> {
    List<UserCustomMiniCategory> findByUserIdAndIsActiveTrue(Long userId);

    List<UserCustomMiniCategory> findByUserIdAndParentCategoryIdAndIsActiveTrue(Long userId, Long parentCategoryId);

    Optional<UserCustomMiniCategory> findByIdAndUserIdAndIsActiveTrue(Long id, Long userId);

    boolean existsByUserIdAndParentCategoryIdAndNameAndIsActiveTrue(
            Long userId, Long parentCategoryId, String name);
}