package com.vampa.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.vampa.model.MemberVO;

public class AdminInterceptor implements HandlerInterceptor{
	//("/admin/**")에 접근하는 사용자의 admiCk 1인지 확인하는 작업이 핵심
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		MemberVO lvo = (MemberVO)session.getAttribute("member");
		if(lvo == null || lvo.getAdminCk() ==0) {
			//관리자가 아님
			response.sendRedirect("/main");
			return false;
		}
		// 관리자 계정 로그인 경우(lvo != null && lvo.getAdminCk() == 1)
		return true;
	}
}
