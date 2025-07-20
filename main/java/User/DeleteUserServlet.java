package User;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/deleteAccount")
public class DeleteUserServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/User/deleteAccount.jsp").forward(req, resp);  
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
        
        HttpSession session = req.getSession(false);
        User user = null;
        boolean isDeleted = false;
        
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        
        // 로그인된 상태인지 확인
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        // 비밀번호 확인
        String confirmPassword = req.getParameter("confirm_password");
        
        if (confirmPassword != null && confirmPassword.equals(user.getUser_pw())) {
            // 비밀번호 일치, 계정 삭제 진행
            UserService service = new UserService();
            isDeleted = service.deleteUser(user.getUser_mail());
            
            if (isDeleted) {
                // 세션 무효화
                session.invalidate();
            }
        }
        
        // JSON 응답 전송
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        
        if (isDeleted) {
            out.print("{\"success\": true, \"message\": \"계정이 성공적으로 삭제되었습니다.\"}");
        } else {
            out.print("{\"success\": false, \"message\": \"계정 삭제에 실패했습니다. 비밀번호를 확인해주세요.\"}");
        }
        out.flush();
    }
	
}
