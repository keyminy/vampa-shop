<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/admin/goodsDetail.css">
<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
	<script
	src="https://cdn.ckeditor.com/ckeditor5/33.0.0/classic/ckeditor.js"></script>
</head>
<body>
	<%@include file="../includes/admin/header.jsp"%>
	<div class="admin_content_wrap">
		<div class="admin_content_subject">
			<span>상품 상세</span>
		</div>

		<div class="admin_content_main">

			<div class="form_section">
				<div class="form_section_title">
					<label>책 제목</label>
				</div>
				<div class="form_section_content">
					<input name="bookName"
						value="<c:out value="${goodsInfo.bookName}"/>" disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>등록 날짜</label>
				</div>
				<div class="form_section_content">
					<input
						value="<fmt:formatDate value='${goodsInfo.regDate}' pattern='yyyy-MM-dd'/>"
						disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>최근 수정 날짜</label>
				</div>
				<div class="form_section_content">
					<input
						value="<fmt:formatDate value='${goodsInfo.updateDate}' pattern='yyyy-MM-dd'/>"
						disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>작가</label>
				</div>
				<div class="form_section_content">
					<input id="authorName_input" readonly="readonly"
						value="${goodsInfo.authorName }" disabled>

				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>출판일</label>
				</div>
				<div class="form_section_content">
					<input name="publeYear" autocomplete="off" readonly="readonly"
						value="<c:out value="${goodsInfo.publeYear}"/>" disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>출판사</label>
				</div>
				<div class="form_section_content">
					<input name="publisher"
						value="<c:out value="${goodsInfo.publisher}"/>" disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>책 카테고리</label>
				</div>
				<div class="form_section_content">
					<div class="cate_wrap">
						<span>대분류</span> <select class="cate1" disabled >
							<option value="none">선택</option>
						</select>
					</div>
					<div class="cate_wrap">
						<span>중분류</span> <select class="cate2"  disabled>
							<option value="none">선택</option>
						</select>
					</div>
					<div class="cate_wrap">
						<span>소분류</span> <select class="cate3" name="cateCode" disabled >
							<option value="none">선택</option>
						</select>
					</div>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>상품 가격</label>
				</div>
				<div class="form_section_content">
					<input name="bookPrice"
						value="<c:out value="${goodsInfo.bookPrice}"/>" disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>상품 재고</label>
				</div>
				<div class="form_section_content">
					<input name="bookStock"
						value="<c:out value="${goodsInfo.bookStock}"/>" disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>상품 할인율</label>
				</div>
				<div class="form_section_content">
					<input id="discount_interface" maxlength="2" disabled>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>책 소개</label>
				</div>
				<div class="form_section_content bit">
					<textarea name="bookIntro" id="bookIntro_textarea" disabled>${goodsInfo.bookIntro}</textarea>
				</div>
			</div>
			<div class="form_section">
				<div class="form_section_title">
					<label>책 목차</label>
				</div>
				<div class="form_section_content bct">
					<textarea name="bookContents" id="bookContents_textarea" disabled>${goodsInfo.bookContents}</textarea>
				</div>
			</div>

			<div class="btn_section">
				<button id="cancelBtn" class="btn">상품 목록</button>
				<button id="modifyBtn" class="btn enroll_btn">수정</button>
			</div>
		</div>


		<form id="moveForm" action="/admin/goodsManage" method="get">
			<input type="hidden" name="pageNum" value="${cri.pageNum}">
			<input type="hidden" name="amount" value="${cri.amount}">
			<input type="hidden" name="keyword" value="${cri.keyword}">
		</form>
	</div>
	<%@include file="../includes/admin/footer.jsp"%>
	<script>
		$(document).ready(function(){
			/* 할인율 값 삽입 */
			let bookDiscount = '<c:out value="${goodsInfo.bookDiscount}"/>'*100;
			$("#discount_interface").attr("value",bookDiscount);
			
			/* 출판일 값 가공 */
			let publeYear = '${goodsInfo.publeYear}';
			let length = publeYear.indexOf(" ");
			publeYear = publeYear.substring(0,length);
			$("input[name='publeYear']").attr("value",publeYear);
			
			/* 책 소개에 CkEditor추가 */
			ClassicEditor
				.create(document.querySelector('#bookIntro_textarea'))
				.then(editor=>{
					console.log(editor);
					editor.isReadOnly=true;
				})
				.catch(error=>{
					console.log(error);
				});
			
			/* 책 목차에 CkEditor추가 */
			ClassicEditor
				.create(document.querySelector('#bookContents_textarea'))
				.then(editor=>{
					console.log(editor);
					editor.isReadOnly=true;
				})
				.catch(error=>{
					console.log(error);
				});
			/* 카테고리 */
			let cateList = JSON.parse('${cateList}');
			let cate1Array = new Array();
			let cate2Array = new Array();
			let cate3Array = new Array();
			let cate1Obj = new Object();
			let cate2Obj = new Object();
			let cate3Obj = new Object();
			
			let cateSelect1 = $(".cate1");
			let cateSelect2 = $(".cate2");
			let cateSelect3 = $(".cate3");
			
			/* 카테고리 배열 초기화 메서드 */
			function makeCateArray(obj,array,cateList,tier){
				for(let i=0;i<cateList.length;i++){
					if(cateList[i].tier===tier){
						obj = new Object();
						obj.cateName = cateList[i].cateName;
						obj.cateCode = cateList[i].cateCode;
						obj.cateParent = cateList[i].cateParent;
						array.push(obj);
					}
				}
			}
			
			/* 배열 초기화 */
			makeCateArray(cate1Obj,cate1Array,cateList,1);
			makeCateArray(cate2Obj,cate2Array,cateList,2);
			makeCateArray(cate3Obj,cate3Array,cateList,3);
			
			/* 중,소분류 카테고리 */
			let targetCate2 = '';
			//소분류 변수에는 DB에 저장된 사용자가 선택한 카테고리 코드로 초기화 
			let targetCate3 = '${goodsInfo.cateCode}'; //102001(코드만 저장됨)
			//alert(targetCate3);
			
			//targetCate3변수를 cateParent,cateName값도 포함된 객체를 저장되도록
			for(let i=0;i<cate3Array.length;i++){
				if(targetCate3===cate3Array[i].cateCode){
					targetCate3 = cate3Array[i];
				}
			}//end for
			
			console.log("cate3Array : " , cate3Array)
			console.log('targetCate3 : ' + targetCate3);
			console.log('targetCate3.cateName : ' + targetCate3.cateName);
			console.log('targetCate3.cateCode : ' + targetCate3.cateCode);
			console.log('targetCate3.cateParent : ' + targetCate3.cateParent);
			
			/*소분류 select항목에 추가하는 코드 작성하기 */
			for(let i=0; i< cate3Array.length;i++){
				if(targetCate3.cateParent === cate3Array[i].cateParent){
					cateSelect3.append("<option value='"+cate3Array[i].cateCode+"'>"+cate3Array[i].cateName+"</option>");
				}
			}
			//DB에 저장된 값에 해당하는 카테고리 <option>태그에 selected 속성이 추가되도록 코드 작성
			$(".cate3 option").each(function(i,obj){
				//여기서 obj는 item값임 == <option value="102002">해외시</option>
				if(targetCate3.cateCode === obj.value){
					$(obj).attr("selected","selected");
				}		
			});
		/* 중분류 출력시키기 targetCate2변수에 선택되어야 할 항목 객체로 초기화 */
		for(let i=0;i<cate2Array.length;i++){
			if(targetCate3.cateParent === cate2Array[i].cateCode){
				targetCate2 = cate2Array[i];
			}
		}
		console.log("targetCate2",targetCate2);
		//중분류의 select태그에 option태그 추가
		for(let i=0;i<cate2Array.length;i++){
			if(targetCate2.cateParent === cate2Array[i].cateParent){
				cateSelect2.append("<option value='"+cate2Array[i].cateCode+"'>"+cate2Array[i].cateName+"</option>")
			}
		}
		$(".cate2 option").each(function(i,obj){
			if(targetCate2.cateCode===obj.value){
				$(obj).attr("selected","selected");
			}
		});
		/*대분류 출력시키기*/
		//대분류의 option태그들을 추가시키기
		for(let i=0;i<cate1Array.length;i++){
			cateSelect1.append("<option value='"+cate1Array[i].cateCode+"'>"+cate1Array[i].cateName+"</option>")
		}
		//targetCate2.cateParent값을 활용하여 대분류 중 선택되어야 할 option태그에 selected속성 추가
		$(".cate1 option").each(function(i,obj){
			if(targetCate2.cateParent === obj.value){
				$(obj).attr("selected","selected");
			}
		});
		
		/* 목록 이동 버튼 */
		$("#cancelBtn").on("click",function(e){
			e.preventDefault();
			$("#moveForm").submit();
		});
		/* 수정 페이지 이동 */
		$("#modifyBtn").on("click",function(e){
			e.preventDefault();
			let addInput = '<input type="hidden" name="bookId" value="${goodsInfo.bookId}">';
			$("#moveForm").append(addInput);
			$("#moveForm").attr("action","/admin/goodsModify");
			$("#moveForm").submit();
		});
	}); //end $(document).ready		
	</script>
</body>
</html>