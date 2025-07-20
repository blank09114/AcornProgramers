<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="User.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>글쓰기</title>
    <link rel="stylesheet" href="css/Community/postWrite.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script>
        function submitPost(e) {
            e.preventDefault();
            
            let title = document.getElementById("title").value.trim();
            let content = document.getElementById("content").value.trim();
            
            if (title === "") {
                alert("제목을 입력해주세요.");
                return;
            }
            
            if (content === "") {
                alert("내용을 입력해주세요.");
                return;
            }
            
            let postData = {
                title: title,
                content: content
            };
            
            $.ajax({
                type: "post",
                url: "postWrite",
                contentType: "application/json",
                data: JSON.stringify(postData),
                success: function(response) {
                    if (response.success) {
                        alert("게시글이 등록되었습니다.");
                        location.href = "postDetail?id=" + response.postId;
                    } else {
                        alert(response.message || "게시글 등록에 실패했습니다.");
                    }
                },
                error: function(err) {
                    alert("서버 오류가 발생했습니다.");
                    console.log(err);
                }
            });
        }
    </script>
</head>
<body>
    <jsp:include page="../Main/header.jsp"></jsp:include>
    
    <div class="container">
        <h1>글쓰기</h1>
        
        <form onsubmit="submitPost(event)">
            <div class="inputBox">
                <label for="title">제목</label>
                <input type="text" id="title" placeholder="제목을 입력하세요" maxlength="100" required>
            </div>
            
            <div class="inputBox">
                <label for="content">내용</label>
                <textarea id="content" placeholder="내용을 입력하세요" rows="15" required></textarea>
            </div>
            
            <div class="buttonGroup">
                <button type="submit">등록</button>
                <button type="button" onclick="location.href='community'">취소</button>
            </div>
        </form>
    </div>
    
    <jsp:include page="../Main/footer.jsp"></jsp:include>
</body>
</html>