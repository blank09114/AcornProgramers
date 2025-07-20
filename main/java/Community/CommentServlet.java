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

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    private CommunityService service = new CommunityService();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 로그인 유무 체크 세션 활용
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("{\"success\": false, \"message\": \"로그인이 필요합니다.\"}");
            return;
        }
        
        // Json 데이터 파싱하는 파트
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        
        JSONObject jsonBody = new JSONObject(sb.toString());
        // JSON 데이터에서 필요한 정보 추출
        int postId = jsonBody.getInt("postId");
        String content = jsonBody.getString("content");
        
        // 댓글 객체 생성
        CommentDTO comment = new CommentDTO(postId, user.getUser_id(), content);
        
        // 댓글 저장
        boolean success = service.addComment(comment);
        
        // 응답 전송
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        
        if (success) {
            out.print("{\"success\": true}");
        } else {
            out.print("{\"success\": false, \"message\": \"댓글 작성에 실패했습니다.\"}");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 로그인 유무 체크
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("{\"success\": false, \"message\": \"로그인이 필요합니다.\"}");
            return;
        }
        
        // 댓글 ID 파라미터 추출
        String commentIdParam = req.getParameter("id");
        
        try {
            int commentId = Integer.parseInt(commentIdParam);
            
            // 댓글 삭제
            boolean success = service.deleteComment(commentId, user.getUser_id());
            
            // 응답 전송
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            
            if (success) {
                out.print("{\"success\": true}");
            } else {
                out.print("{\"success\": false, \"message\": \"댓글 삭제에 실패했습니다.\"}");
            }
            
        } catch (NumberFormatException e) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("{\"success\": false, \"message\": \"잘못된 댓글 ID입니다.\"}");
        }
    }
}