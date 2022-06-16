package com.vampa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vampa.model.BookVO;
import com.vampa.model.MemberVO;
import com.vampa.model.OrderDTO;
import com.vampa.model.OrderItemDTO;
import com.vampa.model.OrderPageItemDTO;

@Mapper
public interface OrderMapper {
	
	/* 주문 상품 정보 요청(주문 페이지에서) */
	public OrderPageItemDTO getGoodsInfo(int bookId);

	/*[42-2~]주문 구현*/
	/* 주문 상품 정보(주문 처리) */ //bookId,bookPrice,bookDiscount
	public OrderItemDTO getOrderInfo(int bookId);
	
	/* 주문(vam_order) 테이블 등록 */
	public int enrollOrder(OrderDTO ord);
	
	/* vam_orderItem테이블(주문 아이템) 등록 */
	public int enrollOrderItem(OrderItemDTO orid);
	
	/* 주문 후, vam_member테이블에서 회원의 돈,포인트 차감 */
	public int deductMoney(MemberVO member);
	
	/* 회원이 주문한 상품의 개수만큼, vam_book테이블의 '상품 재고'를 차감 */
	public int deductStock(BookVO book);
	
	/*[44~]주문 취소*/
	/* 주문 취소 */
	public int orderCancle(String orderId);
	
	/* vam_orderItem테이블, 주문 상품 정보(주문취소) */
	public List<OrderItemDTO> getOrderItemInfo(String orderId);
	
	/* (회원이 주문한 vam_order테이블) 주문 정보(주문취소) */
	public OrderDTO getOrder(String orderId);
}
