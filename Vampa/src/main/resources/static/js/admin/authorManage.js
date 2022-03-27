		$(document).ready(function(){
			checkResult(result);
			function checkResult(result){
				if(result === ''){
					return;
				}
				alert("작가 \"" + result + "\" 을 등록하였습니다.");
			}
			//수정 시 메시지 표시
			checkmResult(mresult);
			function checkmResult(mresult){
				if(mresult==='1'){
					alert("작가 정보 수정을 완료 하였습니다.");
				}else if(mresult==='0'){
					alert("작가 정보 수정을 하지 못했습니다.");
				}
			}
		});