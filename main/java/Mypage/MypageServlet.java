package Mypage;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Quiz.Quiz;
import Quiz.QuizService;
import User.User;


@WebServlet("/mypage")
public class MypageServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 퀴즈 리스트 가져오기
				HttpSession session = req.getSession();
				User user = (User) session.getAttribute("user");
				
				//로그인을 했을 시 퀴즈 페이지로 이동
				if(user != null) {
					
					
					req.getRequestDispatcher("WEB-INF/views/Mypage/mypage.jsp").forward(req, resp);
				}
				//로그인을 하지 않았으면 로그인 페이지로 이동
				else {
					resp.sendRedirect("/testprj/login");
				}
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("WEB-INF/views/Mypage/mypage.jsp").forward(req, resp);
	}
}
