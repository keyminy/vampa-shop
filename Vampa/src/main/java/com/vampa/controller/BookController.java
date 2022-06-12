package com.vampa.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vampa.mapper.AttachMapper;
import com.vampa.model.AttachImageVO;
import com.vampa.model.BookVO;
import com.vampa.model.Criteria;
import com.vampa.model.PageDTO;
import com.vampa.service.AttachService;
import com.vampa.service.BookService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class BookController {
	
	@Autowired
	private AttachService attachService;
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public void mainPageGET(Model model) {
		log.info("메인 페이지 진입");
		model.addAttribute("cate1", bookService.getCateCode1());
		model.addAttribute("cate2", bookService.getCateCode2());
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
		return new ResponseEntity<List<AttachImageVO>>(attachService.getAttachList(bookId),HttpStatus.OK);
	}
	
	/* 상품 검색 */
	@GetMapping("search")
	public String searchGoodsGET(Criteria cri,Model model) {
		log.info("cri : " + cri);
		List<BookVO> list = bookService.getGoodsList(cri);
		log.info("pre list : " + list);
		if(!list.isEmpty()) {
			model.addAttribute("list",list);
			log.info("list : " + list);
		}else {
			model.addAttribute("listcheck","empty");
			return "search";
		}
		model.addAttribute("pageMaker",new PageDTO(cri, bookService.goodsGetTotal(cri)));
		
		//getCateInfoList()메서드는 type이 'A','AC','T','TC'인 경우만 호출되어야함.
		String[] typeArr = cri.getType().split("");
		for(String s : typeArr) {
			if(s.equals("T") || s.equals("A")) {
				model.addAttribute("filter_info",bookService.getCateInfoList(cri));
			}
		}
		return "search";
	}
	
	/* 상품 상세 보기 */
	@GetMapping("/goodsDetail/{bookId}")
	public String goodsDetailGET(@PathVariable("bookId") int bookId,Model model) {
		log.info("goodsDetailGET()...........");
		model.addAttribute("goodsInfo",bookService.getGoodsInfo(bookId));
		return "goodsDetail";
	}
}
