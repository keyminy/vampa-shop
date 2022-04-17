package com.vampa.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vampa.model.AuthorVO;
import com.vampa.model.BookVO;
import com.vampa.model.CateVO;
import com.vampa.model.Criteria;
import com.vampa.model.PageDTO;
import com.vampa.service.AdminService;
import com.vampa.service.AuthorService;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/admin")
@Log4j2
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private AuthorService authorService;
	
	
	  /* 관리자 메인 페이지 이동 */
    @RequestMapping(value="main", method = RequestMethod.GET)
    public void adminMainGET() throws Exception{
        
        log.info("관리자 페이지 이동");
        
    }
    
    
    /* 상품 관리(상품목록) 페이지 접속 */
    @RequestMapping(value = "goodsManage", method = RequestMethod.GET)
    public void goodsManageGET(Criteria cri, Model model) throws Exception{
        log.info("상품 관리(상품목록) 페이지 접속, cri : " + cri);
        /* 상품 리스트 데이터 */
        List<BookVO> list = adminService.goodsGetList(cri);
        if(!list.isEmpty()) {
        	model.addAttribute("list",list);
        }else {
        	model.addAttribute("listCheck","empty");
        	return;
        }
        /* 페이지 인터페이스 데이터 */
        int total = adminService.goodsGetTotal(cri);
        PageDTO pageMaker = new PageDTO(cri, total);
        model.addAttribute("pageMaker",pageMaker);
    }
    
    /* 상품 등록 페이지 접속 */
    @RequestMapping(value = "goodsEnroll", method = RequestMethod.GET)
    public void goodsEnrollGET(Model model) throws Exception{
        log.info("상품 등록 페이지 접속");
        ObjectMapper objm = new ObjectMapper();
        List<CateVO> list = adminService.cateList();
        //자바 객체 => String 타입의 JSON형식 데이터로 변환해줌
        String cateList = objm.writeValueAsString(list);
        model.addAttribute("cateList",cateList);
        log.info("변경 전.........." + list);
		log.info("변경 후.........." + cateList);
    }
    /* 상품 등록 */
	@PostMapping("/goodsEnroll")
	public String goodsEnrollPOST(BookVO book, RedirectAttributes rttr) {
		log.info("goodsEnrollPOST......" + book);
		adminService.bookEnroll(book);
		rttr.addFlashAttribute("enroll_result", book.getBookName());
		return "redirect:/admin/goodsManage";
	}	
	/* 작가 검색 팝업창 */
	@GetMapping("/authorPop")
	public void authorPopGET(Criteria cri,Model model) throws Exception{
		log.info("authorPopGET.......");
		
		cri.setAmount(5);
		/* 게시물 출력 데이터 */
		List<AuthorVO> list = authorService.authorGetList(cri);
		
		if(!list.isEmpty()) {
			model.addAttribute("list",list);
		}else {
			//작가가존재 하지 않을 경우
			model.addAttribute("listCheck","empty");
		}
        /* 페이지 이동 인터페이스 데이터 */
        int total = authorService.authorGetTotal(cri);
        PageDTO pageMaker = new PageDTO(cri, total);
        model.addAttribute("pageMaker",pageMaker);
	}
    
    /* 작가 등록 페이지 접속 */
    @RequestMapping(value = "authorEnroll", method = RequestMethod.GET)
    public void authorEnrollGET() throws Exception{
        log.info("작가 등록 페이지 접속");
    }
    
    /* 작가 관리 페이지 접속 */
    @RequestMapping(value = "authorManage", method = RequestMethod.GET)
    public void authorManageGET(Criteria cri,Model model) throws Exception{
        log.info("작가 관리 페이지 접속" + cri);
        //작가 목록 출력 데이터 : list
        List<AuthorVO> list = authorService.authorGetList(cri);
        if(!list.isEmpty()) {
        	model.addAttribute("list",list);        	
        }else{
        	model.addAttribute("listCheck","empty");        	
        }
        
        /* 페이지 이동 인터페이스 데이터 */
        int total = authorService.authorGetTotal(cri);
        PageDTO pageMaker = new PageDTO(cri, total);
        model.addAttribute("pageMaker",pageMaker);
        
    }    
 
    /* 작가 등록 */
    @RequestMapping(value="authorEnroll.do", method = RequestMethod.POST)
    public String authorEnrollPOST(AuthorVO author, RedirectAttributes rttr) throws Exception{
    	log.info("authorEnroll : " + author);
    	authorService.authorEnroll(author); //작가등록 쿼리 실행
    	rttr.addFlashAttribute("enroll_result", author.getAuthorName());
    	//작가 목록 페이지로 리턴
    	return "redirect:/admin/authorManage";
    }
    
	/* 작가 상세 페이지,수정페이지 */
	@GetMapping({"/authorDetail","/authorModify"})
	public void authorGetInfoGET(int authorId, Criteria cri, Model model) throws Exception {
		log.info("authorDetail......." + authorId);
		log.info("detail cri : " + cri);
		/* 작가 관리 페이지 정보 */
		model.addAttribute("cri", cri);
		/* 선택 작가 정보 */
		model.addAttribute("authorInfo", authorService.authorGetDetail(authorId));
	}
	
	/* 작가 정보 수정 */
	@PostMapping("/authorModify")
	public String authorModifyPOST(AuthorVO author,RedirectAttributes rttr) throws Exception{
		log.info("authorModifyPOST......." + author);
		int result = authorService.authorModify(author);
		rttr.addFlashAttribute("modify_result", result);
		return "redirect:/admin/authorManage";
	}
}
