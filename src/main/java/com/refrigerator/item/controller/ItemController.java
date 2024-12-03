package com.refrigerator.item.controller;

import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.item.dto.ItemCreateDto;
import com.refrigerator.item.entity.Item;
import com.refrigerator.item.entity.ItemCategory;
import com.refrigerator.item.service.ItemCategoryService;
import com.refrigerator.item.service.ItemService;
import com.refrigerator.member.entity.Member;
import com.refrigerator.state.entity.State;
import com.refrigerator.state.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemCategoryService itemCategoryService;
    private final StateService stateService;
    // 모든 Item 리스트 페이지
    @GetMapping
    public String getItems(Model model) {
        List<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "items";  // items.html 뷰로 이동
    }

    // 새 Item 생성 폼 페이지
    @GetMapping("/new")
    public String createItemForm(
            @CurrentMember Member member, // 로그인이 필요한 부분은 이 부분 추가
            @ModelAttribute("item") ItemCreateDto itemCreateDto,
            Model model
    ) {
        List<ItemCategory> categories = itemCategoryService.getAllCategories();
        List<State> states = stateService.getAllStates();
        model.addAttribute("categories", categories);
        model.addAttribute("states", states);
        return "items/new";  // items/new.html 뷰로 이동
    }

    // 새 Item 생성 처리
    @PostMapping("/new")
    public String createItem(
            @Valid @ModelAttribute("item") ItemCreateDto itemCreateDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            List<ItemCategory> categories = itemCategoryService.getAllCategories();
            List<State> states = stateService.getAllStates();
            model.addAttribute("categories", categories);
            model.addAttribute("states", states);
            return "items/new";  // 유효성 검사 실패 시 다시 폼으로 이동
        }

        itemService.createItem(itemCreateDto);
        return "redirect:/refrigerators";  // 성공 시 '/refrigerators'로 리디렉션
    }

    // 특정 Item 상세 페이지
    @GetMapping("/{itemId}")
    public String getItemById(@PathVariable Long itemId, Model model) {
        Item item = itemService.getItemById(itemId);
        model.addAttribute("item", item);
        return "items/detail";  // items/detail.html 뷰로 이동
    }
}