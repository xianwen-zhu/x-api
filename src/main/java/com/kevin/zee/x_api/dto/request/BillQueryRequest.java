package com.kevin.zee.x_api.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BillQueryRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private Long mainCategoryId;
    private Long subCategoryId;
    private Long miniCategoryId;
    private Integer page = 0;
    private Integer size = 10;
}