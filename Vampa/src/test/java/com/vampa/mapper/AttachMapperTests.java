package com.vampa.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class AttachMapperTests {
	
	@Autowired
	private AttachMapper attachMapper;
	
	/* 이미지 정보 반환 */
	@Test
	public void getAttachListTests() {
		int bookId = 16776;
		System.out.println("이미지 정보 : " + attachMapper.getAttachList(bookId));
	}
}
