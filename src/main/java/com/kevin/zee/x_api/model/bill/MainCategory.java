package com.kevin.zee.x_api.model.bill;


import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "main_categories")
@Data
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