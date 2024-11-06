package com.refrigerator.common.resolver;

import com.refrigerator.common.exception.UnauthorizedException;
import com.refrigerator.common.session.SessionConst;
import com.refrigerator.member.entity.Member;
import com.refrigerator.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.security.auth.login.LoginException;

@Component
@RequiredArgsConstructor
public class CurrentMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository userRepository; // 세션이나 토큰에서 사용자 정보를 가져오기 위한 서비스

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터에 @CurrentUser 애노테이션이 있고 타입이 User인 경우에만 지원
        return parameter.hasParameterAnnotation(CurrentMember.class) &&
               Member.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Member resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        // 세션 또는 토큰에서 사용자 ID를 가져옴 (예시는 세션 기반)
        Long userId = (Long) webRequest.getAttribute(SessionConst.LOGIN_MEMBER_ID, RequestAttributes.SCOPE_SESSION);

        if (userId == null) {
            throw new LoginException("User not logged in");
        }

        // 사용자 ID로 사용자 객체를 조회
        return userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
    }
}
