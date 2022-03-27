package com.vampa.mapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vampa.model.MemberVO;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class MemberMapperTests {
	
	@Autowired
	private MemberMapper memberMapper;
	
	//회원가입 쿼리 테스트 메서드
		@Test
		public void memberJoin() throws Exception{
			MemberVO member = new MemberVO();
			
			member.setMemberId("test");			//회원 id
			member.setMemberPw("test");			//회원 비밀번호
			member.setMemberName("test");		//회원 이름
			member.setMemberMail("test");		//회원 메일
			member.setMemberAddr1("test");		//회원 우편번호
			member.setMemberAddr2("test");		//회원 주소
			member.setMemberAddr3("test");		//회원 상세주소
			
			memberMapper.memberJoin(member);			//쿼리 메서드 실행
			
		}
}
