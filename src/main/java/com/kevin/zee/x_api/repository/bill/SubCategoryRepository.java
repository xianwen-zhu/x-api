package com.kevin.zee.x_api.repository.bill;

import com.kevin.zee.x_api.model.bill.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findByMainCategoryId(Long mainCategoryId);
}