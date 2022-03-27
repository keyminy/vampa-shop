package com.vampa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vampa.mapper.AdminMapper;
import com.vampa.model.BookVO;
import com.vampa.model.CateVO;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminMapper adminMapper;	
	
	@Override
	public void bookEnroll(BookVO book) {
		log.info("(srevice)bookEnroll........");
		
		adminMapper.bookEnroll(book);
	}

	/* 카테고리 리스트 */
	@Override
	public List<CateVO> cateList() {
		
		log.info("(service)cateList........");
		
		return adminMapper.cateList();
	}
	
}
