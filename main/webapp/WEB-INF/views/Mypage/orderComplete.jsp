<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="User.User"%>
<%@page import="Mypage.OrderDAO"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>주문 완료</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <link href="css/Mypage/orderComplete.css" rel="stylesheet">
</head>
<body>

<%
User user = (User) session.getAttribute("user");
Integer totalPointsObj = (Integer) session.getAttribute("totalPoints");
List<Map<String, String>> cartList = (List<Map<String, String>>) session.getAttribute("cartList");

int totalPoints = totalPointsObj != null ? totalPointsObj : 0;
int user_point = user.getUser_point();

if (user_point >= totalPoints) {
    // 포인트 차감
    int updatedPoints = user_point - totalPoints;

    user.setUser_point(updatedPoints);

    session.setAttribute("user", user);

    // 장바구니 비우기
    session.removeAttribute("cartList");
    session.removeAttribute("totalPoints");
%>


<div class="complete-container">
  <i class="fas fa-check-circle"></i>
  <h1>주문이 완료되었습니다!</h1>
  <p>소중한 주문 감사합니다 😊<br>빠른 시일 내에 배송해드릴게요.</p>
  <a href="/testprj/mypage" class="btn-home">마이페이지로 돌아가기</a>
</div>

<%
} else {
%>
<script>
    alert("포인트가 부족하여 주문을 처리할 수 없습니다.");
    history.back();
</script>
<%
}
%>

</body>
</html>
