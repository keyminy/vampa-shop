package com.vampa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vampa.mapper.AdminMapper;
import com.vampa.model.BookVO;
import com.vampa.model.CateVO;
import com.vampa.model.Criteria;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminMapper adminMapper;	
	
	@Transactional
	@Override
	public void bookEnroll(BookVO book) {
		log.info("(service)bookEnroll........");
		adminMapper.bookEnroll(book);
		/* 이미지 존재 여부를 확인하여, 없으면 imageEnroll()수행 안시키게 */
		/* =>BookVO객체에 imageList참조 변수의 요소의 개수가 0이면 이미지 파일이 없는 것 */
		if(book.getImageList()==null || book.getImageList().size()<=0) {
			return;
		}
		book.getImageList().forEach(attach->{
			attach.setBookId(book.getBookId());
			adminMapper.imageEnroll(attach);
		});
	}

	/* 카테고리 리스트 */
	@Override
	public List<CateVO> cateList() {
		
		log.info("(service)cateList........");
		
		return adminMapper.cateList();
	}

	/* 상품 리스트 */
	@Override
	public List<BookVO> goodsGetList(Criteria cri) {
		log.info("goodsGetTotalList()............");
		return adminMapper.goodsGetList(cri);
	}

	/* 상품 총 갯수 */
	@Override
	public int goodsGetTotal(Criteria cri) {
		log.info("goodsGetTotal().........");
		return adminMapper.goodsGetTotal(cri);
	}

	@Override
	public BookVO goodsGetDetail(int bookId) {
		log.info("(service)bookGetDetail......." + bookId);
		return adminMapper.goodsGetDetail(bookId);
	}

	@Override
	public int goodsModify(BookVO vo) {
		log.info("goodsModify........");	
		return adminMapper.goodsModify(vo);
	}

	@Override
	public int goodsDelete(int bookId) {
		log.info("goodsDelete..........");
		return adminMapper.goodsDelete(bookId);
	}
}
