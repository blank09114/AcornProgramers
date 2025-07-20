<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@page import="User.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니</title>
<link href="css/Mypage/cart.css" rel="stylesheet">
</head>
<body>
<h2>장바구니 목록</h2>

<%
User user = (User) session.getAttribute("user");


List<Map<String, String>> cartList = (List<Map<String, String>>) session.getAttribute("cartList");

int totalPoints = 0;

if (cartList != null) {
    for (Map<String, String> product : cartList) {
        try {
            totalPoints += Integer.parseInt(product.get("point"));
        } catch (NumberFormatException e) {
          
        }
    }
}
session.setAttribute("totalPoints", totalPoints);


int userPoints = user.getUser_point();
%>


<div class="MypageHeader">
    <div><a href="/testprj/index">메인페이지로 이동하기</a></div>
    <div><a href="/testprj/mypage">마이페이지로 이동하기</a></div>
    <div><span><%= user.getUser_id() %>님의 장바구니 입니다</span></div>
    <div>현재 회원님의 포인트(P)는 <%= user.getUser_point()%>P입니다</div>
    <div class="button-container">
  <button id="addressBtn" style="background-color: #0056b3; color: white; border: none; padding: 12px 24px; font-size: 16px; font-weight: 600; border-radius: 6px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); width: 200px; text-align: center;">배송지 입력하기</button>
</div>



    <%
    if (cartList == null || cartList.isEmpty()) {
    %>
        <p>장바구니에 담긴 상품이 없습니다.</p>
    <%
    } else {
    %>
        <div class="cart-list">
        <%
            for (int i = 0; i < cartList.size(); i++) {
                Map<String, String> product = cartList.get(i);
                String name = product.get("name");
                String point = product.get("point");
                String imgSrc = product.get("imgSrc");
        %>
            <div class="cart-item">
                <img src="<%= imgSrc %>" alt="<%= name %>">
                <div class="cart-item-info">
                    <h3><%= name %></h3>
                    <p><%= point %>P</p>
                </div>
                <button class="delete-button" data-index="<%= i %>">삭제하기</button>
            </div>
        <%
            }
        %>
        </div>
    <%
    }
    %>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const deleteButtons = document.querySelectorAll('.delete-button');
        deleteButtons.forEach(button => {
            button.addEventListener('click', function() {
                const index = this.getAttribute('data-index');
                if (confirm('이 상품을 장바구니에서 삭제하시겠습니까?')) {
                    fetch('/testprj/cart/delete', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: 'index=' + index
                    })
                    .then(response => {
                        if (response.ok) {
                            window.location.reload();
                        } else {
                            alert('삭제 중 오류가 발생했습니다.');
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('서버와 통신 중 오류가 발생했습니다.');
                    });
                }
            });
        });

     // 배송지 입력 버튼 처리
        const addressBtn = document.getElementById('addressBtn');
        addressBtn.addEventListener('click', function() {
            const isCartEmpty = <%= (cartList == null || cartList.isEmpty()) ? "true" : "false" %>;
            const totalPoints = <%= totalPoints %>;
            const userPoints = <%= userPoints %>;

            if (isCartEmpty) {
                alert('장바구니에 담긴 상품이 없습니다.');
            } else if (totalPoints > userPoints) {
                alert('포인트가 부족합니다.');
            } else {
                window.location.href = '/testprj/address';
            }
        });

    });
</script>


</body>
</html>