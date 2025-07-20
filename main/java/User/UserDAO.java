package User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
	
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:testdb";
	String user="scott";
	String password="tiger";
	
//	    String driver="oracle.jdbc.driver.OracleDriver";
//	    String url="jdbc:oracle:thin:@13.125.27.64:1521:xe";
//	    String user="system";
//	    String password="1234";
	
	Connection dbCon() {
		Connection con = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	//화원가입
	public void join(User user) {
		Connection con = dbCon();
		
		//데이터베이스에 유저 값 삽입
		String sql = "insert into user_tbl(user_id, user_pw, user_mail, user_tell) values (?,?,?,?)";
		
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, user.getUser_id()); //유저 아이디
			pst.setString(2, user.getUser_pw()); //유저 비밀번호
			pst.setString(3, user.getUser_mail()); //유저 메일
			pst.setString(4, user.getUser_tell()); //유저 전화번호
			
			pst.executeUpdate();
			
			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//아이디, 이메일 중복 확인
	public boolean isUserExist(String user_id, String user_mail) {
	    Connection con = dbCon();
	    
	    //user_id 또는 user_mail이 일치하는 사용자가 있는지 검색
	    String sql = "SELECT COUNT(*) FROM user_tbl WHERE user_id = ? OR user_mail = ?";
	    
	    try {
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setString(1, user_id);
	        pst.setString(2, user_mail);
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            int count = rs.getInt(1); //중복된 레코드가 있으면 count 값을 1로 지정
	            return count > 0; // count가 0보다 크면 1(true) 반환
	        }
	        
	        rs.close();
	        pst.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return false;
	}

	//유저 정보 가져오기
	public User userInfo(String user_mail) {
	    Connection con = dbCon();
	    
	    //로그인한 유저의 정보를 데이터베이스에서 검색
	    String sql = "SELECT * FROM user_tbl WHERE user_mail = ?";
	    User user = null;

	    try {
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setString(1, user_mail);
	        ResultSet rs = pst.executeQuery();

	        if (rs.next()) {
	            String user_id = rs.getString("user_id");
	            String user_pw = rs.getString("user_pw");
	            String user_tell = rs.getString("user_tell");
	            int user_score = rs.getInt("user_score");
	            int user_point = rs.getInt("user_point");

	            //검색한 유저 정보를 User 클래스에 저장
	            user = new User(user_id, user_pw, user_mail, user_tell, user_score, user_point);
	        }

	        rs.close();
	        pst.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return user;
	}
	
	/*
	// 회원탈퇴
	public boolean deleteUser(String user_mail) {
	    Connection con = dbCon();
	    String sql = "DELETE FROM user_tbl WHERE user_mail = ?";
	    boolean result = false;
	    
	    try {
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setString(1, user_mail);
	        
	        int rowsAffected = pst.executeUpdate();
	        if (rowsAffected > 0) {
	            result = true;
	        }
	        
	        pst.close();
	        con.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return result;
	}
	*/
	
	// 회원탈퇴
	public boolean deleteUser(String user_mail) {
	    Connection con = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    boolean result = false;
	    
	    try {
	        con = dbCon();
	        con.setAutoCommit(false); // 트랜잭션 시작
	        
	        // 1. user_mail로 user_id 조회
	        String getUserIdSql = "SELECT user_id FROM user_tbl WHERE user_mail = ?";
	        pst = con.prepareStatement(getUserIdSql);
	        pst.setString(1, user_mail);
	        rs = pst.executeQuery();
	        
	        String userId = null;
	        if (rs.next()) {
	            userId = rs.getString("user_id");
	        } else {
	            // 사용자가 존재하지 않음
	            return false;
	        }
	        
	        rs.close();
	        pst.close();
	        
	        // 2. 사용자가 작성한 댓글 삭제
	        String deleteCommentsSql = "DELETE FROM comment_tbl WHERE user_id = ?";
	        pst = con.prepareStatement(deleteCommentsSql);
	        pst.setString(1, userId);
	        pst.executeUpdate();
	        pst.close();
	        
	        // 3. 사용자가 작성한 게시물 삭제
	        String deletePostsSql = "DELETE FROM post_tbl WHERE user_id = ?";
	        pst = con.prepareStatement(deletePostsSql);
	        pst.setString(1, userId);
	        pst.executeUpdate();
	        pst.close();
	        
	        // 4. 사용자 삭제
	        String deleteUserSql = "DELETE FROM user_tbl WHERE user_id = ?";
	        pst = con.prepareStatement(deleteUserSql);
	        pst.setString(1, userId);
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            result = true;
	            con.commit(); // 모든 작업이 성공적으로 완료되면 커밋
	        } else {
	            con.rollback(); // 사용자 삭제 실패 시 롤백
	        }
	        
	    } catch (SQLException e) {
	        // 오류 발생 시 롤백
	        try {
	            if (con != null) {
	                con.rollback();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        // 자원 해제
	        try {
	            if (rs != null) rs.close();
	            if (pst != null) pst.close();
	            if (con != null) {
	                con.setAutoCommit(true); // 자동 커밋 모드 복원
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return result;
	}
	
}
