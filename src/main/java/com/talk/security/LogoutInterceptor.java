package com.talk.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogoutInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			HttpSession session = request.getSession();
			if(session == null) {
				return false;
			}
			
			session.removeAttribute("authUser");
			session.invalidate();
			
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		}catch(Exception e) {
			throw e;
		}
	}

}