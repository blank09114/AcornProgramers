package User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

@WebServlet("/googleLogin")
public class GoogleLoginServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idToken = req.getParameter("id_token");
        
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
        	// 자바에서 제공하는 HttpURLConnection을 사용함
        	// 구글 서버에 토큰 검증 요청 보내는 파트
        	// 구글 토큰 검증 api 주소에 받아온 id_token을 붙여서 요청
            URL url = new URL("https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken);
            // HttpURLConnection을 사용하여 GET 요청을 보냄
            // HttpURLConnection은 자바에서 제공하는 HTTP 통신을 위한 클래스임
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            // 응답 결과를 나타낼 변수
            // 응답코드가 200이면 성공, 그 외에는 실패 3x) 404 not found 같은거 나오면 실패
            // 저 코드 값들은 웹 규격이라고 하네요
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder responseBody = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    responseBody.append(line);
                }
                br.close();
                
                JSONObject userInfo = new JSONObject(responseBody.toString());
                

                String email = userInfo.getString("email");
                String name = userInfo.optString("name", "");
                

                GoogleDAO googleDAO = new GoogleDAO();
                User user = googleDAO.findOrCreateGoogleUser(email, name);
                

                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                

                out.print("{\"success\": true}");
            } else {
                out.print("{\"success\": false, \"message\": \"Invalid token\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }
}