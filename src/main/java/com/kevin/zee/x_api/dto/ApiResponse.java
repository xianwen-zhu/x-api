package com.kevin.zee.x_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;   // 状态码
    private String msg; // 提示信息
    private T data;     // 返回数据
}