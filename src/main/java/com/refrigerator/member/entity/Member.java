package com.refrigerator.member.entity;

//import com.refrigerator.role.entity.UserRefrig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // user_id

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String name;

    private Member(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static Member of(String email, String name) {
        return new Member(email, name);
    }
}
