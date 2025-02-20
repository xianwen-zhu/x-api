package com.kevin.zee.x_api.repository.bill;

import com.kevin.zee.x_api.model.bill.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByUserId(Long userId);

    @Query("SELECT b FROM Bill b WHERE b.user.id = :userId " +
            "AND YEAR(b.transactionTime) = :year " +
            "AND MONTH(b.transactionTime) = :month")
    List<Bill> findByUserAndYearAndMonth(Long userId, int year, int month);


    // 添加新方法
    Optional<Bill> findByBillNumber(String billNumber);

    // 根据用户ID和账单号一起查询，可以添加：
    Optional<Bill> findByUserIdAndBillNumber(Long userId, String billNumber);
}