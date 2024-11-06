package com.refrigerator.user.entity;

import com.refrigerator.role.entity.UserRefrig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // user_id

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "user")
    private Set<UserRefrig> userRefrigs = new HashSet<>();

    private User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static User of(String email, String name) {
        return new User(email, name);
    }
}
