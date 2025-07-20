package Quiz;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import User.User;

//퀴즈 풀이 페이지 servlet
@WebServlet("/quizDetail")
public class QuizDetailServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// id에 맞는 퀴즈 정보 가져오기
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		
		//로그인을 했으면 퀴즈 풀이 페이지로 이동
		if(user != null) {
			String quiz_id = req.getParameter("quiz_id");
			
			QuizService service = new QuizService();
			Quiz quiz = service.getOneQuiz(quiz_id);
			
			req.setAttribute("quiz", quiz);
			
			req.getRequestDispatcher("WEB-INF/views/Quiz/quizDetail.jsp").forward(req, resp);
		}
		//로그인을 하지 않았으면 로그인 페이지로 이동
		else {
			resp.sendRedirect("/testprj/login");
		}
	}
}