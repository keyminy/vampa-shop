package com.vampa.mapper;

import java.util.List;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.vampa.model.AuthorVO;
import com.vampa.model.Criteria;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class AuthorMapperTests {

	@Autowired
	private AuthorMapper mapper;

	/* 작가 등록 테스트 */
	@Test
	public void authorEnroll() throws Exception {
		AuthorVO author = new AuthorVO();
		author.setNationId("01");
		author.setAuthorName("테스트");
		author.setAuthorIntro("테스트 소개");
		mapper.authorEnroll(author);
	}

	/* 작가 목록 테스트 */
	@Test
	public void authorGetListTest() throws Exception {
		Criteria cri = new Criteria(3, 10); // 3페이지 & 10개 행 표시
		cri.setKeyword("유홍준");

		List<AuthorVO> list = mapper.authorGetList(cri);
		list.forEach(x -> System.out.println("list : " + x));
	}
	
	/*작가 총 수*/
	@Test
	public void authorGetTotalTest() throws Exception{
		Criteria cri = new Criteria();
		cri.setKeyword("유홍준");
		int total = mapper.authorGetTotal(cri);
		System.out.println("total.........." + total);
	}
	
	/* 작가 상세 페이지 테스트 */
	@Test
	public void authorGetDetailTest() {
		int authorId = 899;
		AuthorVO author = mapper.authorGetDetail(authorId);
		System.out.println("author............. " + author);
	}
	
	/* 작가 정보 수정 */
	@Test
	public void authorModifyTest() {
		AuthorVO author = new AuthorVO();
		author.setAuthorId(1);
		System.out.println("수정 전................." + mapper.authorGetDetail(author.getAuthorId()));
		
		author.setAuthorName("수정");
		author.setNationId("01");
		author.setAuthorIntro("소개 수정 하였습니다.");
		
		mapper.authorModify(author);
		System.out.println("수정 후 ..................." + mapper.authorGetDetail(author.getAuthorId()));
	}
	
	/* 작가 정보 삭제 */
	@Test
	public void authorDeleteTest() {
		int authorId = 44;
		int result = mapper.authorDelete(authorId);
		if(result == 1) {
			System.out.println("삭제 성공");
		}
	}
}