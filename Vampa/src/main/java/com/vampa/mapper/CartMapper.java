package com.vampa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vampa.model.CartDTO;

@Mapper
public interface CartMapper {

	/* 카트 추가 
	 * vam_cart 테이블의 row를 추가
	 * memberId, bookId, bookCount값이 필요로 하므로 CartDTO로 한번에 가져온다. 
	 * row 추가 성공 시 1, 실패시 0 */
	public int addCart(CartDTO cart);
	
	/* 카트 삭제 
	 * 어떠한 row를 삭제할지 지정하기 위해 cartId에 대한 값이 필요 */
	public int deleteCart(int cartId);
	
	/* 카트 수량 수정 
	 * 장바구니 테이블 수량 COLUMN 수정
	 * 어떠한 row인지 지정을 위한 cartId와 몇 개의 수량으로 변경할지에 대한 값 bookCount가 필요 */
	public int modifyCount(CartDTO cart);
	
	
	/* 카트 목록
	 * memberId로 지정한 회원의 장바구니 테이블 모든 ROW 가져오기 
	 * 반환 타입 : 한 개 이상의 장바구니 데이터를 반환받아야 하기 때문에 List<CartDTO>*/
	public List<CartDTO> getCart(String memberId);
	
	/* 카트 확인
	 * 장바구니 테이블 한 개의 ROW 가져오기 */
	public CartDTO checkCart(CartDTO cart);
	
	/*[42-2]주문 구현*/
	/* 장바구니 경로로, 주문을 한 경우 주문 상품에 대한 장바구니 상품 정보 삭제 */
	public int deleteOrderCart(CartDTO dto);
}
