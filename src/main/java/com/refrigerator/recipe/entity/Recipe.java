package com.refrigerator.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Long userId;

    @ManyToMany
    @JoinTable(
            name = "r_rcategory",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )

    private Set<RecipeCategory> categories = new HashSet<>();

    //생성자 (toRecipe 사용)
    public Recipe(String name, Set<RecipeCategory> categories, Long userId) {
        this.name = name;
        this.categories = categories;
        this.userId = userId;
    }
    //기본 생성자 (JPA 사용)
    public Recipe() {}
}
