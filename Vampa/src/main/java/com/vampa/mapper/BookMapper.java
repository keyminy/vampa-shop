package com.vampa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vampa.model.BookVO;
import com.vampa.model.Criteria;

@Mapper
public interface BookMapper {
	/* 상품 검색 결과 */
	public List<BookVO> getGoodsList(Criteria cri);
	
	/* 상품 총 갯수(페이징 데이터) */
	public int goodsGetTotal(Criteria cri);
	
	/* 작가 id 리스트 요청 */
	public String[] getAuthorIdList(String keyword);
}