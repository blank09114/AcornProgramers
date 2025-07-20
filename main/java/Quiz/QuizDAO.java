package Quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizDAO {
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:testdb";
	String user="scott";
	String password="tiger";
	
	
//	String driver="oracle.jdbc.driver.OracleDriver";
//	String url="jdbc:oracle:thin:@13.125.27.64:1521:xe";
//    String user="system";
//    String password="1234";
	
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
	
	//퀴즈 전체를 가져와 목록 형식으로 보여주기 DAO 코드
	public ArrayList<Quiz> quizList() {
		Connection con = dbCon();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		ArrayList<Quiz> list = new ArrayList<>();
		String sql = "select * from quiz_tbl ORDER BY TO_NUMBER(REPLACE(quiz_id, 'Lv.', ''))"; 
		
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				String quiz_id = rs.getString(1);
				String quiz_title = rs.getString(2);
				String quiz_answer = rs.getString(3);
				int quiz_solved = rs.getInt(4);
				
			
				Quiz quiz = new Quiz(quiz_id, quiz_title, quiz_answer, quiz_solved);
	            list.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//퀴즈 풀이 페이지에서 보여줄 각 퀴즈의 상세 데이터 DAO 코드
	public Quiz selectOne(String quiz_id) {
		Quiz quiz = null;
		
		Connection con = dbCon();
		String sql = "SELECT * FROM QUIZ_TBL WHERE quiz_id=?";
		
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, quiz_id);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				String new_quiz_id = rs.getString(1);
				String quiz_title = rs.getString(2);
				String quiz_answer = rs.getString(3);
				int quiz_solution = rs.getInt(4);
				
				quiz = new Quiz(new_quiz_id, quiz_title, quiz_answer, quiz_solution);
			}
			
			rs.close();
			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return quiz;
	}
	
	//퀴즈를 맞힌 후 해당 유저의 점수(score)를 올리는 DAO 코드
	public void updateUserScore(String user_mail) {
		Connection con = dbCon();
		String sql = "UPDATE user_tbl SET user_score = user_score + 1, user_point = user_point + 10 WHERE user_mail = ?";
		
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, user_mail);
			pst.executeUpdate();
			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//퀴즈를 맞힌 후 해당 퀴즈를 맞힌 유저의 인원 수를 올리는 DAO 코드
	public void updateQuizSolved(String quiz_id) {
		Connection con = dbCon();
		String sql = "UPDATE quiz_tbl SET quiz_solved = quiz_solved + 1 WHERE quiz_id = ?";
		
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, quiz_id);
			pst.executeUpdate();
			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QuizDAO dao = new QuizDAO();
//		ArrayList<Quiz> list = dao.quizList();
//		for (Quiz quiz : list) {
//	        System.out.println(quiz);
//	    }
		
		Quiz quiz = dao.selectOne("Lv.1");
		System.out.println(quiz);
	}
}
