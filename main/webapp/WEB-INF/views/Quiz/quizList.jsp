<%@page import="User.User"%>
<%@page import="Quiz.Quiz"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>퀴즈목록</title>
<link href="css/Quiz/quizList.css" rel="stylesheet" />
</head>
<body>
	<%ArrayList<Quiz> list = (ArrayList<Quiz>) request.getAttribute("list"); %>
	<%User user = (User) session.getAttribute("user");%>
	
	<jsp:include page="../Main/header.jsp"></jsp:include>
	
	<div class="quizContainer">
		<div class="quizList">
		<h3>-<%=list.size() %>문제</h3>
		  <div class="quizHeader">
			<img class="quizState" src="" />
	        <span class="quizLevel header">단계</span>
	        <span class="quizTitle header">문제</span>
	        <span class="quizSolved header">완료된 사람</span>
		  </div>
		  
		  <%for(Quiz quiz : list){ %>
		  	<div class="quizItem" onclick="location.href='/testprj/quizDetail?quiz_id=<%=quiz.getQuiz_id()%>'">
		        <img class="quizState" src="" />
		        <span class="quizLevel"><%=quiz.getQuiz_id() %></span>
		        <span class="quizTitle"><%=quiz.getQuiz_title() %></span>
		        <span class="quizSolved"><%=quiz.getQuiz_solution() %>명</span>
		      </div>
		      <div class="lock">
		        <img class="quizState" src="image/Quiz/lock.png" />
		      </div>
		  <%} %>
	    </div>
    </div>
    
    <jsp:include page="../Main/footer.jsp"></jsp:include>
    
    <script>
	    let quizItems = document.querySelectorAll(".quizItem"); //10개의 퀴즈 아이템(해금)
	    let locks = document.querySelectorAll(".lock"); //10개의 퀴즈 아이템(잠금)
	
	    //로그인한 유저의 user_grade 값 가져오기
	    let userScore = <%=user.getUser_grade() %>;
	
	    for (let i = 0; i < quizItems.length; i++) {
	        let quizState = quizItems[i].querySelector(".quizState"); 
	
	        //로그인한 유저의 user_grade 값보다 작을 경우
	        if (i < userScore) {
	        	//퀴즈 해금
	            quizItems[i].style.display = "flex";
	            locks[i].style.display = "none";
	
	            //이미 푼 문제는 solved.png 이미지로 표시
	            if (i < userScore - 1) {
	            	quizState.src = "image/Quiz/solved.png"; 
	            } 
	            //이제 풀어야 하는 문제는 solving.png 이미지로 표시
	            else {
	            	quizState.src = "image/Quiz/solving.png";
	            }
	
	        } 
	      //로그인한 유저의 user_grade 값보다 클 경우
	        else {
	        	//퀴즈 잠금
	            quizItems[i].style.display = "none";
	            locks[i].style.display = "flex";
	        }
	    }
    </script>
</body>
</html>