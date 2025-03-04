package com.kevin.zee.x_api.model.bill;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "sub_categories")
@Data
@ToString(exclude = {"mainCategory", "miniCategories"})
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "main_category_id")
    private MainCategory mainCategory;

    @OneToMany(mappedBy = "subCategory")
    private List<MiniCategory> miniCategories;
}