package Rank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RankDAO {
//	String driver="oracle.jdbc.driver.OracleDriver";
//	String url="jdbc:oracle:thin:@13.125.27.64:1521:xe";
//    String user="system";
//    String password="1234";
	
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:testdb";
	String user="scott";
	String password="tiger";
	
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
	
	public ArrayList<Rank> getRanking(){
		ArrayList<Rank> list = new ArrayList<>();
		
		Connection con = dbCon();
		String sql = "SELECT RANK() OVER (ORDER BY user_score DESC) AS rank, user_id, user_score, user_day FROM user_tbl ORDER BY rank";
		
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				int ranking = rs.getInt("rank");
				String user_id = rs.getString("user_id");
				String user_score = rs.getString("user_score");
				String user_day = rs.getString("user_day");
				
				Rank rank = new Rank(ranking, user_id, user_score, user_day);
				list.add(rank);
			}
			
			rs.close();
			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Rank getOneRanking(String user_id) {
		Rank rank = null;
		
		Connection con = dbCon();
		String sql = "SELECT * FROM (SELECT RANK() OVER (ORDER BY user_score DESC) AS rank, user_id, user_score, user_day FROM user_tbl) ranked_users WHERE user_id = ?";
		
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, user_id);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				int ranking = rs.getInt(1);
				String new_user_id = rs.getString(2);
				String user_score = rs.getString(3);
				String user_day = rs.getString(4);
				
				rank = new Rank(ranking, new_user_id, user_score, user_day);
			}
			
			rs.close();
			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rank;
	}
	
	//페이지네이션
	public ArrayList<Rank> rankPage(int currentPage){
		ArrayList<Rank> list = new ArrayList<>();
		
		//현재 페이지에 따라 시작 순위와 끝 순위 계산 (페이지당 10명 기준)
		int start = (currentPage - 1) * 10 + 1;
		int end = currentPage * 10;
		
		Connection con = dbCon();
		
		//점수순으로 랭킹을 매기고, 해당 페이지에 해당하는 랭킹 범위의 사용자만 조회
		String sql = "SELECT *FROM (SELECT RANK() OVER (ORDER BY user_score DESC) AS rank, user_id, user_score, user_day FROM user_tbl) ranked_users WHERE rank BETWEEN ? AND ? ORDER BY rank";
		
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, start);
			pst.setInt(2, end);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				int ranking = rs.getInt("rank");
				String user_id = rs.getString("user_id");
				String user_score = rs.getString("user_score");
				String user_day = rs.getString("user_day");
				
				Rank rank = new Rank(ranking, user_id, user_score, user_day);
				list.add(rank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	//페이지네이션 계산에 사용될 전체 데이터 수 가져오기
	public int getTotalRankCount() {
	    int count = 0;
	    Connection con = dbCon();
	    String sql = "SELECT COUNT(*) FROM user_tbl";

	    try {
	        PreparedStatement pst = con.prepareStatement(sql);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return count;
	}
	
	public static void main(String[] args) {
		RankDAO dao = new RankDAO();
//		ArrayList<Rank> list = dao.getRanking();
//		
//		for(Rank r : list) {
//			System.out.println(r);
//		}
		
//		Rank rank = dao.getOneRanking("test5");
//		System.out.println(rank);
		
		ArrayList<Rank> list = dao.rankPage(1);
		
		for(Rank r : list) {
			System.out.println(r);
		}
	}
}