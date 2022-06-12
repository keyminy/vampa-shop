package com.vampa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vampa.mapper.CartMapper;
import com.vampa.model.CartDTO;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public int addCart(CartDTO cart) {
		//장바구니 데이터 존재 체크
		CartDTO checkCart = cartMapper.checkCart(cart);
		if(checkCart != null) {
			return 2; //이미 등록되었을때
		}
		try {
			return cartMapper.addCart(cart);
		} catch (Exception e) {
			return 0;
		}
	}
	
}
