package com.vampa.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.vampa.model.CartDTO;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class CartServiceTests {
	@Autowired
	private CartService cartService;
	
	//등록 테스트
	@Test
	public void addCartTest() {
		//given
		//String memberId = "admin23";
		String memberId = "admin231515";
		int bookId = 16803;
		int count = 5;
		
		CartDTO dto = new CartDTO(); 
		dto.setMemberId(memberId);
		dto.setBookId(bookId);
		dto.setBookCount(count);
		
		//when
		int result = cartService.addCart(dto);
	
		//then
		System.out.println("** result : " + result);
	}
}
