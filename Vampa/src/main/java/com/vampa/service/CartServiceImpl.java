package com.vampa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vampa.mapper.AttachMapper;
import com.vampa.mapper.CartMapper;
import com.vampa.model.AttachImageVO;
import com.vampa.model.CartDTO;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private AttachMapper attachMapper;
	
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

	@Override
	public List<CartDTO> getCartList(String memberIdt) {
		List<CartDTO> cart = cartMapper.getCart(memberIdt);
		for(CartDTO dto : cart) {
			dto.initSaleTotal();
			/* 이미지 정보 얻기 */
			int bookId = dto.getBookId();
			List<AttachImageVO> imageList = attachMapper.getAttachList(bookId);
			dto.setImageList(imageList);
		}
		//값이 셋팅된 cart를 반환
		return cart;
	}
	
}
