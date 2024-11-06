package com.refrigerator.permission.entity;

import com.refrigerator.member.entity.Member;
import com.refrigerator.refrig.entity.Refrigerator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_refrig")
public class MemberRefrig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "refrig_id", nullable = false)
    private Refrigerator refrigerator;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    private MemberRefrig(Member member, Refrigerator refrigerator, Permission permission) {
        this.member = member;
        this.refrigerator = refrigerator;
        this.permission = permission;
    }

    public static MemberRefrig of(Member member, Refrigerator refrigerator, Permission permission) {
        return new MemberRefrig(member, refrigerator, permission);
    }
}