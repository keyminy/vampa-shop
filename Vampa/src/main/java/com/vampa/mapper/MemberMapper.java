package com.vampa.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.vampa.model.MemberVO;

@Mapper
public interface MemberMapper {
	public void memberJoin(MemberVO member);
	
	// 아이디 중복 검사
	public int idCheck(String memberId);
	
	  /* 로그인 */
    public MemberVO memberLogin(MemberVO member);
    
    /* 주문페이지, 주문자 주소 정보 */
    public MemberVO getMemberInfo(String memberId);
}
