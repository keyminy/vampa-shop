<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/search.css" />
<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
</head>
<body>
	<div class="wrapper">
		<div class="wrap">
			<div class="top_gnb_area">
				<h1>gnb area</h1>
			</div>
			<div class="top_area">
				<div class="logo_area">
					<a href="/main"><img src="/img/vamlogo.png"></a>
				</div>
				<div class="search_area">
					<div class="search_wrap">
						<form action="/search" id="searchForm" method="get">
							<div class="search_input">
        				<select name="type">
         					<option value="T" selected="selected">책 제목</option>
         					<option value="A">작가</option>
         				</select>
         				<!-- 검색 인터페이스에 사용자가 입력한 값 가지게 value -->
								<input type="text" name="keyword" value="<c:out value="${pageMaker.cri.keyword}"/>"/>
								<button class="btn search_btn">검 색</button>
							</div>
						</form>
					</div>
				</div>
				<div class="login_area">
					<div class="login_button">
						<a href="/member/login">로그인</a>
					</div>
					<span><a href="/member/join">회원가입</a></span>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="content_area">
				<!-- 게시물o -->
				<c:if test="${listcheck!='empty'}">
					<div class="search_filter">
						<div class="filter_button_wrap">
							<button class="filter_button filter_active" id="filter_button_a">국내</button>
							<button class="filter_button" id="filter_button_b">외국</button>
					</div>
							<div class="filter_content filter_a">
								<c:forEach items="${filter_info}" var="filter">
									<c:if test="${filter.cateGroup eq '1'}">
										<a href="${filter.cateCode}">${filter.cateName}(${filter.cateCount})</a>									
									</c:if>
								</c:forEach>
							</div>
							<div class="filter_content filter_b">
								<c:forEach items="${filter_info}" var="filter">
									<c:if test="${filter.cateGroup eq '2'}">
										<a href="${filter.cateCode}">${filter.cateName}(${filter.cateCount})</a>
									</c:if>
								</c:forEach>
							</div>
							<!-- form태그로,카테고리필터의 a태그를 제어하기 -->
							<form action="/search" id="filter_form" method="get">
								<input type="hidden" name="keyword"/>
								<input type="hidden" name="cateCode"/>
								<input type="hidden" name="type"/>
							</form>		
					</div>
					<div class="list_search_result">
						<table class="type_list">
							<colgroup>
								<col width="110">
								<col width="*">
								<col width="120">
								<col width="120">
								<col width="120">
							</colgroup>
							<tbody id="searchList">
								<c:forEach items="${list}" var="list">
									<tr>
										<td class="image">
											<div class="image_wrap" data-bookid="${list.imageList[0].bookId}" data-path="${list.imageList[0].uploadPath}"
											data-uuid="${list.imageList[0].uuid}" data-filename="${list.imageList[0].fileName}">
												<img/>
											</div>
										</td>
										<td class="detail">
											<div class="category">
												[${list.cateName}]
											</div>
											<div class="title">
												<a href="/goodsDetail/${list.bookId}">
													${list.bookName}
												</a>
											</div>
											<div class="author">
												<fmt:parseDate var="publeYear" value="${list.publeYear}" pattern="yyyy-MM-dd"/>
												${list.authorName} 지음 | ${list.publisher} | <fmt:formatDate value="${publeYear}" pattern="yyyy-MM-dd"/>
											</div>
										</td>
										<td class="info">
											<div class="rating">
												평점(추후 추가)
											</div>
										</td>
										<td class="price">
											<div class="org_price">
												<del>
													<fmt:formatNumber value="${list.bookPrice}" pattern="#,### 원" />
												</del>
											</div>
											<div class="sell_price">
												<strong>
													<fmt:formatNumber value="${list.bookPrice * (1-list.bookDiscount)}" pattern="#,### 원" />
												</strong>
											</div>
										</td>
										<td class="option"></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					
					<!-- 페이지 이동 인터페이스 -->
					<div class="pageMaker_wrap">
						<ul class="pageMaker">
							<!-- 이전 버튼 -->
							<c:if test="${pageMaker.prev}">
								<li class="pageMaker_btn prev">
									<a href="${pageMaker.pageStart-1}">이전</a>
								</li>
							</c:if>
							<!-- 페이지 번호(반복) -->
							<c:forEach begin="${pageMaker.pageStart}" end="${pageMaker.pageEnd}" var="num">
								<li class="pageMaker_btn ${pageMaker.cri.pageNum == num ? 'active':''}">
         					<a href="${num}">${num}</a>
           			</li>	
							</c:forEach>
							<!-- 다음 버튼 -->
             	<c:if test="${pageMaker.next}">
             		<li class="pageMaker_btn next">
             			<a href="${pageMaker.pageEnd + 1 }">다음</a>
             		</li>
             	</c:if>
						</ul>
					</div>
					
					<form id="moveForm" action="/search" method="get" >
						<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
						<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
						<c:if test="${not empty pageMaker.cri.keyword}">
							<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
						</c:if>
						<input type="hidden" name="type" value="${pageMaker.cri.type}">
						<input type="hidden" name="cateCode" value="<c:out value="${pageMaker.cri.cateCode}"/>">
					</form>
				</c:if>
				<!-- 게시물x -->
				<c:if test="${listcheck=='empty'}">
					<div class="table_empty">
						검색결과가 없습니다.
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<script>

	/* 페이지 이동 버튼 */
    const moveForm = $('#moveForm');
    
		$(".pageMaker_btn a").on("click", function(e){
			e.preventDefault();
			moveForm.find("input[name='pageNum']").val($(this).attr("href"));
			moveForm.submit();
		});
		$(document).ready(function(){

		/* 검색창에서, 사용자가 사용한 type의 option태그 selected속성 부여 */
			//검색 타입 selected
			const selectedType = '<c:out value="${pageMaker.cri.type}"/>';
			if(selectedType != ""){
				if(selectedType.includes("T")){
					$("select[name='type']").val("T").attr("selected", "selected");	
				}else if(selectedType.includes("A")){
					$("select[name='type']").val("A").attr("selected", "selected");	
				}
			}
			
			/* 상품 이미지는 목록 페이지가 만들어질 때,같이 호출되기때문에
			document.ready안에 작성한다*/
			/* 이미지 삽입 */
			$(".image_wrap").each(function(idx,obj){
				const bobj = $(obj);
				if(bobj.data("bookid")){
					const uploadPath = bobj.data("path");
					const uuid = bobj.data("uuid");
					const fileName = bobj.data("filename");
					// "/display" 매핑 메서드의 파라미터로 전달시킬 fileName 데이터를 만든 뒤 fileCallPath 변수에 저장을 해줍니다.
					const fileCallPath = encodeURIComponent(uploadPath+"/s_"+uuid+"_"+fileName);
					//img에 값넣기
					$(this).find("img").attr('src','/display?fileName='+fileCallPath);
				}else{
					$(this).find("img").attr('src', '/img/이미지없음.png');
				}
			});
			/* 검색 필터 */
			let buttonA = $("#filter_button_a");
			let buttonB = $("#filter_button_b");
			/*국내 buttonA클릭시, 
			1.filter_a <div>태그는 보여지고, filter_b <div>태그는 숨겨져야함
			2."국내"버튼 태그에 "filter_active"클래스 속성이 추가되고,"국외"에는 "filter_active"속성 없어지게 */
			buttonA.on("click",function(){
				$(".filter_b").css("display","none");
				$(".filter_a").css("display","block");
				buttonA.attr("class","filter_button filter_active");
				buttonB.attr("class","filter_button");
			});
			buttonB.on("click", function(){
				$(".filter_a").css("display", "none");
				$(".filter_b").css("display", "block");
				buttonB.attr("class", "filter_button filter_active");
				buttonA.attr("class", "filter_button");		
			});
			
			/*cateCode에 따라 국내,외국 도서 기본 select정하기 */
			let cateCode = '<c:out value="${pageMaker.cri.cateCode}"/>';
			if(cateCode.startsWith("1")){
				$(".filter_b").css("display","none");
				$(".filter_a").css("display","block");
				buttonA.attr("class","filter_button filter_active");
				buttonB.attr("class","filter_button");
			}else if(cateCode.startsWith("2")){
				$(".filter_a").css("display", "none");
				$(".filter_b").css("display", "block");
				buttonB.attr("class", "filter_button filter_active");
				buttonA.attr("class", "filter_button");		
			}
			
			/* [34~]필터링 정보 <a>태그를 클릭했을때,
			기존 검색결과에 + 필터링 된 상품목록 페이지로 이동할 수 있게 */
			$(".filter_content a").on("click",function(e){
				//a태그 클릭되어졌을때, 기본 동작 막기
				e.preventDefault();
				/*기존 type값 A혹은 T인데, 필터링된 상품 목록 페이지로 이동은
				카테고리 검색조건이 추가되어 type이 "AC" OR "AT"가 되는 것이다.
				그러므로,type에 "C"문자열을 붙여주는 코드를 추가해보자. */
				let type ='<c:out value="${pageMaker.cri.type}"/>';
				/* 필터링 검색 이후, 사용자가 다른 필터링 정보를 클릭하면
				C가 한번 더 붙으므로, type이 "T"이거나 "A"일때만 "C"문자열이 추가될 수 있도록 if문 */
				if(type==='A' || type === 'T'){
					type= type+'C';
				}
				/* 키워드와 카테고리 코드를 담을 변수 추가해주고, 해당 값으로 초기화 */
				let keyword = '<c:out value="${pageMaker.cri.keyword}"/>';
				let cateCode= $(this).attr("href");
				
				/*값을 얻었으니, 해당 값들을 #filter_form태그 안에 있는 각각의 input값으로 부여하기 */
				$("#filter_form input[name='keyword']").val(keyword);
				$("#filter_form input[name='cateCode']").val(cateCode);
				$("#filter_form input[name='type']").val(type);
				$("#filter_form").submit();
			});
		});
	</script>
</body>
</html>