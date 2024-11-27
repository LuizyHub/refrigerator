package com.refrigerator.history.service;

import com.refrigerator.common.exception.UnauthorizedException;
import com.refrigerator.history.dto.History;
import com.refrigerator.history.entity.Log;
import com.refrigerator.history.repository.LogRepository;
import com.refrigerator.inventory.entity.Inventory;
import com.refrigerator.member.entity.Member;
import com.refrigerator.permission.service.MemberRefrigService;
import com.refrigerator.unit.entity.Unit;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final LogRepository logRepository;
    private final EntityManager entityManager;
    private final MemberRefrigService memberRefrigService;

    public long addLog(History history) {
        Member member = entityManager.find(Member.class, history.memberId());
        Inventory inventory = entityManager.find(Inventory.class, history.inventoryId());
        Unit unit = entityManager.find(Unit.class, history.unitId());

        Log log = Log.of(member, inventory, unit, history.amount(), null);

        Log savedLog = logRepository.save(log);

        if (savedLog == null) {
            throw new RuntimeException("로그 저장에 실패했습니다.");
        }

        return savedLog.getLogId();
    }

    public List<History> getLogsByRefrigId(long memberId, long refrigId) {
        if (!memberRefrigService.hasPermissionToRead(memberId, refrigId)) {
            throw new UnauthorizedException("해당 냉장고에 대한 읽기 권한이 없습니다.");
        }

        List<Log> logs = logRepository.findByInventory_RefrigIdOrderByTimestampAsc(refrigId);

        return logs.stream()
                .map(log -> History.of(log.getLogId(), log.getMember().getUserId(), log.getInventory().getId(), log.getUnit().getUnitId(), log.getAmount()))
                .toList();
    }
}
