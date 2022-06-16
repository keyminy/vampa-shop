package com.vampa.model;

import lombok.Data;

@Data
public class OrderCancleDTO {
private String memberId;
	
	private String orderId;
	
	/* 주문취소 후 리다이렉트할때, 페이징 정보 */
	private String keyword;
	
	private int amount;
	
	private int pageNum;
}
