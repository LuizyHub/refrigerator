package com.refrigerator.member.service;

import com.refrigerator.member.dto.MemberLoginDto;
import com.refrigerator.member.dto.MemberRegisterDto;
import com.refrigerator.member.entity.Member;
import com.refrigerator.member.repository.MemberRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository userRepository;

    public Member login(MemberLoginDto memberLoginDto) {
        Optional<Member> user = userRepository.findByEmail(memberLoginDto.getEmail());
        return user.orElse(null);
    }

    public void registerUser(MemberRegisterDto memberRegisterDto) {
        Member user = memberRegisterDto.toMember();
        userRepository.save(user);
    }

    public Member findMemberByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow( () -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    public Member findMemberById(Long userId) {
        return userRepository.findById(userId).orElseThrow( () -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }
}
