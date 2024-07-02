package com.talk.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.talk.model.Users;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;



// extends HandlerInterceptorAdapter -> spring 5.3 버전 이상에서는 HandlerInterceptorAdapter를 사용하는 대신 HandlerInterceptor를 implements 하는 방식으로 바꿔었다고 한다.
// implements HandlerInterceptor -> Interceptor를 커스텀 할때 사용
public class AuthInterceptor implements HandlerInterceptor{
	// HandlerInterceptor 인터페이스에 정의된 메서드 : 
	// - preHandle() : 컨트롤러가 호출되기 전에 실행
	// - postHandle() : 핸들러 실행 완료, View 생성 이전에 호출
	// - afterCompletion() : 모든 View에서 최종 결과를 생성하는 일을 포함한 모든 작업이 완료된 후 실행
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			
	        System.out.println("preHandle 실행 (AuthInterceptor.java)");
	        System.out.println("[preHandle][" + request + "]" + "[" + request.getMethod() + "]" + request.getRequestURI());
	        System.out.println("[handler][" + handler.toString() + "]");
	        //return값이 false 일경우 Controller로 넘어가지 않는다.
			
			
			//1. handler 종류 확인
			if(handler instanceof HandlerMethod == false) {
				return true;
			}
			
			//2. casting
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			
			//3. Handler Method의 @Auth 받아오기
			Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
			
			//4. Handler Method에 @Auth가 없으면 Type에 있는 지 확인(과제)
			// ElementType.TYPE -> 클래스 / 인터페이스 / 열거 타입(enum)에 어노테이션을 부착할 수 있게 하겠다는 의미
			if(auth == null) {
				auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
			}
			
			//5. Type과 Method에 @Auth가 적용이 안되어 있는 경우
			if(auth == null) {
				return true;
			}
			
			//6. @Auth가 적용이 되어 있기 때문에 인증(Authenfication) 여부 확인
			HttpSession session = request.getSession();
			if(session == null) {
				response.sendRedirect(request.getContextPath() + "/user/login");
				return false;
			}
			Users authUser = (Users)session.getAttribute("authUser");
			if(authUser == null) {
				response.sendRedirect(request.getContextPath() + "/user/login");
				return false;
			}
			
			//7. 권한(Authorization) 체크를 위해서 @Auth의 role 가져오기("USER", "ADMIN")
			String role = auth.role();
			
			//8. @Auth의 role이 "USER" 인 경우, authUser의 role은 상관없다.
			if(role.equals(authUser.getType())) {
				return true;
			}
			else{
				response.sendRedirect(request.getContextPath() + "/");
				return false;
			}
		
		}catch(Exception e) {
			throw e;
		}

	}
}