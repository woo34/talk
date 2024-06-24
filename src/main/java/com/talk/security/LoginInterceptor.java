package com.talk.security;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.talk.service.MemberService;


//extends HandlerInterceptorAdapter -> spring 5.3 버전 이상에서는 HandlerInterceptorAdapter를 사용하는 대신 HandlerInterceptor를 implements 하는 방식으로 바꿔었다고 한다.
//implements HandlerInterceptor -> Interceptor를 커스텀 할때 사용
public class LoginInterceptor implements HandlerInterceptor {
	// HandlerInterceptor 인터페이스에 정의된 메서드 : 
	// - preHandle() : 컨트롤러가 호출되기 전에 실행
	// - postHandle() : 핸들러 실행 완료, View 생성 이전에 호출
	// - afterCompletion() : 모든 View에서 최종 결과를 생성하는 일을 포함한 모든 작업이 완료된 후 실행
	
	@Autowired
	private MemberService memberService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			System.out.println("Pre Handle method is Calling: " + request.getRequestURI());
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("P_ID", request.getParameter("ID"));
			parameters.put("P_PASSWORD", request.getParameter("PASSWORD"));

			//멤버VO내 유효성 검사를 위한 어노테이션 추가하고 주석 달아두기
			Member authUser = memberService.find_Member(parameters);
			if(authUser == null) {
				//로그인 화면을 다시 호출하는 로직(아이디/비밀번호가 일치하지 않습니다. 팝업 띄워도 됨)
//				request
//					.getRequestDispatcher("/WEB-INF/views/user/login.jsp")
//					.forward(request, response);
				return false;
			}
			
			// session 처리
			System.out.println(authUser);
			
			HttpSession session = request.getSession(true);
			session.setAttribute("authUser", authUser);
			session.setMaxInactiveInterval(1800);
			System.out.println("getRequestURI : " + request.getRequestURI()); // ex) /template/index.jsp
			System.out.println("getContextPath : " + request.getContextPath()); // ex) /template
			System.out.println("getRequestURL : " + request.getRequestURL()); // ex)http://localhost:8080/template/index.jsp
			System.out.println("getServletPath : " + request.getServletPath()); // ex) /index.jsp
			
			response.sendRedirect(request.getContextPath() + "/"); //request.getContextPath() 만 받아오면 실행X, + "/" 붙여줘야 http://localhost:8080/ 실행된다.

			return true;

		}catch(Exception e) {
			throw e;
		}
	}
	
	
	// 아래 메서드 필요없으니 그냥 override해둠
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Post Handle method is Calling: " + request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        System.out.println("Request and Response is completed: " + request.getRequestURI());
    }
}