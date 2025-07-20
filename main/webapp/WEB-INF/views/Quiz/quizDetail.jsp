<%@page import="User.User"%>
<%@page import="Quiz.Quiz"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>퀴즈 도전</title>
<link rel="stylesheet" href="css/Quiz/quizDetail.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
<%Quiz quiz = (Quiz) request.getAttribute("quiz"); %>
<%User user = (User) session.getAttribute("user");%>

<script>
	function checkAnswer() {
		let solve = "<%=quiz.getQuiz_answer()%>"; //퀴즈 정답을 저장
		let checkInput = document.querySelector(".checkInput"); //사용자가 입력한 정답
		
		//퀴즈의 난이도 레벨(quiz_id)에서 숫자만 추출
		let level = "<%=quiz.getQuiz_id()%>";
		let quizLevel = parseInt(level.match(/\d+/)[0]);
		
		//사용자 점수(user_grade) 가져옴
		let userScore = "<%=user.getUser_grade()%>"
		
		// 입력값이 비어있는지 확인
		if(checkInput.value === ""){
			alert("정답을 입력해주세요.")
		}else{
			// 정답과 입력값이 다른 경우
			if(checkInput.value != solve){
				alert("오답입니다.");
				location.href='/testprj/quizDetail?quiz_id=<%=quiz.getQuiz_id()%>'; //새로고침
			}else{
				// 이미 푼 문제라면 점수를 올리지 않고 퀴즈 목록 페이지로 이동
				if(userScore > quizLevel){
					alert("정답입니다.");
					location.href='/testprj/quiz';
				}else{
					// 정답 + 점수 갱신 필요 시 서버로 user_mail, quiz_id 전송
					let sendData = {
						user_mail: "<%=user.getUser_mail()%>",
						quiz_id: "<%=quiz.getQuiz_id()%>"
					}
							
					let sendDataStr = JSON.stringify(sendData); // JSON 문자열로 변환
						
					$.ajax({
						type: "put",
						url: "/testprj/quiz/solve",
						contentType: "application/json",
						data: sendDataStr,
						success: function(data) { //정답일 경우 퀴즈 목록 페이지로 이동
							alert("정답입니다.");
							location.href='/testprj/quiz';
						},
						error: function(err) {
							console.log(err)
						}
					})
				}
			}
		}
	}
</script>

	<jsp:include page="../Main/header.jsp"></jsp:include>
	
	<div class="detailContainer">
		<div class="quizContainer">
		    <h2><%= quiz.getQuiz_id() %> - <%= quiz.getQuiz_title() %></h2>
		    
		    <div class="quizImage">
		        <img src="image/Quiz/level/<%= quiz.getQuiz_id() %>.png" alt="퀴즈 이미지">
		    </div>
		    
			 <div class="quizAnswer">
			      <input class="checkInput" type="text" placeholder="다음 코드의 결과값을 입력하세요." />
			      <button onclick="checkAnswer()">확인</button>
			 </div>
		</div>
	</div>
	
	<jsp:include page="../Main/footer.jsp"></jsp:include>

</body>
</html>
