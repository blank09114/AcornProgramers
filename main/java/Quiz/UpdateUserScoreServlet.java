package Quiz;

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
import User.UserService;

//유저 점수 올리기, 퀴즈 맞힌 유저 수 올리기 servlet
@WebServlet("/quiz/solve")
public class UpdateUserScoreServlet extends HttpServlet {
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//현재 로그인된 사용자의 세션 정보 가져오기
		HttpSession session = req.getSession();
	    User user = (User) session.getAttribute("user");

	    if (user == null) { //로그인이 안 되어 있으면 오류 응답
	        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        return;
	    }

	    // Json 데이터 파싱
	    BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }
	    reader.close();

	    // 파싱한 JSON 데이터에서 필요 정보 추출
	    JSONObject json = new JSONObject(sb.toString());
	    String quiz_id = json.getString("quiz_id"); //퀴즈 ID 추출
	    String user_mail = user.getUser_mail(); //현재 로그인한 유저의 이메일 추출

	    // 점수 및 퀴즈 맞힌 인원수 증가
	    QuizService quizService = new QuizService();
	    quizService.updateScore(user_mail); //유저 점수 증가
	    quizService.updateSolved(quiz_id); //퀴즈 맞힌 인원수 증가

	    // 세션 갱신
	    UserService userService = new UserService();
	    
	    User updatedUser = userService.getUserInfo(user_mail); //유저 점수
	    session.setAttribute("user", updatedUser);

	    Quiz updateQuiz = quizService.getOneQuiz(quiz_id); //퀴즈 맞힌 인원수
	    session.setAttribute("quiz", updateQuiz);

	    resp.setContentType("application/json");
	    resp.getWriter().write("{\"success\": true}");
	}
}

