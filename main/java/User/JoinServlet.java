package User;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/join")
public class JoinServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/views/User/join.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json; charset=UTF-8"); //응답 타입을 JSON으로 설정 + 한글 깨짐 방지를 위해 UTF-8 지정
		PrintWriter out = resp.getWriter(); //응답 출력 객체
		
		//요청 본문(InputStream)을 읽기 위한 BufferedReader 설정
		BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
		StringBuilder sb = new StringBuilder(); //요청 데이터를 담을 StringBuilder
		String line;
		
		//JSON 문자열을 한 줄씩 읽어서 sb에 추가
		while((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		
		JSONObject jsonBody = new JSONObject(sb.toString()); //문자열을 JSON 객체로 파싱

		//JSON에서 각각의 파라미터 추출
		String user_id = jsonBody.getString("user_id");
		String user_pw = jsonBody.getString("user_pw");
		String user_mail = jsonBody.getString("user_mail");
		String user_tell = jsonBody.getString("user_tell");

		User user = new User(user_id, user_pw, user_mail, user_tell, 1, 10); //User 객체 생성 (마지막 1은 기본 등급)
		UserService service = new UserService();

		//아이디 또는 이메일이 중복이면 오류 출력
		if (service.checkDuplicate(user)) {
			resp.setStatus(HttpServletResponse.SC_CONFLICT);
			out.print("{\"message\": \"이미 존재하는 아이디 또는 이메일입니다.\"}");
		} else {
			//아이디 또는 이메일이 중복이 아니면 화원가입 실행
			service.joinUser(user);
			resp.setStatus(HttpServletResponse.SC_OK);
			out.print("{\"message\": \"회원가입 성공\"}");
		}
	}
}
