<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href="css/Main/footer.css">
</head>
<body>
	<footer class="site-footer">
  <div class="footer-container">
    <div class="footer-left">
      <h3>Acorn Project</h3>
      <p>지금 바로 도전하고, 랭킹을 확인해보세요!</p>
    </div>
    
    <div class="site-footer">
	  <p>© 2025 Acorn Academy All rights reserved.</p>
	  <div class="footer-line"></div>
	  <p class="team-member">김민환 | 김유민 | 박예린 | 이정호 | 정연수</p>
	</div>
    
    <div class="footer-right">
      <ul>
        <li><a href="<%=request.getContextPath()%>/quiz">퀴즈 풀기</a></li>
        <li><a href="<%=request.getContextPath()%>/rank">랭킹 보기</a></li>
        <li><a onclick="alert('그냥 써')" href="#">이용약관</a></li>
        <li><a href="<%=request.getContextPath()%>/index">홈으로</a></li>
      </ul>
    </div>
  </div>
</footer>
</body>
</html>