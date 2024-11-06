package com.refrigerator.inventory.controller;

import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.inventory.service.InventoryService;
import com.refrigerator.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/refrigerators/{refrigeratorId}/inventories")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public String getInventories(
            Model model,
            @CurrentMember Member member,
            @PathVariable Long refrigeratorId
    ) {
        List inventories = inventoryService.getAllInventoriesReadable(member.getUserId(), refrigeratorId);
        model.addAttribute("inventories", inventories);

        return "inventories";
    }
}
