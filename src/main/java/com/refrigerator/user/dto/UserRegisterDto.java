// UserRequestDto.java
package com.refrigerator.user.dto;

import com.refrigerator.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    public User toUser() {
        return User.of(email, name);
    }
}


