package com.kevin.zee.x_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data   // 自动生成 Getter/Setter、toString、equals、hashCode
@NoArgsConstructor // 生成无参构造函数
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
}