package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KakaoDAO {
    
	//전체적으로 구글이랑 거의 유사함
    private LoginDAO loginDAO = new LoginDAO(); 
    
    public User findOrCreateKakaoUser(long kakaoId, String nickname) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        User user = null;
        
        try {
            con = loginDAO.dbCon();
            
            // kakao_ 붙인 ID로 기존 사용자 확인
            String kakaoUserId = "kakao_" + kakaoId;
            String checkSql = "SELECT * FROM user_tbl WHERE user_id = ?";
            pst = con.prepareStatement(checkSql);
            pst.setString(1, kakaoUserId);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                // 기존 사용자 정보 반환 - user_score로 수정
                user = new User(
                    rs.getString("user_id"),
                    rs.getString("user_pw"),
                    rs.getString("user_mail"),
                    rs.getString("user_tell"),
                    rs.getInt("user_score"), // Changed from user_grade to user_score
                    rs.getInt("user_point")
                );
                
                System.out.println("기존 카카오 로그인: " + kakaoUserId);
            } else {
                // 새 사용자 등록
                String dummyEmail = kakaoUserId + "@kakao.user";
                String insertSql = "INSERT INTO user_tbl(user_id, user_pw, user_mail, user_tell, user_score, user_point) VALUES (?, ?, ?, ?, ?, ?)";
                
                if (pst != null) pst.close();
                
                pst = con.prepareStatement(insertSql);
                pst.setString(1, kakaoUserId);
                pst.setString(2, "kakao_oauth"); // 카카오 계정용 더미 비밀번호
                pst.setString(3, dummyEmail);    // 더미 이메일
                pst.setString(4, null);          // 전화번호는 null로 설정
                pst.setInt(5, 1);                // score 값 설정
                pst.setInt(6, 10);				 // point 값 설정
                
                pst.executeUpdate();
                
                user = new User(kakaoUserId, "kakao_oauth", dummyEmail, null, 1, 10);
                System.out.println("새로운 카카오 계정 등록: " + kakaoUserId);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("카카오 사용자 처리 중 오류: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return user;
    }
}