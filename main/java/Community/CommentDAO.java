package Community;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
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
    
    void close(AutoCloseable... a) {
        for(AutoCloseable item: a) {
            try {
                item.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // 댓글 목록 조회하는 파트
    public List<CommentDTO> getCommentList(int postId) {
        List<CommentDTO> commentList = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            con = dbCon();
            
            String sql = "SELECT * FROM comment_tbl WHERE post_id = ? ORDER BY created_at";
            
            pst = con.prepareStatement(sql);
            pst.setInt(1, postId);
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                CommentDTO comment = new CommentDTO();
                comment.setComment_id(rs.getInt("comment_id"));
                comment.setPost_id(rs.getInt("post_id"));
                comment.setUser_id(rs.getString("user_id"));
                comment.setContent(rs.getString("comment_content")); 
                comment.setCreated_at(rs.getTimestamp("created_at"));
                
                commentList.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs, pst, con);
        }
        
        return commentList;
    }
    
    //댓글 추가 파트
    public boolean addComment(CommentDTO comment) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean success = false;
        
        try {
            con = dbCon();
            
            // 시퀀스에서 새 ID 가져오기 NEXTVAL은 시퀸스에서 다음값을 반환해주고 호출때마다 시퀸스값 증가
            String getSeqSql = "SELECT comment_seq.NEXTVAL FROM DUAL";
            pst = con.prepareStatement(getSeqSql);
            rs = pst.executeQuery();
            
            int commentId = -1;
            if (rs.next()) {
                commentId = rs.getInt(1);
            }
            
            rs.close();
            pst.close();
            
            String sql = "INSERT INTO comment_tbl(comment_id, post_id, user_id, comment_content) VALUES (?, ?, ?, ?)";
            
            pst = con.prepareStatement(sql);
            pst.setInt(1, commentId);
            pst.setInt(2, comment.getPost_id());
            pst.setString(3, comment.getUser_id());
            pst.setString(4, comment.getContent()); 
            
            int result = pst.executeUpdate();
            
            if (result > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs, pst, con);
        }
        
        return success;
    }
    

    //댓글 삭제 파트
    public boolean deleteComment(int commentId, String userId) {
        Connection con = null;
        PreparedStatement pst = null;
        boolean success = false;
        
        try {
            con = dbCon();
            
            String sql = "DELETE FROM comment_tbl WHERE comment_id = ? AND user_id = ?";
            
            pst = con.prepareStatement(sql);
            pst.setInt(1, commentId);
            pst.setString(2, userId);
            
            int result = pst.executeUpdate();
            
            if (result > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pst, con);
        }
        
        return success;
    }
}