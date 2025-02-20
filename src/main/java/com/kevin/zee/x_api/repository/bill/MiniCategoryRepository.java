package com.kevin.zee.x_api.repository.bill;


import com.kevin.zee.x_api.model.bill.MiniCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MiniCategoryRepository extends JpaRepository<MiniCategory, Long> {
    List<MiniCategory> findBySubCategoryId(Long subCategoryId);
}