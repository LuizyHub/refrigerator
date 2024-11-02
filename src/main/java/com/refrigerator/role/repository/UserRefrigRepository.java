package com.refrigerator.role.repository;

import com.refrigerator.role.entity.UserRefrig;
import com.refrigerator.user.entity.User;
import com.refrigerator.refrig.entity.Refrigerator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRefrigRepository extends JpaRepository<UserRefrig, Long> {
    Optional<UserRefrig> findByUserAndRefrigerator(User user, Refrigerator refrigerator);

    List<UserRefrig> findByRefrigerator(Refrigerator refrigerator);

    List<UserRefrig> findByUser(User user);
}
