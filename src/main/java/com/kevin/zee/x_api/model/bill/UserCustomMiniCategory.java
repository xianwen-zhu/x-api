package com.kevin.zee.x_api.model.bill;

import com.kevin.zee.x_api.model.user.User;
import jakarta.persistence.*;
        import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_custom_mini_categories")
@Data
public class UserCustomMiniCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory parentCategory;

    private String name;
    private String description;
    private Boolean isActive = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}