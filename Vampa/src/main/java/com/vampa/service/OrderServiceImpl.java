package com.vampa.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vampa.mapper.AttachMapper;
import com.vampa.mapper.BookMapper;
import com.vampa.mapper.CartMapper;
import com.vampa.mapper.MemberMapper;
import com.vampa.mapper.OrderMapper;
import com.vampa.model.AttachImageVO;
import com.vampa.model.BookVO;
import com.vampa.model.CartDTO;
import com.vampa.model.MemberVO;
import com.vampa.model.OrderCancleDTO;
import com.vampa.model.OrderDTO;
import com.vampa.model.OrderItemDTO;
import com.vampa.model.OrderPageItemDTO;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private AttachMapper attachMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private BookMapper bookMapper;

	@Override
	public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders) {
		//반환해야될거 초기화
		List<OrderPageItemDTO> result = new ArrayList<OrderPageItemDTO>();
		for(OrderPageItemDTO opid : orders) {
			OrderPageItemDTO goodsInfo = orderMapper.getGoodsInfo(opid.getBookId());
			//bookCount정보는 DB가아닌,view에서 전달받은 값을 set함.
			goodsInfo.setBookCount(opid.getBookCount());
			/* salePrice와 totalPrice,point,totalPoint의 변수 값을 초기화 해주는 메서드 */
			goodsInfo.initSaleTotal();
			List<AttachImageVO> imageList = attachMapper.getAttachList(goodsInfo.getBookId());
			goodsInfo.setImageList(imageList);
			result.add(goodsInfo);
		}
		return result;
	}

	@Override
	@Transactional
	public void order(OrderDTO ord) {
		/* 1.주문자의 정보가 담긴 객체 셋팅 - 사용할 데이터 가져오기 */
		/* 회원 정보 */
		MemberVO member = memberMapper.getMemberInfo(ord.getMemberId());
		/* 주문 정보 (OrderItemDTO) 셋팅 */
		List<OrderItemDTO> ords = new ArrayList<>();
		for(OrderItemDTO oit : ord.getOrders()) {
			OrderItemDTO orderItem = orderMapper.getOrderInfo(oit.getBookId());
			//수량 셋팅
			orderItem.setBookCount(oit.getBookCount());
			//기본정보 셋팅
			orderItem.initSaleTotal();
			//List객체에 추가
			ords.add(orderItem);
		}
		/* 주문 정보 (OrderDTO) 셋팅 */
		ord.setOrders(ords);
		/* 주문 작업에 필요로 한 데이터를 세팅해주는 메서드이다(추가했던것) */
		ord.getOrderPriceInfo();
		
		/* 2.DB 주문,주문상품(,배송정보) 넣기 */
		/* orderId만들기 및 OrderDTO객체에 orderId값 저장 */
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("_yyyyMMddmm");
		String orderId = member.getMemberId() + format.format(date);
		ord.setOrderId(orderId);
		
		/* DB에 넣기 */
		orderMapper.enrollOrder(ord);//vam_order 테이블 등록
		for(OrderItemDTO oit : ord.getOrders()) {
			//vam_orderItem 테이블 등록
			oit.setOrderId(orderId);
			orderMapper.enrollOrderItem(oit);
		}
		
		/*3.비용 포인트 변동 적용*/
		/* 비용 차감 & 변동 돈(money) Member객체에 적용 */
		int calMoney = member.getMoney();
		calMoney -= ord.getOrderFinalSalePrice();
		member.setMoney(calMoney);
		
		/* 주문으로 인한 포인트 차감 & 포인트 증가 & 변동 포인트를 Member객체에 적용 */
		int calPoint = member.getPoint();
		calPoint = calPoint - ord.getUsePoint() + ord.getOrderSavePoint();
		member.setPoint(calPoint);
		
		/* 주문하고 난 후, 변경된 돈,포인트 데이터를 DB에 반영 : deductMoney */
		orderMapper.deductMoney(member);
		
		/* 4.상품의 재고 값을 가져와 그 값에서 주문한 상품의 수 만큼
		 * vam_book테이블의 기존 재고에서 차감 */
		for(OrderItemDTO oit : ord.getOrders()) {
			/* 변동 재고 값 구하기 */
			BookVO book = bookMapper.getGoodsInfo(oit.getBookId());
			//재고 차감(기존 꺼 - 주문수량)
			book.setBookStock(book.getBookStock() - oit.getBookCount());
			/* 변동 값 DB 적용 */
			orderMapper.deductStock(book);
		}
		/* 5.장바구니 정보 제거 */
		for(OrderItemDTO oit : ord.getOrders()) {
			CartDTO dto = new CartDTO();
			dto.setMemberId(ord.getMemberId());
			dto.setBookId(oit.getBookId());
			cartMapper.deleteOrderCart(dto);
		}
	}

	@Override
	@Transactional
	public void orderCancle(OrderCancleDTO dto) {
		/* 1.DB에 저장된 주문,주문 상품 정보 꺼내오기 */
		/* 회원 */
		MemberVO member = memberMapper.getMemberInfo(dto.getMemberId());
		
		/*2.회원이 지불한 금액,포인트,받았던 포인트 값 구하기 */
		/* 주문 상품 */
		List<OrderItemDTO> ords = orderMapper.getOrderItemInfo(dto.getOrderId());
		for(OrderItemDTO ord : ords) {
			ord.initSaleTotal();
		}
		/* 주문 */
		//orw = OrderRequestWrapper
		OrderDTO orw = orderMapper.getOrder(dto.getOrderId());
		orw.setOrders(ords);
		orw.getOrderPriceInfo();
		
		/* vam_order의 orderState컬럼을 주문취소로 */
		orderMapper.orderCancle(dto.getOrderId());
		
		/* 3.회원이 사용한 돈,포인트반환 */
		/* 돈 */
		int calMoney = member.getMoney();
		calMoney += orw.getOrderFinalSalePrice();
		member.setMoney(calMoney);
		/* 포인트 */
		int calPoint = member.getPoint();
		calPoint += calPoint + orw.getUsePoint() - orw.getOrderSavePoint();
		member.setPoint(calPoint);
		
		/* DB에 돈,point반영 */
		orderMapper.deductMoney(member);
		
		/* 4.마지막, vam_book테이블의 재고 반환 */
		for(OrderItemDTO ord : orw.getOrders()) {
			BookVO book = bookMapper.getGoodsInfo(ord.getBookId());
			book.setBookStock(book.getBookStock() + ord.getBookCount());
			orderMapper.deductStock(book);
		}
	}
}
