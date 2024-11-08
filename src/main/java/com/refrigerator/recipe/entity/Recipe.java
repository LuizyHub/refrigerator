package com.refrigerator.recipe.entity;

//import com.refrigerator.itemCategory.entity.ItemCategory;
import com.refrigerator.member.entity.Member;
//import com.refrigerator.state.entity.State;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;
}

//name, category, state