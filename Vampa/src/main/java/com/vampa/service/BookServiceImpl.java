package com.vampa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vampa.mapper.BookMapper;
import com.vampa.model.BookVO;
import com.vampa.model.Criteria;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BookServiceImpl implements BookService{

	@Autowired
	private BookMapper bookMapper;
	
	@Override
	public List<BookVO> getGoodsList(Criteria cri) {
		log.info("getGoodsList().......");	
		String type = cri.getType();
		String[] typeArr = type.split("");
		String[] authorArr = bookMapper.getAuthorIdList(cri.getKeyword());
		
		if(type.equals("A") || type.equals("AC") || type.equals("AT") || type.equals("ACT")) {
			if(authorArr.length==0) {
				//keyword에대한 vam_author테이블 조회 결과가 없으면 빈 List반환하여 조회안되게함 
				return new ArrayList<>();
			}
		}
		
		/* 검색 조건에 '작가'에 대한 검색이 있을때만 수행해야함=>for문을 돌아서 검사 */
		//authorArr이 빈 리스트 배열이 아닐 경우,해당 배열의 데이터를 Criteria의 authorArr변수에 담은 후, 검색쿼리 실행
		for(String t : typeArr) {
			if(t.equals("A")) {
				cri.setAuthorArr(authorArr);
			}
		}
		
		//return new ArrayList<>();
		return bookMapper.getGoodsList(cri);
	}

	@Override
	public int goodsGetTotal(Criteria cri) {
		log.info("goodsGetTotal().......");
		return bookMapper.goodsGetTotal(cri);
	}
}
