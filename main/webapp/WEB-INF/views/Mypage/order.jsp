<%@page import="Mypage.OrderDAO"%>
<%@page import="java.util.List"%>
<%@page import="User.User"%>
<%@page import="Mypage.OrderDAO"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문 내역</title>
    <link href="css/Mypage/order.css" rel="stylesheet">
</head>
<body>

<%
User user = (User) session.getAttribute("user");

if (user == null) {
%>
    <p>!!로그인 후 이용해주세요!!</p>
    <button onclick="location.href='/testprj/login'">로그인 페이지로 이동</button>
<%
} else {
    // 유저 아이디로 주문내역 조회
    List<String> orderList = OrderDAO.getOrderHistoryByUserId(user.getUser_id());
%>

    <h1><%= user.getUser_id() %>님의 주문 내역</h1>

    <div>
        <a href="/testprj/index">메인페이지</a> |
        <a href="/testprj/mypage">마이페이지</a> |
        <a href="/testprj/cart">장바구니</a>
    </div>

    <hr>

    <% if (orderList == null || orderList.isEmpty()) { %>
        <p>주문 내역이 없습니다.</p>
    <% } else { %>
        <ul>
        <% for (String order : orderList) { %>
            <li><%= order %></li>
        <% } %>
        </ul>
    <% } %>

<%
}
%>

</body>
</html>
