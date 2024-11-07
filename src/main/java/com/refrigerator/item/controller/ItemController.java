package com.refrigerator.item.controller;

import com.refrigerator.item.dto.ItemCreateDto;
import com.refrigerator.item.entity.Item;
import com.refrigerator.item.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // 모든 Item 리스트 페이지
    @GetMapping
    public String getItems(Model model) {
        List<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "items";  // items.html 뷰로 이동
    }

    // 새 Item 생성 폼 페이지
    @GetMapping("/new")
    public String createItemForm(@ModelAttribute("item") ItemCreateDto itemCreateDto) {
        return "items/new";  // items/new.html 뷰로 이동
    }

    // 새 Item 생성 처리
    @PostMapping("/new")
    public String createItem(
            @Valid @ModelAttribute("item") ItemCreateDto itemCreateDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "items/new";  // 유효성 검사 실패 시 다시 폼으로 이동
        }

        itemService.createItem(itemCreateDto);
        return "redirect:/items";  // 아이템 목록 페이지로 리다이렉트
    }

    // 특정 Item 상세 페이지
    @GetMapping("/{id}")
    public String getItemById(@PathVariable Long id, Model model) {
        Item item = itemService.getItemById(id);
        model.addAttribute("item", item);
        return "items/detail";  // items/detail.html 뷰로 이동
    }
}