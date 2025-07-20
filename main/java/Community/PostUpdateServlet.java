package Community;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import User.User;

@WebServlet("/postUpdate")
public class PostUpdateServlet extends HttpServlet {
    private CommunityService service = new CommunityService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 로그인 확인
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        // 게시글 ID 가져옴
        String postIdParam = req.getParameter("id");
        
        if (postIdParam == null || postIdParam.trim().isEmpty()) {
            // ID가 없으면 목록으로 리다이렉트
            resp.sendRedirect(req.getContextPath() + "/community");
            return;
        }
        
        try {
            int postId = Integer.parseInt(postIdParam);
            
            // 게시글 조회
            CommunityDTO post = service.getPost(postId);
            
            if (post == null) {
                // 게시글이 없으면 목록으로 리다이렉트
                resp.sendRedirect(req.getContextPath() + "/community");
                return;
            }
            
            // 작성자 확인
            if (!post.getUser_id().equals(user.getUser_id())) {
                // 작성자가 아니면 상세 페이지로 리다이렉트
                resp.sendRedirect(req.getContextPath() + "/postDetail?id=" + postId);
                return;
            }
            
            // 결과를 저장
            req.setAttribute("post", post);
            
            // JSP로 전달
            req.getRequestDispatcher("WEB-INF/views/Community/postUpdate.jsp").forward(req, resp);
            
        } catch (NumberFormatException e) {
            // ID가 숫자가 아니면 목록으로 리다이렉트
            resp.sendRedirect(req.getContextPath() + "/community");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 로그인 확인
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("{\"success\": false, \"message\": \"로그인이 필요합니다.\"}");
            return;
        }
        
        // JSON 데이터 파싱
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        
        JSONObject jsonBody = new JSONObject(sb.toString());
        
        int postId = jsonBody.getInt("postId");
        String title = jsonBody.getString("title");
        String content = jsonBody.getString("content");
        
        // 게시글 객체 생성
        CommunityDTO post = new CommunityDTO();
        post.setPost_id(postId);
        post.setUser_id(user.getUser_id());
        post.setPost_title(title);
        post.setPost_content(content);
        
        // 게시글 수정
        boolean success = service.updatePost(post);
        
        // 응답 전송
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        
        if (success) {
            out.print("{\"success\": true}");
        } else {
            out.print("{\"success\": false, \"message\": \"게시글 수정에 실패했습니다.\"}");
        }
    }
}