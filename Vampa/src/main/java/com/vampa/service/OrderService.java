package com.vampa.service;

import java.util.List;

import com.vampa.model.OrderCancleDTO;
import com.vampa.model.OrderDTO;
import com.vampa.model.OrderPageItemDTO;

public interface OrderService {
	
	/*주문페이지로 주문 상품 정보 만들어내기 */
	//여러개의 상품정보를 만들어서 반환하므로 List타입으로 반환
	public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders);
	
	/* 주문처리 구현 */
	public void order(OrderDTO orw);
	//orw = OrderRequestWrapper
	
	/* 주문 취소 */
	public void orderCancle(OrderCancleDTO dto);
}
