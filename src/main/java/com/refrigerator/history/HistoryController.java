package com.refrigerator.history;

import com.refrigerator.common.resolver.CurrentMember;
import com.refrigerator.history.dto.History;
import com.refrigerator.history.entity.Log;
import com.refrigerator.history.service.HistoryService;
import com.refrigerator.member.entity.Member;
import com.refrigerator.refrig.service.RefrigeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/refrigerators/{refrigId}/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final RefrigeratorService refrigeratorService;
    @GetMapping
    public String getHistory(
            @CurrentMember Member member,
            @PathVariable Long refrigId,
            Model model
    ) {
        List<Log> logsByRefrigId = historyService.getLogsByRefrigId(member.getUserId(), refrigId);

        model.addAttribute("history", logsByRefrigId);
        model.addAttribute("refrigerator", refrigeratorService.getRefrigeratorById(refrigId));

        return "history";
    }
}
