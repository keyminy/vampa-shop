package com.vampa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vampa.model.BookVO;
import com.vampa.model.CateVO;

@Mapper
public interface AdminMapper {

	/*상품 등록*/
	public void bookEnroll(BookVO book);
	
	/* 카테고리 리스트 */
	public List<CateVO> cateList();
}
