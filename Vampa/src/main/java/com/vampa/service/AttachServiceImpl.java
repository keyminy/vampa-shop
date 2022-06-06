package com.vampa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vampa.mapper.AttachMapper;
import com.vampa.model.AttachImageVO;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AttachServiceImpl implements AttachService{

	@Autowired
	private AttachMapper attachMapper;
	
	@Override
	public List<AttachImageVO> getAttachList(int bookId) {
		log.info("getAttachList.....");
		return attachMapper.getAttachList(bookId);
	}

}
