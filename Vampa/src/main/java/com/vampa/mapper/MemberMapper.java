package com.vampa.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.vampa.model.MemberVO;

@Mapper
public interface MemberMapper {
	public void memberJoin(MemberVO member);
}
