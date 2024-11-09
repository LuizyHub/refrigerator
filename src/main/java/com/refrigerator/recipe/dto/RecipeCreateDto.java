package com.refrigerator.recipe.dto;
import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.member.entity.Member;
import com.refrigerator.recipe.entity.Recipe;
import com.refrigerator.recipe.entity.RecipeCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
public class RecipeCreateDto {

    @NotBlank
    private String name;

    private Long userId;

    private List<Long> categoryIds;

    // 사용자 ID를 기반으로 Recipe 객체 생성
    public Recipe toRecipe(Member member, Set<RecipeCategory> categories) {
        this.userId = member.getUserId();
        return new Recipe(name, categories, userId);
    }
}
