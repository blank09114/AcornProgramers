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

@WebServlet("/kakaoLogin")
public class KakaoLoginServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accessToken = req.getParameter("access_token");
        
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            // 카카오 API로 사용자 정보 요청
        	// 구글과 거의 비슷하지만 Url주소와 같은 엔드포인트가 다름
            URL url = new URL("https://kapi.kakao.com/v2/user/me");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            
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
                
                // 카카오 ID를 사용하여 사용자 식별
                long kakaoId = userInfo.getLong("id");
                String nickname = "";
                
                // 닉네임 정보가 있으면 가져오기 (선택적)
                // 카카오에서 권한 요청할때 선택적으로 설정을 해놨기 때문에 사용자가 줄수도 있고 안줄수도 있음
                // 그래서 사용자의 선택에 따라 null이 돌아올수도 있어서 null 체크용 코드
                if (userInfo.has("properties") && !userInfo.isNull("properties")) {
                    JSONObject properties = userInfo.getJSONObject("properties");
                    if (properties.has("nickname") && !properties.isNull("nickname")) {
                        nickname = properties.getString("nickname");
                    }
                }
                
                // 사용자 정보 처리
                KakaoDAO kakaoDAO = new KakaoDAO();
                User user = kakaoDAO.findOrCreateKakaoUser(kakaoId, nickname);
                
                // 세션에 사용자 정보 저장
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