package com.refrigerator.member.service;

import com.refrigerator.member.dto.MemberLoginDto;
import com.refrigerator.member.dto.MemberRegisterDto;
import com.refrigerator.member.entity.Member;
import com.refrigerator.member.repository.MemberRepository;
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
}
