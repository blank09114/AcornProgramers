package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoogleDAO {
    
    private LoginDAO loginDAO = new LoginDAO(); 
    
    public User findOrCreateGoogleUser(String email, String name) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        User user = null;
        
        try {
            con = loginDAO.dbCon();
            
            // DB에 맞는 이메일 잇으면 기존 사용자로 들어감
            String checkSql = "SELECT * FROM user_tbl WHERE user_mail = ?";
            pst = con.prepareStatement(checkSql);
            pst.setString(1, email);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                user = new User(
                    rs.getString("user_id"),
                    rs.getString("user_pw"),
                    rs.getString("user_mail"),
                    rs.getString("user_tell"),
                    rs.getInt("user_score"), 
                    rs.getInt("user_point")
                );
                
                System.out.println("기존 구글 로그인: " + email);
            } else {
                String userId = "google_" + System.currentTimeMillis();
                String insertSql = "INSERT INTO user_tbl(user_id, user_pw, user_mail, user_tell, user_score, user_point) VALUES (?, ?, ?, ?, ?, ?)";
                
                if (pst != null) pst.close();
                
                pst = con.prepareStatement(insertSql);
                pst.setString(1, email);
                pst.setString(2, "google_oauth"); // 구글계정용으로 더미 값으로 넣어놓음
                pst.setString(3, email);
                pst.setString(4, null); // 전화번호도 구글 용으로 null로 둠
                pst.setInt(5, 1); // score는 0으로 설정
                pst.setInt(6, 10);
                
                pst.executeUpdate();
                
                user = new User(email, "google_oauth", email, null, 1, 10);
                System.out.println("새로은 구글계정 등록: " + email);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("구글 사용자 처리 중 오류: " + e.getMessage());
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