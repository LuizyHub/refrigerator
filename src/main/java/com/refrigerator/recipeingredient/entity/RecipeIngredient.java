package com.refrigerator.recipeingredient.entity;

import com.refrigerator.item.entity.Item;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.unit.entity.Unit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "recipe_ingredient")
public class RecipeIngredient{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne()
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne()
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @Column(nullable = false)
    private Double amount;
}
