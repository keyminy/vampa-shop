package com.vampa.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vampa.model.AttachImageVO;
import com.vampa.model.AuthorVO;
import com.vampa.model.BookVO;
import com.vampa.model.CateVO;
import com.vampa.model.Criteria;
import com.vampa.model.PageDTO;
import com.vampa.service.AdminService;
import com.vampa.service.AuthorService;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/admin")
@Log4j2
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private AuthorService authorService;

	/* 관리자 메인 페이지 이동 */
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public void adminMainGET() throws Exception {

		log.info("관리자 페이지 이동");

	}

	/* 상품 관리(상품목록) 페이지 접속 */
	@RequestMapping(value = "goodsManage", method = RequestMethod.GET)
	public void goodsManageGET(Criteria cri, Model model) throws Exception {
		log.info("상품 관리(상품목록) 페이지 접속, cri : " + cri);
		/* 상품 리스트 데이터 */
		List<BookVO> list = adminService.goodsGetList(cri);
		if (!list.isEmpty()) {
			model.addAttribute("list", list);
		} else {
			model.addAttribute("listCheck", "empty");
			return;
		}
		/* 페이지 인터페이스 데이터 */
		int total = adminService.goodsGetTotal(cri);
		PageDTO pageMaker = new PageDTO(cri, total);
		model.addAttribute("pageMaker", pageMaker);
	}

	/* 상품 등록 페이지 접속 */
	@RequestMapping(value = "goodsEnroll", method = RequestMethod.GET)
	public void goodsEnrollGET(Model model) throws Exception {
		log.info("상품 등록 페이지 접속");
		ObjectMapper objm = new ObjectMapper();
		List<CateVO> list = adminService.cateList();
		// 자바 객체 => String 타입의 JSON형식 데이터로 변환해줌
		String cateList = objm.writeValueAsString(list);
		model.addAttribute("cateList", cateList);
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

	/* goodsEnroll에서 첨부파일 업로드 */
	@PostMapping("/uploadAjaxAction")
	public ResponseEntity<List<AttachImageVO>> uploadAjaxActionPOST(MultipartFile[] uploadFile) {
		log.info("uploadAjaxActionPOST......");
		/* 이미지 파일 체크 */
		for(MultipartFile multipartFile : uploadFile) {
			/* 전달받은 uploadFile을 File객체로 만들어준다 */
			File checkfile = new File(multipartFile.getOriginalFilename());
			String type = null; //MIME TYPE저장
			try {
				type = Files.probeContentType(checkfile.toPath());
				log.info("MIME TYPE : " + type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!type.startsWith("image")) {
				//MIME_TYPE이 이미지가 아닌 경우 실행
				List<AttachImageVO> list = null;
				return new ResponseEntity<>(list,HttpStatus.BAD_REQUEST);
			}
		}//end 이미지 파일체크 for
		
		String uploadFolder = "D:/dev/vamupload";
		
		/* 날짜 폴더 경로 생성 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		String datePath = str.replace("-",File.separator);
		/* 폴더 생성 부분 */
		//첫번째 인수 : 부모경로, 두번째 인수 : 자식경로
		File uploadPath = new File(uploadFolder,datePath);
		//System.out.println(uploadPath.toString());
		/*계속 폴더를 생성하는 것을 방지하기 위해
		 * 대상 파일/디렉터리가 존재하는지 유무를 판단하고 폴더 생성해야지 */
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();//여러개 폴더 생성
		}
		
		/* 이미지 정보를 담는 객체 */
		List<AttachImageVO> list = new ArrayList();
		
		for(MultipartFile multipartFile : uploadFile) {
			/* 이미지 정보 객체 */
			AttachImageVO vo = new AttachImageVO();
			/* 파일 이름 */
			String uploadFileName = multipartFile.getOriginalFilename();
			vo.setFileName(uploadFileName);
			vo.setUploadPath(datePath);
			
			/* uuid 적용 파일 이름 */
			String uuid = UUID.randomUUID().toString();
			vo.setUuid(uuid);
			uploadFileName = uuid + "_" + uploadFileName;
			
			/* 파일 경로 + 파일 이름을 합친 File객체 만들기 */
			File saveFile = new File(uploadPath,uploadFileName);
			/* 파일 저장 : transferTo() */
			try {
				multipartFile.transferTo(saveFile);
				/* (M1)썸네일 생성(ImageIO) */
//				File thumbnailFile = new File(uploadPath,"s_"+uploadFileName);
//				BufferedImage bo_image = ImageIO.read(saveFile);
//				/* 축소 비율 설정 */
//				double ratio = 3;
//				/* 넓이,높이 */
//				int width = (int)(bo_image.getWidth()/ratio);
//				int height = (int)(bo_image.getHeight()/ratio);
//				BufferedImage bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
//				Graphics2D graphic = bt_image.createGraphics();
//				graphic.drawImage(bo_image, 0, 0, width, height,null);
//				ImageIO.write(bt_image,"jpg", thumbnailFile);
				
				/* (M2)썸네일 생성(thumbnailaotor) */
				File thumbnailFile = new File(uploadPath,"s_"+uploadFileName);
				BufferedImage bo_image = ImageIO.read(saveFile);
				//비율
				double ratio = 3;
				//넓이,높이
				int width = (int)(bo_image.getWidth()/ratio);
				int height = (int)(bo_image.getHeight()/ratio);
				Thumbnails.of(saveFile)
						  .size(width,height)
						  .toFile(thumbnailFile);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(vo);
		}//end for
		ResponseEntity<List<AttachImageVO>> result = new ResponseEntity<List<AttachImageVO>>(list,HttpStatus.OK); 
		return result;
	}

	/* 작가 검색 팝업창 */
	@GetMapping("/authorPop")
	public void authorPopGET(Criteria cri, Model model) throws Exception {
		log.info("authorPopGET.......");

		cri.setAmount(5);
		/* 게시물 출력 데이터 */
		List<AuthorVO> list = authorService.authorGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list);
		} else {
			// 작가가존재 하지 않을 경우
			model.addAttribute("listCheck", "empty");
		}
		/* 페이지 이동 인터페이스 데이터 */
		int total = authorService.authorGetTotal(cri);
		PageDTO pageMaker = new PageDTO(cri, total);
		model.addAttribute("pageMaker", pageMaker);
	}

	/* 작가 등록 페이지 접속 */
	@RequestMapping(value = "authorEnroll", method = RequestMethod.GET)
	public void authorEnrollGET() throws Exception {
		log.info("작가 등록 페이지 접속");
	}

	/* 작가 관리 페이지 접속 */
	@RequestMapping(value = "authorManage", method = RequestMethod.GET)
	public void authorManageGET(Criteria cri, Model model) throws Exception {
		log.info("작가 관리 페이지 접속" + cri);
		// 작가 목록 출력 데이터 : list
		List<AuthorVO> list = authorService.authorGetList(cri);
		if (!list.isEmpty()) {
			model.addAttribute("list", list);
		} else {
			model.addAttribute("listCheck", "empty");
		}

		/* 페이지 이동 인터페이스 데이터 */
		int total = authorService.authorGetTotal(cri);
		PageDTO pageMaker = new PageDTO(cri, total);
		model.addAttribute("pageMaker", pageMaker);

	}

	/* 작가 등록 */
	@RequestMapping(value = "authorEnroll.do", method = RequestMethod.POST)
	public String authorEnrollPOST(AuthorVO author, RedirectAttributes rttr) throws Exception {
		log.info("authorEnroll : " + author);
		authorService.authorEnroll(author); // 작가등록 쿼리 실행
		rttr.addFlashAttribute("enroll_result", author.getAuthorName());
		// 작가 목록 페이지로 리턴
		return "redirect:/admin/authorManage";
	}

	/* 작가 상세 페이지,수정페이지 */
	@GetMapping({ "/authorDetail", "/authorModify" })
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
	public String authorModifyPOST(AuthorVO author, RedirectAttributes rttr) throws Exception {
		log.info("authorModifyPOST......." + author);
		int result = authorService.authorModify(author);
		rttr.addFlashAttribute("modify_result", result);
		return "redirect:/admin/authorManage";
	}

	/* 상품 조회 페이지 */
	@GetMapping({ "/goodsDetail", "/goodsModify" })
	public void goodsGetInfoGET(int bookId, Criteria cri, Model model) throws JsonProcessingException {
		log.info("goodsGetInfo()........." + bookId);
		log.info("크라이테리아? : ", cri);
		ObjectMapper mapper = new ObjectMapper();
		/* 카테고리 리스트 데이터 */
		model.addAttribute("cateList", mapper.writeValueAsString(adminService.cateList()));
		/* 목록 페이지 조건 정보 */
		model.addAttribute("cri", cri);
		/* 조회 페이지 정보 */
		model.addAttribute("goodsInfo", adminService.goodsGetDetail(bookId));
	}

	/* 상품 정보 수정 */
	@PostMapping("/goodsModify")
	public String goodsModifyPOST(BookVO vo, RedirectAttributes rttr) {
		log.info("goodsModifyPOST.........." + vo);
		int result = adminService.goodsModify(vo);
		rttr.addFlashAttribute("modify_result", result);
		return "redirect:/admin/goodsManage";
	}

	/* 상품 정보 삭제 */
	@PostMapping("/goodsDelete")
	public String goodsDeletePOST(int bookId, RedirectAttributes rttr) {
		log.info("goodsDeletePOST..........");
		int result = adminService.goodsDelete(bookId);
		rttr.addFlashAttribute("delete_result", result);
		return "redirect:/admin/goodsManage";
	}

	/* 작가 정보 삭제 */
	/*
	 * 참조되지 않는 행을 지울 땐, 삭제를 수행하고 '작가 목록' 페이지로 1을 전송 참조되고 있는 행을 지울려고해서 예외가 발생하면 '작가
	 * 목록' 페이지로 2를 전송
	 */
	@PostMapping("/authorDelete")
	public String authorDeletePost(int authorId, RedirectAttributes rttr) {
		log.info("authorDeletePOST..........");
		int result = 0;
		try {
			result = authorService.authorDelete(authorId);
		} catch (Exception e) {
			e.printStackTrace();
			result = 2;
			rttr.addFlashAttribute("delete_result", result);
			return "redirect:/admin/authorManage";
		}
		rttr.addFlashAttribute("delete_result", result);
		return "redirect:/admin/authorManage";
	}
}
