package com.vampa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vampa.model.CartDTO;
import com.vampa.model.MemberVO;
import com.vampa.service.CartService;

@Controller
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/cart/add")
	@ResponseBody
	public String addCartPOST(CartDTO cart,HttpServletRequest request) {
		//로그인 체크, 로그인하지않고 cart추가하면 5가 반환
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("member");
		if(mvo==null) {
			return "5";
		}
		//카트 등록 수행 메서드
		int result = cartService.addCart(cart);
		return result + "";
	}
	
	/* 장바구니 뷰 페이지 이동 수행 */
	@GetMapping("/cart/{memberId}")
	public String cartPageGET(@PathVariable("memberId") String memberId,Model model) {
		model.addAttribute("cartInfo",cartService.getCartList(memberId));
		return "/cart";
	}
}
