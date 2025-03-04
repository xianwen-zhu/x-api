package com.kevin.zee.x_api.model.bill;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "main_categories")
@Data
@ToString(exclude = "subCategories")
public class MainCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "mainCategory")
    private List<SubCategory> subCategories;
}