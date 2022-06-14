package com.vampa.model;

import java.util.List;

public class OrderPageDTO {
	//두 개 이상의 상품 정보를 클라이언트=>서버로 넘기기 위해
	//기존의 bookId,bookCount만 담는 OrderPageItemDTO 클래스를 List형태로
	private List<OrderPageItemDTO> orders;

	public List<OrderPageItemDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderPageItemDTO> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "OrderPageDTO [orders=" + orders + "]";
	}
}
