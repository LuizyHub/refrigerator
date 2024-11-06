// UserRequestDto.java
package com.refrigerator.member.dto;

import com.refrigerator.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegisterDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    public Member toMember() {
        return Member.of(email, name);
    }
}


