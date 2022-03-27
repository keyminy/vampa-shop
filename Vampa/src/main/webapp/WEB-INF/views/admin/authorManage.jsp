<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/admin/authorManage.css">

<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
</head>
</head>
<body>

	<%@include file="../includes/admin/header.jsp"%>
	<div class="admin_content_wrap">
		<div class="admin_content_subject">
			<span>작가 관리</span>
		</div>
		<div class="author_table_wrap">
			<!-- 게시물이 있을 때 -->
			<c:if test="${listCheck!='empty'}">
				<table class="author_table">
					<thead>
						<tr>
							<td class="th_column_1">작가 번호</td>
							<td class="th_column_2">작가 이름</td>
							<td class="th_column_3">작가 국가</td>
							<td class="th_column_4">등록 날짜</td>
							<td class="th_column_5">수정 날짜</td>
						</tr>
					</thead>
					<c:forEach items="${list}" var="list">
						<tr>
							<td><c:out value="${list.authorId}" /></td>
							<td>
								<a href="${list.authorId}" class="move">
									<c:out value="${list.authorName}"/>
								</a>
							</td>
							<td><c:out value="${list.nationName}" /></td>
							<td><fmt:formatDate value="${list.regDate}"
									pattern="yyyy-MM-dd" /></td>
							<td><fmt:formatDate value="${list.updateDate}"
									pattern="yyyy-MM-dd" /></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			<!-- 게시물이 없을때 -->
			<c:if test="${listCheck=='empty'}">
				<div class="table_empty">
					등록된 작가가 없습니다.
				</div>
			</c:if>
		</div>
		<!-- 검색창 -->
		<div class="search_wrap">
			<form action="/admin/authorManage" id="searchForm" method="get">
				<div class="search_input">
					<input type="text" name="keyword" value='<c:out value="${pageMaker.cri.keyword}"></c:out>'/>
					<input type="hidden" name="pageNum" value='<c:out value="${pageMaker.cri.pageNum}"></c:out>'/>
					<input type="hidden" name="amount" value='<c:out value="${pageMaker.cri.amount}"></c:out>'/>
					<button class="btn search_btn">검 색</button>
				</div>
			</form>
		</div>
		<!-- 페이지 이동 인터페이스 영역 -->
		<div class="pageMaker_wrap">
			<ul class="pageMaker">
				<!-- 이전 버튼 -->
				<c:if test="${pageMaker.prev}">
					<li class="pageMaker_btn prev"><a
						href="${pageMaker.pageStart - 1}">이전</a></li>
				</c:if>
				<!-- 페이지 번호 -->
				<c:forEach begin="${pageMaker.pageStart}" end="${pageMaker.pageEnd}"
					var="num">
					<li class="pageMaker_btn ${pageMaker.cri.pageNum == num ? "active":""}">
						<a href="${num}">${num}</a>
					</li>
				</c:forEach>
				<!-- 다음 버튼 -->
				<c:if test="${pageMaker.next}">
					<li class="pageMaker_btn next"><a
						href="${pageMaker.pageEnd + 1 }">다음</a></li>
				</c:if>
			</ul>
		</div>
		<form action="/admin/authorManage" id="moveForm" method="get">
			<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
			<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
			<c:if test="${not empty pageMaker.cri.keyword}">
				<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
			</c:if>
		</form>
	</div>
	<%@include file="../includes/admin/footer.jsp"%>
	<script>
		let result = '<c:out value="${enroll_result}"/>';
		let mresult = '<c:out value="${modify_result}"/>';
	</script>
	<script type="text/javascript" src="/js/admin/authorManage.js"></script>
	<script>
		/* 페이지 이동 버튼 */
		let moveForm = $('#moveForm');
		let searchForm = $('#searchForm');
		
		$(".pageMaker_btn a").on("click", function(e) {
			e.preventDefault(); //a태그의 동작 멈추기
			moveForm.find("input[name='pageNum']").val($(this).attr("href"));
			moveForm.submit();
		});
		
		/* 작가 검색 버튼 동작 */
		$("#searchForm button").on("click",function(e){
			e.preventDefault();
			/* 검색 키워드 유효성 검사 */
			if(!searchForm.find("input[name='keyword']").val()){
				// keyword input 택스트에 글자가 없으면 경고 유효성 처리
				alert("키워드를 입력하세요!");
				return false;
			}
			//검색 시 기본 1페이지로 갈 수 있게
			searchForm.find("input[name='pageNum']").val("1");
			searchForm.submit();
		});
		/* 작가 상세 페이지로 이동 */
		$(".move").on("click",function(e){
			e.preventDefault();
			moveForm.append("<input type='hidden' name='authorId' value='"+$(this).attr("href")+"'>");
			moveForm.attr("action","/admin/authorDetail");
			moveForm.submit();
		});
	</script>
</body>
</html>
