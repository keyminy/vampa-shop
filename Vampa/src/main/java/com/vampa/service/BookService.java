package com.vampa.service;

import java.util.List;

import com.vampa.model.BookVO;
import com.vampa.model.Criteria;

public interface BookService {
	/* 상품 검색 */
	public List<BookVO> getGoodsList(Criteria cri);
	
	/* 상품 총 갯수 */
	public int goodsGetTotal(Criteria cri);
}
