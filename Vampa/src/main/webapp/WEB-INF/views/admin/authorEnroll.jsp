<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/admin/authorEnroll.css">

<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
</head>
</head>
<body>

	<%@include file="../includes/admin/header.jsp"%>
	<div class="admin_content_wrap">
		<div class="admin_content_subject">
			<span>작가 등록</span>
		</div>
		<div class="admin_content_main">
			<form action="/admin/authorEnroll.do" method="post" id="enrollForm">
				<div class="form_section">
					<div class="form_section_title">
						<label>작가 이름</label>
					</div>
					<div class="form_section_content">
						<input name="authorName"> <span id="warn_authorName">작가
							이름을 입력 해주세요.</span>
					</div>
				</div>
				<div class="form_section">
					<div class="form_section_title">
						<label>소속 국가</label>
					</div>
					<div class="form_section_content">
						<select name="nationId">
							<option value="none" selected>=== 선택 ===</option>
							<option value="01">국내</option>
							<option value="02">국외</option>
						</select> <span id="warn_nationId">소속 국가를 선택해주세요.</span>
					</div>
				</div>
				<div class="form_section">
					<div class="form_section_title">
						<label>작가소개</label>
					</div>
					<div class="form_section_content">
						<input name="authorIntro" type="text"> <span
							id="warn_authorIntro">작가 소개를 입력 해주세요.</span>
					</div>
				</div>
			</form>
			<div class="btn_section">
				<button id="cancelBtn" class="btn">취 소</button>
				<button id="enrollBtn" class="btn enroll_btn">등 록</button>
			</div>
		</div>
	</div>
	<%@include file="../includes/admin/footer.jsp"%>
	<script>
		//등록버튼
		$("#enrollBtn").click(function() {
			/* 검사 통과 유무 변수 */
			let nameCheck = false; //작가이름
			let nationCheck = false; //소속 국가
			let introCheck = false; //작가 소개

			/*입력값 변수*/
			let authorName = $('input[name=authorName]').val(); //작가 이름
			let nationId = $('select[name=nationId]').val(); // 소속 국가
			let authorIntro = $('input[name=authorIntro]').val(); //작가 소개
			/*공란 경고 span태그 */
			let wAuthorName = $('#warn_authorName');
			let wNationId = $('#warn_nationId');
			let wAuthorIntro = $('#warn_authorIntro');

			/*(14-3강) : 입력란이 '공란'일 경우, span태그가 등장함과 동시에 검사 통과 유무 변수에 false값 대입*/
			/*반대로, 입력란이 '공란'이 아니면 span태그를 dp:none으로 숨기고, 검사 통과 유무 변수에 true값 대입*/

			/*작가 이름 공란 체크*/
			if (authorName === '') {
				//공백이면 => span태그 빨간줄 활성화 + 검사통과유무변수 false
				wAuthorName.css('display', 'block');
				nameCheck = false;
			} else {
				//공백이 아닐때
				wAuthorName.css('display', 'none');
				nameCheck = true;
			}
			/* 소속 국가 공란 체크(select박스) */
			if (nationId === 'none') {
				wNationId.css('display', 'block');
				nationCheck = false;
			} else {
				wNationId.css('display', 'none');
				nationCheck = true;
			}
			/* 작가 소개 공란 체크 */
			if (authorIntro === '') {
				wAuthorIntro.css('display', 'block');
				introCheck = false;
			} else {
				wAuthorIntro.css('display', 'none');
				introCheck = true;
			}
			/*최종 검사 : submit()부분 */
			if (introCheck && nationCheck && nameCheck) {
				$("#enrollForm").submit();
			} else {
				if (!nameCheck) {
					alert("이름은 필수 입력 사항입니다.");
					wAuthorName.focus();
				}
				return;
			}
		});

		//취소버튼
		$("#cancelBtn").click(function() {
			location.href = "/admin/authorManage";
		});
	</script>
</body>
</html>
