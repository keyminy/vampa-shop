package com.vampa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vampa.model.AttachImageVO;
import com.vampa.model.BookVO;
import com.vampa.model.CateVO;
import com.vampa.model.Criteria;

@Mapper
public interface AdminMapper {

	/*상품 등록*/
	public void bookEnroll(BookVO book);
	
	/* 이미지 정보 테이블(vam_image)에 삽입 */
	public void imageEnroll(AttachImageVO vo);
	
	/* 카테고리 리스트 */
	public List<CateVO> cateList();
	
	/* 상품 리스트 */
	public List<BookVO> goodsGetList(Criteria cri);
	
	/* 상품 총 갯수 */
	public int goodsGetTotal(Criteria cri);
	
	/* 상품 조회 페이지 */
	public BookVO goodsGetDetail(int bookId);
	
	/* 상품 수정 */
	public int goodsModify(BookVO vo);
	
	/* 상품 정보 삭제 */
	public int goodsDelete(int bookId);
	
	/* 지정 상품 이미지 전체 삭제 */
	public void deleteImageAll(int bookId);
	
	/* 어제자 날짜 이미지 리스트(배치작업) */
	public List<AttachImageVO> checkFileList();
	
	/* 지정 상품 이미지 정보 얻기(서버 이미지 파일 삭제) */
	public List<AttachImageVO> getAttachInfo(int bookId);
}
