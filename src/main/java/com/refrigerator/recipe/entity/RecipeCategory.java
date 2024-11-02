package com.refrigerator.recipe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Recipe_Category")
@Getter
@Setter
@NoArgsConstructor
public class RecipeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId; // category_id

    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
