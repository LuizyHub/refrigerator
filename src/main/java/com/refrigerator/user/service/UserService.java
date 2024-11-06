package com.refrigerator.user.service;

import com.refrigerator.user.dto.UserLoginDto;
import com.refrigerator.user.dto.UserRegisterDto;
import com.refrigerator.user.entity.User;
import com.refrigerator.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User login(UserLoginDto userLoginDto) {
        Optional<User> user = userRepository.findByEmail(userLoginDto.getEmail());
        return user.orElse(null);
    }

    public void registerUser(UserRegisterDto userRegisterDto) {
        User user = userRegisterDto.toUser();
        userRepository.save(user);
    }
}
