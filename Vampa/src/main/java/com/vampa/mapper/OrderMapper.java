package com.vampa.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.vampa.model.OrderPageItemDTO;

@Mapper
public interface OrderMapper {
	
	/* 주문 상품 정보 요청 */
	public OrderPageItemDTO getGoodsInfo(int bookId);

}
