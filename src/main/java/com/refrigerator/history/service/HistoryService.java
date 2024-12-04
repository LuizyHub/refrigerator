package com.refrigerator.history.service;

import com.refrigerator.common.exception.UnauthorizedException;
import com.refrigerator.common.session.SessionConst;
import com.refrigerator.history.dto.History;
import com.refrigerator.history.entity.Log;
import com.refrigerator.history.repository.LogRepository;
import com.refrigerator.inventory.entity.Inventory;
import com.refrigerator.member.entity.Member;
import com.refrigerator.permission.service.MemberRefrigService;
import com.refrigerator.unit.entity.Unit;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final LogRepository logRepository;
    private final EntityManager entityManager;
    private final MemberRefrigService memberRefrigService;
    private final HttpSession httpSession;

    public long addLog(History history) {
        // 로그를 추가할 때 memberId가 없으면 세션에서 가져와서 추가
        if (history.memberId() == null) {
            Long memberId = (Long) httpSession.getAttribute(SessionConst.LOGIN_MEMBER_ID);
            history = history.withMemberId(memberId);
        }

        Member member = entityManager.getReference(Member.class, history.memberId());
        Inventory inventory = entityManager.getReference(Inventory.class, history.inventoryId());
        Unit unit = entityManager.getReference(Unit.class, history.unitId());

        Log log = Log.of(member, inventory, unit, history.amount(), null);

        Log savedLog = logRepository.save(log);

        if (savedLog == null) {
            throw new RuntimeException("로그 저장에 실패했습니다.");
        }

        return savedLog.getLogId();
    }

    public List<Log> getLogsByRefrigId(long memberId, long refrigId) {
        if (!memberRefrigService.hasPermissionToRead(memberId, refrigId)) {
            throw new UnauthorizedException("해당 냉장고에 대한 읽기 권한이 없습니다.");
        }

        List<Log> logs = logRepository.findByInventory_RefrigIdOrderByTimestampDesc(refrigId);

        return logs;
//        return logs.stream()
//                .map(log -> History.of(log.getLogId(), log.getMember().getUserId(), log.getInventory().getId(), log.getUnit().getUnitId(), log.getAmount()))
//                .toList();
    }
}
