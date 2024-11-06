package com.refrigerator.permission.repository;

import com.refrigerator.permission.entity.MemberRefrig;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRefrigRepository extends JpaRepository<MemberRefrig, Long> {

    // userId와 refrigId로 MemberRefrig 조회
    Optional<MemberRefrig> findByMemberUserIdAndRefrigeratorRefrigId(Long userId, Long refrigId);

    List<MemberRefrig> findByMemberUserIdAndPermissionReadable(Long member_userId, boolean permission_readable);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO member_refrig (user_id, refrig_id, permission_id) VALUES (:userId, :refrigId, :permissionId)", nativeQuery = true)
    int insertMemberRefrig(Long userId, Long refrigId, Long permissionId);
}