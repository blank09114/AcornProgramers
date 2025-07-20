<%@page import="User.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Main/index.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/User/userDelete.css">
    
</head>
<body>
<!-- header -->
    <jsp:include page="/WEB-INF/views/Main/header.jsp" />
    
    <div class="delete-container">
        <h2 class="delete-title">회원 탈퇴</h2>
        
        <% User user = (User) session.getAttribute("user");
           if (user == null) { %>
            <p>로그인이 필요합니다. <a href="<%=request.getContextPath()%>/login">로그인 페이지로 이동</a></p>
        <% } else { %>
        
        <p>회원 탈퇴를 진행하면 모든 데이터가 삭제되며, 복구할 수 없습니다.</p>
        <p>탈퇴를 원하시면 비밀번호를 입력하고 '회원 탈퇴' 버튼을 클릭해주세요.</p>
        
        <form id="deleteForm" class="delete-form" method="post">
            <div class="form-group">
                <label for="confirm_password">비밀번호 확인</label>
                <input type="password" id="confirm_password" name="confirm_password" required>
            </div>
            
            <div class="btn-container">
                <button type="button" class="btn btn-cancel" onclick="location.href='<%=request.getContextPath()%>/index'">취소</button>
                <button type="submit" class="btn btn-danger">회원 탈퇴</button>
            </div>
        </form>
        
        <div id="errorMessage" class="message error-message"></div>
        <div id="successMessage" class="message success-message"></div>
        
        <% } %>
    </div>
    
    <!-- footer -->
    <jsp:include page="/WEB-INF/views/Main/footer.jsp" />
    
    <script>
        document.getElementById('deleteForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            var confirmPassword = document.getElementById('confirm_password').value;
            
            if (!confirmPassword) {
                showError('비밀번호를 입력해주세요.');
                return;
            }
            
            // AJAX로 회원 탈퇴 요청
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '<%=request.getContextPath()%>/deleteAccount', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        try {
                            var response = JSON.parse(xhr.responseText);
                            if (response.success) {
                                showSuccess(response.message);
                                // 3초 후 로그인 페이지로 리디렉션
                                setTimeout(function() {
                                    window.location.href = '<%=request.getContextPath()%>/login';
                                }, 3000);
                            } else {
                                showError(response.message);
                            }
                        } catch (e) {
                            showError('서버 응답을 처리하는 중 오류가 발생했습니다.');
                        }
                    } else {
                        showError('서버와 통신 중 오류가 발생했습니다.');
                    }
                }
            };
            xhr.send('confirm_password=' + encodeURIComponent(confirmPassword));
        });
        
        function showError(message) {
            var errorDiv = document.getElementById('errorMessage');
            errorDiv.textContent = message;
            errorDiv.style.display = 'block';
            document.getElementById('successMessage').style.display = 'none';
        }
        
        function showSuccess(message) {
            var successDiv = document.getElementById('successMessage');
            successDiv.textContent = message;
            successDiv.style.display = 'block';
            document.getElementById('errorMessage').style.display = 'none';
        }
    </script>
</body>
</html>