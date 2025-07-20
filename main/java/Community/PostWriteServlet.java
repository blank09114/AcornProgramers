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

@WebServlet("/postWrite")
public class PostWriteServlet extends HttpServlet {
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
        
        // 글쓰기 페이지로 포워딩
        req.getRequestDispatcher("WEB-INF/views/Community/postWrite.jsp").forward(req, resp);
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
        
        String title = jsonBody.getString("title");
        String content = jsonBody.getString("content");
        
        // 게시글 객체 생성
        CommunityDTO post = new CommunityDTO(user.getUser_id(), title, content);
        
        // 게시글 저장
        int postId = service.createPost(post);
        
        // 응답 전송
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        
        if (postId > 0) {
            out.print("{\"success\": true, \"postId\": " + postId + "}");
        } else {
            out.print("{\"success\": false, \"message\": \"게시글 작성에 실패했습니다.\"}");
        }
    }
}