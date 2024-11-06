package com.refrigerator.role.entity;


import com.refrigerator.refrig.entity.Refrigerator;
import com.refrigerator.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "User_Refrig")
@Getter
@Setter
@NoArgsConstructor
public class UserRefrig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // user_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refrig_id", nullable = false)
    private Refrigerator refrigerator; // refrig_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role; // role_id
}
