<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link href="css/User/userForm.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
	function join(e) {
		e.preventDefault();
		
		let user_id = document.querySelector(".user_id"); //유저 아이디
		let user_pw = document.querySelector(".user_pw"); //유저 비밀번호
		let pw_check = document.querySelector(".pw_check"); //비밀번호 확인
		let user_mail = document.querySelector(".user_mail"); //유저 이메일
		let user_tell = document.querySelector(".user_tell"); //유저 전화번호
		
		if(user_pw.value !== pw_check.value){
			alert("비밀번호가 일치하지 않습니다.") //비밀번호 입력값과 비밀번호 입력값이 일치하지 않은 경우
		}else{
			//서버에 보낼 데이터
			let sendData = {
					user_id: user_id.value, //유저 아이디
					user_pw: user_pw.value, //유저 비밀번호
					user_mail: user_mail.value, //유저 이메일
					user_tell: user_tell.value //유저 전화번호
			}
			
			let sendDataStr = JSON.stringify(sendData); //객체를 JSON 문자열로 변환
			
			$.ajax({
				type: "post",
				url: "/testprj/join",
				contentType: "application/json",
				data: sendDataStr,
				success: function(data) { //회원가입에 성공했을 시
					alert("회원가입에 성공하셨습니다.");
					location.href = "/testprj/login"; //로그인 페이지로 이동
				},
				error: function(err) { //회원가입에 실패했을 시
					if (err.status === 409) { //이미 존재하는 아이디, 이메일인 경우
						let errorMessage = JSON.parse(err.responseText).message;
						alert(errorMessage);
					} else { //그 외 오류
						alert("회원가입에 실패하셨습니다.");
					}
					//input 값 초기화
					user_id.value = "";
					user_pw.value = "";
					pw_check.value = "";
					user_mail.value = "";
					user_tell.value = "";
				}
			});
		}
	}
</script>
</head>
<body>
	<jsp:include page="../Main/header.jsp"></jsp:include>
	<div class="userFormContainer">
		<form class="userForm" onsubmit="join(event)">
			<h2>회원가입</h2>
			<div class="inputBox">
				<label for="user_id">닉네임</label>
				<div class="userInput">
					<img src="image/User/user.png" alt="유저 아이콘" />
					<input class="user_id" type="text" name="user_id" placeholder="닉네임 입력하세요." required>
				</div>
			</div>
			<div class="inputBox">
				<label for="user_pw">비밀번호</label>
				<div class="userInput">
					<img src="image/User/padlock.png" alt="자물쇠 아이콘" />
					<input class="user_pw" type="password" name="user_pw" placeholder="비밀번호를 입력하세요." required>
				</div>
			</div>
			<div class="inputBox">
				<label for="pw_check">비밀번호 확인</label>
				<div class="userInput">
					<img src="image/User/padlock.png" alt="자물쇠 아이콘" />
					<input class="pw_check" type="password" name="pw_check" placeholder="비밀번호를 다시 입력하세요." required>
				</div>
			</div>
			<div class="inputBox">
				<label for="user_mail">이메일</label>
				<div class="userInput">
					<img src="image/User/email.png" alt="이메일 아이콘" />
					<input class="user_mail" type="email" name="user_mail" placeholder="이메일을 입력하세요." required>
				</div>
			</div>
			<div class="inputBox">
				<label for="user_tell">전화번호(선택)</label>
				<div class="userInput">
					<img src="image/User/telephone.png" alt="전화기 아이콘" />
					<input class="user_tell" type="tel" name="user_tell" placeholder="전화번호를 입력하세요." >
				</div>
			</div>
			<div class="submitBtn">
				<button type="submit">회원가입</button>
			</div>
			<hr>
			<div class="link">
				<a href="/testprj/login">로그인하기</a>
			</div>
		</form>
	</div>
	<jsp:include page="../Main/footer.jsp"></jsp:include>
</body>
</html>