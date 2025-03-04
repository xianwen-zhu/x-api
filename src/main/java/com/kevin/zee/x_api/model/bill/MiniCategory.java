package com.kevin.zee.x_api.model.bill;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "mini_categories")
@Data
@ToString(exclude = "subCategory")
public class MiniCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;
}