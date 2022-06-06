package com.vampa.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vampa.mapper.AttachMapper;
import com.vampa.model.AttachImageVO;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class BookController {
	
	@Autowired
	private AttachMapper attachMapper;
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public void mainPageGET() {
		log.info("메인 페이지 진입");
	}
	
	/* 이미지 출력 */
	@GetMapping("/display")
	public ResponseEntity<byte[]> getImage(String fileName){
		String uploadFolder = "D:/dev/vamupload/";
		File file = new File(uploadFolder+fileName);
		ResponseEntity<byte[]> result = null;
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type",Files.probeContentType(file.toPath()));
			result=new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	/* 이미지 정보 반환(view페이지) */
	@GetMapping("/getAttachList")
	public ResponseEntity<List<AttachImageVO>> getAttachList(int bookId){
		//반환 데이터 : 이미지 정보데이터(List<AttachImageVO>)
		log.info("getAttachList.........Controller bookId : " + bookId);
		return new ResponseEntity<List<AttachImageVO>>(attachMapper.getAttachList(bookId),HttpStatus.OK);
	}
}
