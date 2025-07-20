<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Community.CommunityDTO" %>
<%@ page import="User.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 수정</title>
    <link rel="stylesheet" href="css/Community/postWrite.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script>
        function updatePost(e) {
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
                postId: <%= ((CommunityDTO)request.getAttribute("post")).getPost_id() %>,
                title: title,
                content: content
            };
            
            $.ajax({
                type: "post",
                url: "postUpdate",
                contentType: "application/json",
                data: JSON.stringify(postData),
                success: function(response) {
                    if (response.success) {
                        alert("게시글이 수정되었습니다.");
                        location.href = "postDetail?id=" + <%= ((CommunityDTO)request.getAttribute("post")).getPost_id() %>;
                    } else {
                        alert(response.message || "게시글 수정에 실패했습니다.");
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
        <h1>게시글 수정</h1>
        
        <% CommunityDTO post = (CommunityDTO) request.getAttribute("post"); %>
        
        <form onsubmit="updatePost(event)">
            <div class="inputBox">
                <label for="title">제목</label>
                <input type="text" id="title" placeholder="제목을 입력하세요" maxlength="100" value="<%= post.getPost_title() %>" required>
            </div>
            
            <div class="inputBox">
                <label for="content">내용</label>
                <textarea id="content" placeholder="내용을 입력하세요" rows="15" required><%= post.getPost_content() %></textarea>
            </div>
            
            <div class="buttonGroup">
                <button type="submit">수정</button>
                <button type="button" onclick="location.href='postDetail?id=<%= post.getPost_id() %>'">취소</button>
            </div>
        </form>
    </div>
    
    <jsp:include page="../Main/footer.jsp"></jsp:include>
</body>
</html>