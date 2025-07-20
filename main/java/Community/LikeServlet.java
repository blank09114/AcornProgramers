package Community;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/like")
public class LikeServlet extends HttpServlet {
    private CommunityService service = new CommunityService();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 게시글 ID 받아옴
        String postIdParam = req.getParameter("id");
        
        try {
            int postId = Integer.parseInt(postIdParam);
            
            // 좋아요 증가
            boolean success = service.increaseLike(postId);
            
            // 응답 전송
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            
            if (success) {
                out.print("{\"success\": true}");
            } else {
                out.print("{\"success\": false, \"message\": \"좋아요 처리에 실패했습니다.\"}");
            }
            
        } catch (NumberFormatException e) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("{\"success\": false, \"message\": \"잘못된 게시글 ID입니다.\"}");
        }
    }
}