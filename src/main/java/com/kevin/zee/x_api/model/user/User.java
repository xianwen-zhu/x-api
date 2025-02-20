package com.kevin.zee.x_api.model.user;

import com.kevin.zee.x_api.model.bill.Bill;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data   // 自动生成 Getter/Setter、toString、equals、hashCode
@NoArgsConstructor // 生成无参构造函数
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;
    private String phone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<Bill> bills;
}