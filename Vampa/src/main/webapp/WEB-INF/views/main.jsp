<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/main.css" />
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
								<input type="text" name="keyword"/>
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
			<div class="navi_bar_area">
				<h1>navi area</h1>
			</div>
			<div class="content_area">
				<h1>content area</h1>
			</div>
		</div>
	</div>
</body>
</html>