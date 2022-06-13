package com.vampa.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.vampa.model.MemberVO;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 System.out.println("LoginInterceptor preHandle 작동");
	        
        HttpSession session = request.getSession();
        
        session.invalidate();
		return true;
	}
}
