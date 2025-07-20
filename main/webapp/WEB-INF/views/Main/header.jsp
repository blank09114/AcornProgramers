<%@page import="User.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="css/Main/header.css" rel="stylesheet">
</head>
<body>
	<div class="headerCotainer">
		<div class="navHeader">
    <a href="<%=request.getContextPath()%>/index">소개</a>
    <a href="<%=request.getContextPath()%>/quiz">문제</a>
    <a href="<%=request.getContextPath()%>/rank">랭킹</a>
    <a href="<%=request.getContextPath()%>/community">커뮤니티</a>
</div>
<%
User user = (User) session.getAttribute("user");
%>
		<div class="userHeader">	
			<% if (user == null) { %>
        <button onclick="location.href='/testprj/login'">로그인</button>
		<button onclick="location.href='/testprj/join'">회원가입</button>
	    <% } else { %>
	    	<span><%=user.getUser_id() %>님 환영합니다.</span>
	        <button onclick="location.href='/testprj/mypage'">마이페이지</button> |
	        <button onclick="location.href='/testprj/logout'">로그아웃</button>

	    <% } %>
    
		</div>
	</div>
</body>
</html>