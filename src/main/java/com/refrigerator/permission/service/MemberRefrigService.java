package com.refrigerator.permission.service;

import com.refrigerator.member.entity.Member;
import com.refrigerator.permission.dto.ShareDto;
import com.refrigerator.permission.entity.MemberRefrig;
import com.refrigerator.permission.entity.Permission;
import com.refrigerator.permission.repository.MemberRefrigRepository;
import com.refrigerator.refrig.entity.Refrigerator;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberRefrigService {

    private final MemberRefrigRepository memberRefrigRepository;
    private final EntityManager entityManager;

    public boolean hasPermissionToRead(Long userId, Long refrigId) {
        return memberRefrigRepository.findByMemberUserIdAndRefrigeratorRefrigId(userId, refrigId)
                .map(memberRefrig -> memberRefrig.getPermission().isReadable())
                .orElse(false);
    }

    public boolean hasPermissionToWrite(Long userId, Long refrigId) {
        return memberRefrigRepository.findByMemberUserIdAndRefrigeratorRefrigId(userId, refrigId)
                .map(memberRefrig -> memberRefrig.getPermission().isWritable())
                .orElse(false);
    }

    public boolean hasPermissionToDelete(Long userId, Long refrigId) {
        return memberRefrigRepository.findByMemberUserIdAndRefrigeratorRefrigId(userId, refrigId)
                .map(memberRefrig -> memberRefrig.getPermission().isDeletable())
                .orElse(false);
    }

    public boolean hasOwnerPermission(Long userId, Long refrigId) {
        return memberRefrigRepository.findByMemberUserIdAndRefrigeratorRefrigId(userId, refrigId)
                .map(memberRefrig -> memberRefrig.getPermission().getName().equals("OWNER"))
                .orElse(false);
    }

    public List<Refrigerator> getAllReadableRefrigerators(Long userId) {
        return memberRefrigRepository.findByMemberUserIdAndPermissionReadable(userId, true)
                .stream()
                .map(MemberRefrig::getRefrigerator)
                .toList();
    }

    public void createMemberRefrigRWD(Long userId, Long refrigId, Permission permission) {
        int i = memberRefrigRepository.insertMemberRefrig(userId, refrigId, permission.getPermissionId());
        if (i != 1) {
            throw new IllegalArgumentException("멤버 냉장고 생성에 실패했습니다.");
        }
    }

    public Map<Member, Permission> getAllMemberRefrigByRefrigId(Long refrigId) {
        Refrigerator refrigerator = entityManager.getReference(Refrigerator.class, refrigId);
        return memberRefrigRepository.findAllByRefrigerator(refrigerator)
                .stream()
                .collect(Collectors.toMap(
                        MemberRefrig::getMember,
                        MemberRefrig::getPermission
                ));
    }
}
