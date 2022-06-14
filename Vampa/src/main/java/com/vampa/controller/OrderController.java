package com.vampa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
