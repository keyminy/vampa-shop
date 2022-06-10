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
         					<option value="T">책 제목</option>
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
										<td class="image"></td>
										<td class="detail">
											<div class="category">
												[${list.cateName}]
											</div>
											<div class="title">
												${list.bookName}
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
						<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
						<input type="hidden" name="type" value="${pageMaker.cri.type}">
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
				$("select[name='type']").val(selectedType).attr("selected", "selected");	
			}
		});
	</script>
</body>
</html>