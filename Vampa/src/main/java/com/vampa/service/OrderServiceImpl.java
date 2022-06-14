package com.vampa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vampa.mapper.AttachMapper;
import com.vampa.mapper.OrderMapper;
import com.vampa.model.AttachImageVO;
import com.vampa.model.OrderPageItemDTO;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private AttachMapper attachMapper;

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
	
	
}
