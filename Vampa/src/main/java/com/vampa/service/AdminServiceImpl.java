package com.vampa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vampa.mapper.AdminMapper;
import com.vampa.model.AttachImageVO;
import com.vampa.model.BookVO;
import com.vampa.model.CateVO;
import com.vampa.model.Criteria;
import com.vampa.model.OrderDTO;

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

	/* 상품 정보 수정 */
	@Transactional
	@Override
	public int goodsModify(BookVO vo) {
		log.info("goodsModify........");	
		//result : 수정된 "상품 정보"를 DB에 반영하는 메서드
		int result= adminMapper.goodsModify(vo);
		//상품 이미지 정보를 수정하는 코드 : 상품 수정이 정상적으로 되었을때만 수행
		//뷰로부터 상품 이미지 정보가 없으면 실행X이므로,상품 이미지 정보 존재 여부를 확인해야함
		if(result==1&&vo.getImageList()!=null&&vo.getImageList().size()>0) {
			//vam_image테이블의 해당 상품의 이미지 정보를 모두 삭제하기
			adminMapper.deleteImageAll(vo.getBookId());
			vo.getImageList().forEach(attach->{
				attach.setBookId(vo.getBookId());
				adminMapper.imageEnroll(attach);
			});
		}
		return result;
	}

	/* 상품 정보 삭제 */
	@Transactional
	@Override
	public int goodsDelete(int bookId) {
		log.info("goodsDelete..........");
		//이미지 정보 삭제 수행
		adminMapper.deleteImageAll(bookId);
		return adminMapper.goodsDelete(bookId);
	}

	@Override
	public List<AttachImageVO> getAttachInfo(int bookId) {
		log.info("getAttachInfo........");
		return adminMapper.getAttachInfo(bookId);
	}
	
	/*[43]주문현황*/
	/* 주문 상품 리스트 */
	@Override
	public List<OrderDTO> getOrderList(Criteria cri) {
		return adminMapper.getOrderList(cri);
	}
	
	/* 주문 총 갯수 */
	@Override
	public int getOrderTotal(Criteria cri) {
		return adminMapper.getOrderTotal(cri);
	}
}
