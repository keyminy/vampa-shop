package com.vampa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vampa.mapper.AdminMapper;
import com.vampa.mapper.AttachMapper;
import com.vampa.mapper.BookMapper;
import com.vampa.model.AttachImageVO;
import com.vampa.model.BookVO;
import com.vampa.model.CateFilterDTO;
import com.vampa.model.CateVO;
import com.vampa.model.Criteria;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BookServiceImpl implements BookService{

	@Autowired
	private BookMapper bookMapper;
	
	@Autowired
	private AttachMapper attachMapper;
	
	@Autowired
	private AdminMapper adminMapper;
	
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
		
		List<BookVO> list = bookMapper.getGoodsList(cri);
		//각 요소로 있는 BookVO객체 하나하나에 이미지 정보(AttachImageVO)정보를 추가해주는 작업
		list.forEach(book->{
			int bookId = book.getBookId();
			List<AttachImageVO> imageList = attachMapper.getAttachList(bookId);
			book.setImageList(imageList);
		});
		return list;
	}

	@Override
	public int goodsGetTotal(Criteria cri) {
		log.info("goodsGetTotal().......");
		return bookMapper.goodsGetTotal(cri);
	}

	@Override
	public List<CateVO> getCateCode1() {
		log.info("getCateCode1().........");
		return bookMapper.getCateCode1();
	}

	@Override
	public List<CateVO> getCateCode2() {
		log.info("getCateCode2().........");
		return bookMapper.getCateCode2();
	}

	@Override
	public List<CateFilterDTO> getCateInfoList(Criteria cri) {
		List<CateFilterDTO> filterInfoList = new ArrayList<CateFilterDTO>();

		String type = cri.getType();
		String[] typeArr = type.split("");
		String[] authorArr;
		
		for(String t : typeArr) {
			if(t.equals("A")) {
				authorArr = bookMapper.getAuthorIdList(cri.getKeyword());
				if(authorArr.length==0) {
					return filterInfoList;
				}
				cri.setAuthorArr(authorArr);
			}
		}
		//카테고리 코드들 얻기
		String[] cateList = bookMapper.getCateList(cri);
		/*구조 : getCateList()해서 얻어진 String[] cateList를 Criteria객체에 담아서,
		 * getCateInfo()메서드의 파라미터로 전달함 */
		
		/* 그 전에, 기존의 Criteria의 'cateCode'값을 유지해야함,저장해두자 */
		String tempCateCode = cri.getCateCode();
		//얻은 cateList모든 요소들을 순회하여 처리하기 for문
		for(String cateCode : cateList) {
			cri.setCateCode(cateCode);
			CateFilterDTO filterInfo = bookMapper.getCateInfo(cri);
			filterInfoList.add(filterInfo);
		}
		//criteria의 카레고리값 원래값 복원
		cri.setCateCode(tempCateCode);
		return filterInfoList;
	}

	@Override
	public BookVO getGoodsInfo(int bookId) {
		/*mapper의 getGoodsInfo()메서드가 반환해준 BookVO객체에 
		이미지 정보 데이터를 추가 해주기 위해,imageList변수에 값을 부여해주어야함 
		AdminMapper의 getAttachInfo()메서드 활용하기 */
		BookVO goodsInfo = bookMapper.getGoodsInfo(bookId);
		//BookVO에 이미지 정보 추가
		goodsInfo.setImageList(adminMapper.getAttachInfo(bookId));
		return goodsInfo;
	}
}
