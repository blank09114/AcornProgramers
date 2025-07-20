package Community;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import User.User;

@WebServlet("/postDelete")
public class PostDeleteServlet extends HttpServlet {
    private CommunityService service = new CommunityService();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 로그인 확인용 세션
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("{\"success\": false, \"message\": \"로그인이 필요합니다.\"}");
            return;
        }
        
        // 게시글 ID 가져옴
        String postIdParam = req.getParameter("id");
        
        try {
            int postId = Integer.parseInt(postIdParam);
            
            // 게시글 삭제
            boolean success = service.deletePost(postId, user.getUser_id());
            
            // 응답 전송
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            
            if (success) {
                out.print("{\"success\": true}");
            } else {
                out.print("{\"success\": false, \"message\": \"게시글 삭제에 실패했습니다.\"}");
            }
            // 오류 처리
        } catch (NumberFormatException e) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("{\"success\": false, \"message\": \"잘못된 게시글 ID입니다.\"}");
        }
    }
}