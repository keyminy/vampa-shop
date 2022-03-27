package com.vampa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vampa.mapper.AuthorMapper;
import com.vampa.model.AuthorVO;
import com.vampa.model.Criteria;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AuthorService {
    
	@Autowired
    AuthorMapper authorMapper;
    
    public void authorEnroll(AuthorVO author) throws Exception {
        authorMapper.authorEnroll(author);
    }
    public List<AuthorVO> authorGetList(Criteria cri) throws Exception{
    	log.info("(service)authorGetList()............" + cri);
	   return authorMapper.authorGetList(cri);
    }
    /* 작가 총 수 */
    public int authorGetTotal(Criteria cri) throws Exception {
        log.info("(service)authorGetTotal()......." + cri);
        return authorMapper.authorGetTotal(cri);
    }
    
    /* 작가 상세 페이지 */
    public AuthorVO authorGetDetail(int authorId) throws Exception{
    	log.info("authorGetDetail........ " + authorId);
    	return authorMapper.authorGetDetail(authorId);
    }
 
	/* 작가 정보 수정 */
	public int authorModify(AuthorVO author) throws Exception{
		log.info("(service) authorModify........." + author);
		return authorMapper.authorModify(author);	
	}
}

