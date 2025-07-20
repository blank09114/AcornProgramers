<%@page import="java.util.List"%>
<%@page import="Community.CommunityDTO"%>
<%@page import="Community.CommunityDAO"%>
<%@page import="User.User"%>
<%@page import="Rank.RankDAO"%>
<%@page import="Rank.Rank"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title> 메인 </title>
    <link rel="stylesheet" href="css/Main/index.css">
    <script src="image/Main/index.js"></script>  
</head>
<body> 
    <div class="container">  
    <!-- header -->
	<jsp:include page="/WEB-INF/views/Main/header.jsp" />
        <!-- 상단 배너 -->
        <div class="topBanner">
            <!-- 슬라이드 1 -->         
            <div class="topBannerSlide topBannerSlide1 now">
                <a href="<%=request.getContextPath()%>/quiz"> 퀴즈 풀기 </a>
            </div>

            <!-- 슬라이드 2 -->
            <div class="topBannerSlide topBannerSlide2">
                <a href="<%=request.getContextPath()%>/rank"> 랭킹 확인하기 </a>
            </div>
            
             <!-- 슬라이드 3 -->
            <div class="topBannerSlide topBannerSlide3">
                <a href="<%=request.getContextPath()%>/community"> 자유 게시판 </a>
            </div>

            <button class="prevBtn" onclick="bottomCtr()"> 《 </button>
            <button class="nextBtn" onclick="topCtr()"> 》 </button>
        </div>
        
        <div class="middleBanner">
        	<div>
        		<button onclick="location.href='/testprj/quiz'"><img src="image/Main/web-programming.png"></button>
        		<span>코딩 퀴즈</span>
        	</div>
        	<div>
        		<button onclick="location.href='/testprj/rank'"><img src="image/Main/crown.png"></button>
        		<span>랭킹</span>
        	</div>
        	<div>
        		<button onclick="location.href='/testprj/community'"><img src="image/Main/speech-bubble.png"></button>
        		<span>커뮤니티</span>
        	</div>
        	<div>
        		<button onclick="location.href='/testprj/mypage'"><img src="image/Main/add-to-cart.png"></button>
        		<span>장바구니</span>
        	</div>
        </div>

        <!-- 하단 배너 -->
        <div class="bottomBanner">
            <!-- 하단 좌측 배너 -->
            <div class="bottomLeftBanner">
                <!-- 랭킹 -->
                <div class="ranking">
                    <h2 class="bannerTitle"> 이달의 챔피언 </h2>
                    <p class="bannerSubtitle">이번 달 순위를 확인해보세요.</p>
                    <div class="boardWrap">
                       <%
						    ArrayList<Rank> list = (ArrayList<Rank>) request.getAttribute("list");
						    int limit = 5;
						    int count = 0;
						%>
                        <table class="rankingBoard">
                            <tr class="tr1">
                                <td> RANK </td>
                                <td> NAME </td>
                                <td> SCORE </td>
                                <td> DATE </td>
                            </tr>
                           <% 
						        for (Rank r : list) {
						            if (count >= limit) break;
						    %>
                            <tr>
                                <td class="rank"><%= r.getRanking() %> <!-- ▲1, ▼1, - 같은 형식으로 변동 사항도 넣으면 좋을 듯 --> </td>
                                <td class="name"><%= r.getUser_id() %></td>
                                <td class="score"><%= Integer.parseInt(r.getUser_score()) * 10 %> <!-- 현재 점수 --> </td>
                                <td class="score"><%= r.getUser_day() %></td>
                            </tr>
							<%
							            count++;
							        }
							%>	
                        </table>
                    </div>
                </div>

                <!-- 커뮤니티 -->
                <div class="community">
                    <!-- 슬라이드 1 -->
				    <div class="communitySlide communitySlide1 now">
				        <div class="slideBtnDiv">
				            <div>
				                <h2 class="bannerTitle">인기 급상승 게시물</h2>
				                <p class="bannerSubtitle">이번 주 인기 게시글을 확인하세요.</p>
				            </div>
				            <div>
				                <button class="prevBtn" onclick="comCtr()">《 </button>
				                <button class="nextBtn" onclick="comCtr()"> 》 </button>
				            </div>
				        </div>
				        <div class="boardWrap">
				            <div class="communityBoard">
				                <% 
				                List<CommunityDTO> topPosts = (List<CommunityDTO>) request.getAttribute("topPosts");
				                if (topPosts != null && !topPosts.isEmpty()) {
				                    int rank = 1;
				                    for (CommunityDTO post : topPosts) {
				                        String finClass = (rank == 5) ? "fin" : "";
				                %>
				                <a href="postDetail?id=<%= post.getPost_id() %>" class="<%= finClass %>">
				                    <p class="postRank"><%= rank %></p>
				                    <p class="postTitle"><%= post.getPost_title() %></p>
				                    <p class="postGood">좋아요 <%= post.getLike_number() %></p>
				                    <p class="postRipple">댓글 <%= post.getComment_count() %></p>
				                </a>
				                <%
				                        rank++;
				                    }
				                } else {
				                    // 데이터가 없을 경우 기본 표시
				                    for (int i = 1; i <= 5; i++) {
				                        String finClass = (i == 5) ? "fin" : "";
				                %>
				                <a href="" class="<%= finClass %>">
				                    <p class="postRank"><%= i %></p>
				                    <p class="postTitle">등록된 게시글이 없습니다</p>
				                    <p class="postGood">좋아요 0</p>
				                    <p class="postRipple">댓글 0</p>
				                </a>
				                <%
				                    }
				                }
				                %>
                            </div>
                        </div>
                    </div>

                    <!-- 슬라이드 2 -->
					<div class="communitySlide communitySlide2">
					    <div class="slideBtnDiv">
					        <div>
					            <h2 class="bannerTitle">실시간 커뮤니티</h2>
					            <p class="bannerSubtitle">지금 올라오는 글을 확인하세요.</p>
					        </div>
					        <div>
					            <button class="prevBtn" onclick="comCtr()">《 </button>
					            <button class="nextBtn" onclick="comCtr()"> 》 </button>
					        </div>
					    </div>
					    <div class="boardWrap">
					        <div class="communityBoard">
					            <% 
					            List<CommunityDTO> recentPosts = (List<CommunityDTO>) request.getAttribute("recentPosts");
					            if (recentPosts != null && !recentPosts.isEmpty()) {
					                int rank = 1;
					                for (CommunityDTO post : recentPosts) {
					                    String finClass = (rank == 5) ? "fin" : "";
					            %>
					            <a href="postDetail?id=<%= post.getPost_id() %>" class="<%= finClass %>">
					                <p class="postRank"><%= rank %></p>
					                <p class="postTitle"><%= post.getPost_title() %></p>
					                <p class="postGood">좋아요 <%= post.getLike_number() %></p>
					                <p class="postRipple">댓글 <%= post.getComment_count() %></p>
					            </a>
					            <%
					                    rank++;
					                }
					            } else {
					                // 데이터가 없을 경우 기본 표시
					                for (int i = 1; i <= 5; i++) {
					                    String finClass = (i == 5) ? "fin" : "";
					            %>
					            <a href="" class="<%= finClass %>">
					                <p class="postRank"><%= i %></p>
					                <p class="postTitle">등록된 게시글이 없습니다</p>
					                <p class="postGood">좋아요 0</p>
					                <p class="postRipple">댓글 0</p>
					            </a>
					            <%
					                }
					            }
					            %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

			<%User user = (User) session.getAttribute("user");%>
			<%Rank rank = (Rank) request.getAttribute("rankInfo"); %>
            <!-- 하단 우측 배너 -->
            <div class="bottomRightBanner">
                <!-- 회원 정보 -->
                <div class="user">
                	<% if (user == null) { %>
                    <!-- 비로그인 사용자에게 보이는 영역 -->
                    <div class="noneLogin state">
                        <p> 로그인하고 다양한 퀴즈를 풀어보세요! </p>
                        <a href="<%=request.getContextPath()%>/login"> 로그인 </a>
                    </div>
					<%} else { %>
                    <!-- 로그인 사용자에게 보이는 영역 -->
                    <div class="login">
                        <p><span><%=user.getUser_id() %></span>님 반갑습니다. </p>
                        <table class="userInfo">
                            <tr class="tr-1">
                                <td> 순위 </td>
                                <td> 점수 </td>
                                <td> 해결한 퀴즈 </td>
                            </tr>
                            <tr class="tr-2">
                                <td><%=rank.getRanking() %>위 </td>
                                <td><%=Integer.parseInt(rank.getUser_score()) * 10 %>점 </td>
                                <td> <%=Integer.parseInt(rank.getUser_score()) - 1 %>개 </td>
                            </tr>
                        </table>
                    </div>
					 <% } %>
                    <!-- 로그인 / 비로그인 상태에 따라 state 클래스를 부여하세요 -->
                </div>

                <!-- 광고 -->
                <div class="adBox">
                    <p class="lol"> <span> 열심히 하세요. 전 여기서 광고나 띄우고 있을게요. </span> </p>
                    <div class="adBoxImage">
                  		<img src="<%=request.getContextPath()%>/image/Main/car.jpg" style="width: 300px; height: auto;" />
                    </div>
                    <button onclick="ad()"> 쪽찌하러가기 </button>
                </div>

            </div>
        </div>
    </div>

    <!-- footer -->
<jsp:include page="/WEB-INF/views/Main/footer.jsp" />
    
</body>
</html>