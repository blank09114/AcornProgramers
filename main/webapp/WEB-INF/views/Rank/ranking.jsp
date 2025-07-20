<%@page import="Rank.PageHandler"%>
<%@page import="Rank.Rank"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>랭킹</title>
<link href="css/Rank/Rank.css" rel="stylesheet">
</head>
<body>
	<%ArrayList<Rank> list = (ArrayList<Rank>) request.getAttribute("list"); %>
	
	<jsp:include page="../Main/header.jsp"></jsp:include>
	
	<div class="rankContainer">
		<div class="rankWrapper">
			<h1>-👑RANKING👑-</h1>
			<div class="rankHeader">
				<span class="ref ref-left">순위</span>
				<span class="ref">아이디</span>
				<span class="ref">점수</span>
				<span class="ref">가입날짜</span>
			</div>
		
			<% for(Rank rank : list) { 
				String displayRank = "";
				switch(rank.getRanking()) {
					case 1: displayRank = "🥇"; break; //1등이면 금메달
					case 2: displayRank = "🥈"; break; //2등이면 금메달
					case 3: displayRank = "🥉"; break; //3등이면 동메달 
					default: displayRank = String.valueOf(rank.getRanking()); break;
				}
			%>
				<div class="rankList">
					<span class="ref ref-left <%= rank.getRanking() == 1 ? "topRank" : "" %>">
						<%= displayRank %>
					</span>
					<span class="ref"><%= rank.getUser_id() %></span>
					<span class="ref"><%= Integer.parseInt(rank.getUser_score()) * 10 %></span>
					<span class="ref"><%= rank.getUser_day() %></span>
				</div>
			<% } %>
			
			<% PageHandler ph = (PageHandler) request.getAttribute("ph");
			   int currentPage = (int) request.getAttribute("currentPage");
			%>
			
			<div class="pagination">
			    <% if (ph.getGrpStartPage() > 1) { %>
			        <a href="rank?page=<%=ph.getGrpStartPage() - 1%>">◀ 이전</a>
			    <% } %>
			
			    <% for (int i = ph.getGrpStartPage(); i <= ph.getGrpEndPage(); i++) { %>
			        <a href="rank?page=<%=i%>" <%= i == currentPage ? "class='active'" : "" %>><%=i%></a>
			    <% } %>
			
			    <% if (ph.getGrpEndPage() < ph.getTotalPage()) { %>
			        <a href="rank?page=<%=ph.getGrpEndPage() + 1%>">다음 ▶</a>
			    <% } %>
			</div>
		</div>
	</div>

	<jsp:include page="../Main/footer.jsp"></jsp:include>
</body>
</html>