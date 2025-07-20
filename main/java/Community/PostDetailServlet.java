package Community;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import User.User;

@WebServlet("/postDetail")
public class PostDetailServlet extends HttpServlet {
    private CommunityService service = new CommunityService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            
            // 댓글 목록 조회
            List<CommentDTO> comments = service.getCommentList(postId);
            
            // 현재 로그인한 사용자 정보
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            
            // 결과를 저장
            req.setAttribute("post", post);
            req.setAttribute("comments", comments);
            req.setAttribute("user", user);
            
            //jsp로 넘김
            req.getRequestDispatcher("WEB-INF/views/Community/postDetail.jsp").forward(req, resp);
            
        } catch (NumberFormatException e) {
        	//오류 처리
            // ID가 숫자가 아니면 목록으로 리다이렉트
            resp.sendRedirect(req.getContextPath() + "/community");
        }
    }
}