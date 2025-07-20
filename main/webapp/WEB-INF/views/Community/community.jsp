<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="Community.CommunityDTO" %>
<%@ page import="User.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>커뮤니티</title>
    <link rel="stylesheet" href="css/Community/community.css">
    <script>
        // 검색어가 입력됐는지 확인
        function search() {
            let searchContent = document.getElementById("searchContent").value.trim();
            let searchType = document.querySelector("select").value;

            if (searchContent === "") {
                alert("검색어를 입력해주세요.");
                return;
            }
            
            // 검색 요청
            location.href = "community?searchType=" + searchType + "&keyword=" + encodeURIComponent(searchContent);
        }

        // 섹션 높이 최솟값 설정
        window.addEventListener('load', () => {
            const header = document.querySelector('.headerCotainer');
            const footer = document.querySelector('.site-footer');
            const section = document.querySelector('section');

            const headerHeight = header ? header.offsetHeight : 0;
            const footerHeight = footer ? footer.offsetHeight : 0;

            section.style.minHeight = `calc(100vh - ${headerHeight + footerHeight}px)`;
        });
    </script>
</head>
<body>
    <jsp:include page="../Main/header.jsp"></jsp:include>
    
    <section>
        <div class="container">
            <!-- 글쓰기 메뉴 -->
            <div class="menu">
                <h1>커뮤니티</h1>
                <%
                User user = (User) session.getAttribute("user");
                if (user != null) {
                %>
                <a href="postWrite">글쓰기</a>
                <%
                } else {
                %>
                <a href="login" onclick="alert('로그인이 필요합니다.'); return false;">글쓰기</a>
                <%
                }
                %>
            </div>
    
            <!-- 게시글 목록 -->
            <div class="postListWrap">
                <table>
                    <tr class="tr1">
                        <td class="td1">제목</td>
                        <td class="td2">작성자</td>
                        <td class="td3">좋아요</td>
                        <td class="td3">댓글</td>
                        <td class="td3">작성 시간</td>
                    </tr>
                    
                    <%
                    Map<String, Object> result = (Map<String, Object>) request.getAttribute("result");
                    List<CommunityDTO> postList = (List<CommunityDTO>) result.get("postList");
                    int currentPage = (int) result.get("currentPage");
                    int totalPages = (int) result.get("totalPages");
                    
                    if (postList != null && !postList.isEmpty()) {
                        for (CommunityDTO post : postList) {
                    %>
                    <tr>
                        <td class="td4"><a href="postDetail?id=<%= post.getPost_id() %>"><%= post.getPost_title() %></a></td>
                        <td><%= post.getUser_id() %></td>
                        <td><%= post.getLike_number() %></td>
                        <td><%= post.getComment_count() %></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="5" style="text-align: center;">게시글이 없습니다.</td>
                    </tr>
                    <%
                    }
                    %>
                </table>
            </div>
    
            <!-- 페이지 이동 -->
            <div class="pagesWrap">
                <%
                if (currentPage > 1) {
                %>
                <a href="community?page=<%= currentPage - 1 %><%= result.get("keyword") != null ? "&searchType=" + result.get("searchType") + "&keyword=" + result.get("keyword") : "" %>"> < </a>
                <%
                }
                
                for (int i = 1; i <= totalPages; i++) {
                    if (i == currentPage) {
                %>
                <a href="community?page=<%= i %><%= result.get("keyword") != null ? "&searchType=" + result.get("searchType") + "&keyword=" + result.get("keyword") : "" %>" style="font-weight: bold;"><%= i %></a>
                <%
                    } else {
                %>
                <a href="community?page=<%= i %><%= result.get("keyword") != null ? "&searchType=" + result.get("searchType") + "&keyword=" + result.get("keyword") : "" %>"><%= i %></a>
                <%
                    }
                }
                
                if (currentPage < totalPages) {
                %>
                <a href="community?page=<%= currentPage + 1 %><%= result.get("keyword") != null ? "&searchType=" + result.get("searchType") + "&keyword=" + result.get("keyword") : "" %>"> > </a>
                <%
                }
                %>
            </div>
    
            <!-- 검색 바 -->
            <div class="searchBarWrap">
                <!-- 검색 옵션 -->
                <select>
                    <option value="title_content">제목+내용</option>
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                    <option value="user">작성자</option>
                </select>
    
                <!-- 검색 바 -->
                <input type="text" id="searchContent" value="<%= result.get("keyword") != null ? result.get("keyword") : "" %>">
    
                <button onclick="search()">검색</button>
            </div>
        </div>
    </section>
    
    <jsp:include page="../Main/footer.jsp"></jsp:include>
</body>
</html>