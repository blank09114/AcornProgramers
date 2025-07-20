<%@page import="User.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link href="css/Mypage/mypage.css" rel="stylesheet">
</head>


<body>

<%
User user = (User) session.getAttribute("user");
%>

<div class="MypageHeader">
<% if (user == null) { %>
    !!로그인 후 이용해주세요!!<button onclick="location.href='/testprj/login'">로그인페이지로 이동하기</button>
<% } else { %>
    <div><a href="/testprj/index">메인페이지로 이동하기</a></div>
    <div><span><%= user.getUser_id() %>님 환영합니다!</span></div>
    <div>현재 회원님의 포인트(P)는 <%=user.getUser_point()%>P입니다</div>
    
    <!-- 버튼들을 오른쪽 위에 세로로 정렬하기 위한 컨테이너 -->
    <div class="header-buttons">
        <button onclick="location.href='/testprj/cart'">장바구니로 이동</button>
        <button onclick="location.href='/testprj/order'">주문내역</button>
        <button onclick="location.href='/testprj/Mypagelogout'">로그아웃</button>
        <button onclick="location.href='/testprj/deleteAccount'">회원탈퇴</button>
    </div>
<% } %>
</div>


<% if (user != null) { %>
<div class="product-list">

    <div class="product">
        <img src="/testprj/image/Mypage/prod1.jpg" alt="상품 1">
        <h3>애플펜슬</h3>
        <p>8P</p>
        <button class="buy-button" data-product="애플펜슬">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod2.jpg" alt="상품 2">
        <h3>노트북 스탠드</h3>
        <p>20P</p>
        <button class="buy-button" data-product="노트북 스탠드">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod3.jpg" alt="상품 3">
        <h3>기계식 키보드</h3>
        <p>20P</p>
        <button class="buy-button" data-product="기계식 키보드">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod4.jpg" alt="상품 3">
        <h3>손목받침대</h3>
        <p>20P</p>
        <button class="buy-button" data-product="손목받침대">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod5.jpg" alt="상품 3">
        <h3>인체공학 의자</h3>
        <p>30P</p>
        <button class="buy-button" data-product="인체공학 의자">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod6.jpg" alt="상품 3">
        <h3>맥북에어 m4</h3>
        <p>40P</p>
        <button class="buy-button" data-product="맥북에어 m4">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod7.jpg" alt="상품 3">
        <h3>울트라 와이드 모니터</h3>
        <p>40P</p>
        <button class="buy-button" data-product="울트라 와이드 모니터">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod8.jpg" alt="상품 3">
        <h3>USB 도킹 스테이션</h3>
        <p>20P</p>
        <button class="buy-button" data-product="USB 도킹 스테이션">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod9.jpg" alt="상품 3">
        <h3>에어팟 프로 4세대</h3>
        <p>20P</p>
        <button class="buy-button" data-product="에어팟 프로 4세대">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod10.jpg" alt="상품 3">
        <h3>웹캠</h3>
        <p>10P</p>
        <button class="buy-button" data-product="웹캠">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod11.jpg" alt="상품 3">
        <h3>텀블러</h3>
        <p>20P</p>
        <button class="buy-button" data-product="텀블러">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod12.jpg" alt="상품 3">
        <h3>포스트잇</h3>
        <p>10P</p>
        <button class="buy-button" data-product="포스트잇">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod13.jpg" alt="상품 3">
        <h3>모니터 암</h3>
        <p>40P</p>
        <button class="buy-button" data-product="모니터 암">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod14.jpg" alt="상품 3">
        <h3>멀티 모니터</h3>
        <p>50P</p>
        <button class="buy-button" data-product="멀티 모니터">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod15.jpg" alt="상품 3">
        <h3>와이드 모니터</h3>
        <p>60P</p>
        <button class="buy-button" data-product="와이드 모니터">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod16.jpg" alt="상품 3">
        <h3>다리 마사지기</h3>
        <p>20P</p>
        <button class="buy-button" data-product="다리 마사지기">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod17.jpg" alt="상품 3">
        <h3>목 쿠션</h3>
        <p>10P</p>
        <button class="buy-button" data-product="목 쿠션">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod18.jpg" alt="상품 3">
        <h3>높이 조절 책상</h3>
        <p>20P</p>
        <button class="buy-button" data-product="높이 조절 책상">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod19.jpg" alt="상품 3">
        <h3>USB정리 자석</h3>
        <p>10P</p>
        <button class="buy-button" data-product="USB정리 자석">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod20.jpg" alt="상품 3">
        <h3>무선충전기</h3>
        <p>10P</p>
        <button class="buy-button" data-product="무선충전기">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod21.jpg" alt="상품 3">
        <h3>노트북 파우치</h3>
        <p>10P</p>
        <button class="buy-button" data-product="노트북 파우치">장바구니에 담기</button>
    </div>
    
    <% 
    if (user.getUser_grade() >= 15) {
%>
	<div class="product">
        <img src="/testprj/image/Mypage/prod22.jpg" alt="상품 1">
        <h3>G80침수차</h3>
        <p>150P</p>
        <button class="buy-button" data-product="G80침수차">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod25.jpg" alt="상품 1">
        <h3>에이콘 칩 한판 용</h3>
        <p>10P</p>
        <button class="buy-button" data-product="에이콘 칩 한판 용">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod24.jpg" alt="상품 1">
        <h3>에이콘 칩 두 판 용</h3>
        <p>20P</p>
        <button class="buy-button" data-product="에이콘 칩 두 판 용">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod29.jpg" alt="상품 1">
        <h3>에이콘 칩 집문서 가져와용</h3>
        <p>1000P</p>
        <button class="buy-button" data-product="에이콘 칩 집문서 가져와용">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod26.jpg" alt="상품 1">
        <h3>에이콘 칩 인생 아찔한 맛</h3>
        <p>100P</p>
        <button class="buy-button" data-product="에이콘 칩 인생 아찔한 맛">장바구니에 담기</button>
    </div>
    
    <div class="product">
        <img src="/testprj/image/Mypage/prod31.jpg" alt="상품 1">
        <h3>에이콘 칩 인생나락 맛</h3>
        <p>300P</p>
        <button class="buy-button" data-product="에이콘 칩 인생나락 맛">장바구니에 담기</button>
    </div>
     <div class="product">
        <img src="/testprj/image/Mypage/prod27.jpg" alt="상품 1">
        <h3>에이콘 칩 저세상 맛</h3>
        <p>500P</p>
        <button class="buy-button" data-product="에이콘 칩 저세상 맛">장바구니에 담기</button>
    </div>
    <div class="product">
        <img src="/testprj/image/Mypage/prod28.jpg" alt="상품 1">
        <h3>에이콘 칩 인생은 한방이다</h3>
        <p>1000P</p>
        <button class="buy-button" data-product="에이콘 칩 인생은 한방이다">장바구니에 담기</button>
    </div>
    <% 
    } else {
%>
    <div class="product">
    	<img src="/testprj/image/Mypage/prod32.jpg" alt="상품1">
     <h3>퀴즈만점시 추가로 개방</h3>
    </div>
    <div class="product">
    	<img src="/testprj/image/Mypage/prod32.jpg" alt="상품1">
     <h3>퀴즈만점시 추가로 개방</h3>
    </div>
    <div class="product">
    	<img src="/testprj/image/Mypage/prod32.jpg" alt="상품1">
     <h3>퀴즈만점시 추가로 개방</h3>
    </div>
    <div class="product">
    	<img src="/testprj/image/Mypage/prod32.jpg" alt="상품1">
     <h3>퀴즈만점시 추가로 개방</h3>
    </div>
    <div class="product">
    	<img src="/testprj/image/Mypage/prod32.jpg" alt="상품1">
     <h3>퀴즈만점시 추가로 개방</h3>
    </div>
    <div class="product">
    	<img src="/testprj/image/Mypage/prod32.jpg" alt="상품1">
     <h3>퀴즈만점시 추가로 개방</h3>
    </div>
    <div class="product">
    	<img src="/testprj/image/Mypage/prod32.jpg" alt="상품1">
     <h3>퀴즈만점시 추가로 개방</h3>
    </div>
    <div class="product">
    	<img src="/testprj/image/Mypage/prod32.jpg" alt="상품1">
     <h3>퀴즈만점시 추가로 개방</h3>
    </div>
<%
    }
%>
    
</div>
<% } %>
<script>
window.onload = function () {
    const buttons = document.querySelectorAll(".buy-button");
    buttons.forEach(function (button) {
        button.addEventListener("click", function () {
            const productDiv = button.closest(".product");


            const productName = button.getAttribute("data-product");


            const pointTag = productDiv.querySelector("p");
            const pointText = pointTag ? pointTag.textContent.trim() : "0P";
            const productPoint = pointText.replace(/[^0-9]/g, "");
            

            const imgTag = productDiv.querySelector("img");
            const imgSrc = imgTag ? imgTag.getAttribute("src") : "";

            if (confirm('"' + productName + productPoint + 'P"을(를) 장바구니에 담으시겠습니까?')) {
                fetch("/testprj/cart", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "productName=" + encodeURIComponent(productName) +
                          "&productPoint=" + encodeURIComponent(productPoint) +
                          "&imgSrc=" + encodeURIComponent(imgSrc)
                })
                .then(response => {
                    if (response.ok) {
                        alert("장바구니에 담겼습니다!");
                    } else {
                        alert("오류가 발생했습니다. 다시 시도해주세요.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("서버와 통신 중 오류가 발생했습니다.");
                });
            }
        });
    });
};
</script>


</body>
</html>
