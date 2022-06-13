package com.vampa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vampa.model.MemberVO;
import com.vampa.service.MemberService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping(value = "/member")
public class MemberController {
	
	@Autowired
	private MemberService memberservice;
	
	// 회원가입 페이지 이동
	@RequestMapping(value = "join", method = RequestMethod.GET)
	public void loginGET() {

		log.info("회원가입 페이지 진입");

	}

	// 로그인 페이지 이동
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public void joinGET() {
		log.info("로그인 페이지 진입");
	}
	
	//회원가입
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String joinPOST(MemberVO member) throws Exception{
		log.info("join 진입");
		// 회원가입 서비스 실행
		memberservice.memberJoin(member);
		log.info("join Service 성공");
		return "redirect:/main";
		
	}
	
	// 아이디 중복 검사
	@RequestMapping(value = "/memberIdChk", method = RequestMethod.POST)
	@ResponseBody
	public String memberIdChkPOST(String memberId) throws Exception{
		int result = memberservice.idCheck(memberId);

		if(result != 0) {
			return "fail";	// 중복 아이디가 존재
		} else {
			return "success";	// 중복 아이디 x
		}	
		
	} // memberIdChkPOST() 종료	
 
	/* 로그인 POST*/
    @RequestMapping(value="login.do", method=RequestMethod.POST)
    public String loginPOST(HttpServletRequest request, MemberVO member, RedirectAttributes rttr) throws Exception{      
       HttpSession session = request.getSession();
       MemberVO lvo = memberservice.memberLogin(member);
       if(lvo==null) {
    	   //일치하지 않는 아이디,비밀번호를 입력하여 lvo가 null
    	   int result = 0;
    	   //0은 실패
    	   rttr.addFlashAttribute("result",result);
    	   return "redirect:/member/login";
       }
       // 일치하는 아이디, 비밀번호 경우 (로그인 성공)
       //session에 변수 lvo에 담긴 데이터를 저장하는 코드를 추가해줍니다.
       session.setAttribute("member", lvo);            
       return "redirect:/main";
    }
    /* 메인페이지 로그아웃 */
    @RequestMapping(value="logout.do", method=RequestMethod.GET)
    public String logoutMainGET(HttpServletRequest request) throws Exception{
    	//session변수 및 초기화
    	HttpSession session = request.getSession();
    	 session.invalidate();
    	 return "redirect:/main";     
    }
    
    /* 비동기방식 로그아웃 메서드 */
    @RequestMapping(value="logout.do", method=RequestMethod.POST)
    @ResponseBody
    public void logoutPOST(HttpServletRequest request) throws Exception{      
        log.info("비동기 로그아웃 메서드 진입");
        HttpSession session = request.getSession();   
        session.invalidate();    
    }
}
