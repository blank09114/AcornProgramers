<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Community.CommunityDTO" %>
<%@ page import="Community.CommentDTO" %>
<%@ page import="User.User" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 상세</title>
    <link rel="stylesheet" href="css/Community/postDetail.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script>
        function likePost() {
            $.ajax({
                type: "post",
                url: "like?id=${post.post_id}",
                success: function(response) {
                    if (response.success) {
                        alert("좋아요를 눌렀습니다!");
                        location.reload();
                    } else {
                        alert(response.message || "좋아요 처리에 실패했습니다.");
                    }
                },
                error: function(err) {
                    alert("서버 오류가 발생했습니다.");
                    console.log(err);
                }
            });
        }
        
        function deletePost() {
            if (confirm("정말 삭제하시겠습니까?")) {
                $.ajax({
                    type: "post",
                    url: "postDelete?id=${post.post_id}",
                    success: function(response) {
                        if (response.success) {
                            alert("게시글이 삭제되었습니다.");
                            location.href = "community";
                        } else {
                            alert(response.message || "게시글 삭제에 실패했습니다.");
                        }
                    },
                    error: function(err) {
                        alert("서버 오류가 발생했습니다.");
                        console.log(err);
                    }
                });
            }
        }
        
        function submitComment() {
            let content = document.getElementById("commentContent").value.trim();
            
            if (content === "") {
                alert("댓글 내용을 입력해주세요.");
                return;
            }
            
            let commentData = {
                postId: ${post.post_id},
                content: content
            };
            
            $.ajax({
                type: "post",
                url: "comment",
                contentType: "application/json",
                data: JSON.stringify(commentData),
                success: function(response) {
                    if (response.success) {
                        alert("댓글이 등록되었습니다.");
                        location.reload();
                    } else {
                        alert(response.message || "댓글 등록에 실패했습니다.");
                    }
                },
                error: function(err) {
                    alert("서버 오류가 발생했습니다.");
                    console.log(err);
                }
            });
        }
        
        function deleteComment(commentId) {
            if (confirm("댓글을 삭제하시겠습니까?")) {
                $.ajax({
                    type: "delete",
                    url: "comment?id=" + commentId,
                    success: function(response) {
                        if (response.success) {
                            alert("댓글이 삭제되었습니다.");
                            location.reload();
                        } else {
                            alert(response.message || "댓글 삭제에 실패했습니다.");
                        }
                    },
                    error: function(err) {
                        alert("서버 오류가 발생했습니다.");
                        console.log(err);
                    }
                });
            }
        }
    </script>
</head>
<body>
    <jsp:include page="../Main/header.jsp"></jsp:include>
    
    <div class="container">
        <%
        CommunityDTO post = (CommunityDTO) request.getAttribute("post");
        List<CommentDTO> comments = (List<CommentDTO>) request.getAttribute("comments");
        User user = (User) session.getAttribute("user");
        %>
        
        <div class="postHeader">
            <h1><%= post.getPost_title() %></h1>
            <div class="postInfo">
                <span>작성자: <%= post.getUser_id() %></span>
                <span>조회수: <%= post.getViews_number() %></span>
                <span>좋아요: <%= post.getLike_number() %></span>
            </div>
        </div>
        
        <div class="postContent">
            <%= post.getPost_content().replaceAll("\n", "<br>") %>
        </div>
        
        <div class="postActions">
            <button onclick="likePost()">👍 좋아요</button>
            
            <% if (user != null && user.getUser_id().equals(post.getUser_id())) { %>
            <button onclick="location.href='postUpdate?id=<%= post.getPost_id() %>'">✏️ 수정</button>
            <button onclick="deletePost()">🗑️ 삭제</button>
            <% } %>
            
            <button onclick="location.href='community'">목록으로</button>
        </div>
        
        <div class="commentSection">
            <h3>댓글 (<%= comments.size() %>)</h3>
            
            <% if (user != null) { %>
            <div class="commentForm">
                <textarea id="commentContent" placeholder="댓글을 입력하세요" rows="3"></textarea>
                <button onclick="submitComment()">등록</button>
            </div>
            <% } else { %>
            <div class="commentForm">
                <p>댓글을 작성하려면 <a href="login">로그인</a>이 필요합니다.</p>
            </div>
            <% } %>
            
            <div class="commentList">
                <% if (comments != null && !comments.isEmpty()) { %>
                    <% for (CommentDTO comment : comments) { %>
                    <div class="comment">
                        <div class="commentHeader">
                            <span class="commentAuthor"><%= comment.getUser_id() %></span>
                            <% if (user != null && user.getUser_id().equals(comment.getUser_id())) { %>
                            <button class="deleteBtn" onclick="deleteComment(<%= comment.getComment_id() %>)">삭제</button>
                            <% } %>
                        </div>
                        <div class="commentBody">
                            <%= comment.getContent().replaceAll("\n", "<br>") %>
                        </div>
                    </div>
                    <% } %>
                <% } else { %>
                <p class="noComments">등록된 댓글이 없습니다.</p>
                <% } %>
            </div>
        </div>
    </div>
    
    <jsp:include page="../Main/footer.jsp"></jsp:include>
</body>
</html>