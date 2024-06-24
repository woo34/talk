package com.talk.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.talk.vo.Member;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter parameter, 
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, 
			WebDataBinderFactory binderFactory) throws Exception {
		// HandlerMethodArgumentResolver : 사용자 요청이 Controller에 도달하기 전에 Parameter들을 수정할 수 있도록 해준다.
		// 2가지 메서드를 가지고 있다.
		// supportsParameter : 핸들러(컨트롤러의 메소드)의 특정 파라미터를 지원하는지 여부를 판단하기 위한 메소드
		// resolveArgument : 해당 parameter에 대한 실질적인 로직을 처리하는 곳, parameter에 전달할 객체에 대한 조작을 자유롭게 진행한 뒤 해당 객체를 리턴
		
		
		//1. 파라미터에 @AuthUser가 붙어있는지, 타입이 UserVo인지 확인
		if(!supportsParameter(parameter)) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		
		if(session == null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
		//2. @AuthUser가 붙어 있는지 확인
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		//@AuthUser가 안붙어있음
		if(authUser == null) {
			return false;
		}
		
		//파라미터 타입이 UserVo가 아님
		if(parameter.getParameterType().equals(Member.class) == false) {
			return false;
		}
		
		return true;
	}

}
