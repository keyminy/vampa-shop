package com.vampa.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.vampa.mapper.AuthorMapper;
import com.vampa.model.AuthorVO;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class AuthorServiceTests {
	
	@Autowired
    private AuthorService service;
    
	@Test
	public void authorEnrollTest() throws Exception {
		AuthorVO author = new AuthorVO();
		
		author.setNationId("01");
		author.setAuthorName("테스트");
		author.setAuthorIntro("테스트 소개");
	        
	    service.authorEnroll(author);
	}
}
