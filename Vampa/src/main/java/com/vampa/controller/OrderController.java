package com.vampa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.vampa.model.MemberVO;
import com.vampa.model.OrderDTO;
import com.vampa.model.OrderPageDTO;
import com.vampa.service.MemberService;
import com.vampa.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/order/{memberId}")
	public String orderPageGET(@PathVariable("memberId") String memberId,OrderPageDTO opd,Model model) {
		System.out.println("memberId : " + memberId);
		System.out.println("orders : " + opd.getOrders());
		model.addAttribute("orderList",orderService.getGoodsInfo(opd.getOrders()));
		model.addAttribute("memberInfo",memberService.getMemberInfo(memberId));
		return "/order";
	}
	
	@PostMapping("/order")
	public String orderPagePost(OrderDTO od,HttpServletRequest request) {
		System.out.println("od : " + od);
		orderService.order(od);
		//세션 정보를 최신화 하여, 주문하고 난 후의 돈과 포인트 보이게 해주기
		HttpSession session = request.getSession();
		
		MemberVO member = new MemberVO();
		member = (MemberVO)session.getAttribute("member");
		
		try {
			MemberVO memberLogin = memberService.memberLogin(member);
			session.setAttribute("member", memberLogin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/main";
	}
}
