package Community;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommunityDAO {
	
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
	  
	  // 글 목록 조회
	  public List<CommunityDTO> getPostList(int page, int pageSize) {
	        List<CommunityDTO> postList = new ArrayList<>();
	        Connection con = null;
	        PreparedStatement pst = null;
	        ResultSet rs = null;
	        
	        try {
	            con = dbCon();
	            
	            
	            String sql = "SELECT p.*, (SELECT COUNT(*) FROM comment_tbl c WHERE c.post_id = p.post_id) AS comment_count " +
	                         "FROM (SELECT post_id, user_id, title, content, likes, views, created_at, " +
	                         "ROW_NUMBER() OVER (ORDER BY post_id DESC) AS rn " +
	                         "FROM post_tbl) p " +
	                         "WHERE rn BETWEEN ? AND ?";
	            
	            pst = con.prepareStatement(sql);
	            
	            int start = (page - 1) * pageSize + 1;
	            int end = page * pageSize;
	            
	            pst.setInt(1, start);
	            pst.setInt(2, end);
	            
	            rs = pst.executeQuery();
	            
	            while (rs.next()) {
	                CommunityDTO post = new CommunityDTO();
	                post.setPost_id(rs.getInt("post_id"));
	                post.setUser_id(rs.getString("user_id"));
	                post.setPost_title(rs.getString("title")); 
	                post.setPost_content(rs.getString("content")); 
	                post.setLike_number(rs.getInt("likes")); 
	                post.setViews_number(rs.getInt("views")); 
	                post.setCreated_at(rs.getTimestamp("created_at"));
	                post.setComment_count(rs.getInt("comment_count"));
	                
	                postList.add(post);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            close(rs, pst, con);
	        }
	        
	        return postList;
	    }
	  
	  // 글의 갯수 조회
	  public int getPostCount() {
	        Connection con = null;
	        PreparedStatement pst = null;
	        ResultSet rs = null;
	        int count = 0;
	        
	        try {
	            con = dbCon();
	            
	            String sql = "SELECT COUNT(*) FROM post_tbl";
	            
	            pst = con.prepareStatement(sql);
	            rs = pst.executeQuery();
	            
	            if (rs.next()) {
	                count = rs.getInt(1);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            close(rs, pst, con);
	        }
	        
	        return count;
	    }
	  // 글 생성
	  public int createPost(CommunityDTO post) {
	        Connection con = null;
	        PreparedStatement pst = null;
	        ResultSet rs = null;
	        int postId = -1;
	        
	        try {
	            con = dbCon();
	            
	            
	            String getSeqSql = "SELECT post_seq.NEXTVAL FROM DUAL";
	            pst = con.prepareStatement(getSeqSql);
	            rs = pst.executeQuery();
	            
	            if (rs.next()) {
	                postId = rs.getInt(1);
	            }
	            
	            rs.close();
	            pst.close();
	            
	            String sql = "INSERT INTO post_tbl(post_id, user_id, title, content) VALUES (?, ?, ?, ?)";
	            
	            pst = con.prepareStatement(sql);
	            pst.setInt(1, postId);
	            pst.setString(2, post.getUser_id());
	            pst.setString(3, post.getPost_title()); 
	            pst.setString(4, post.getPost_content()); 
	            
	            pst.executeUpdate();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            postId = -1;
	        } finally {
	            close(rs, pst, con);
	        }
	        
	        return postId;
	    }
	  
	  // 글 가져오기
	  public CommunityDTO getPost(int postId) {
	        Connection con = null;
	        PreparedStatement pst = null;
	        ResultSet rs = null;
	        CommunityDTO post = null;
	        
	        try {
	            con = dbCon();
	            
	         
	            String updateViewsSql = "UPDATE post_tbl SET views = views + 1 WHERE post_id = ?";
	            pst = con.prepareStatement(updateViewsSql);
	            pst.setInt(1, postId);
	            pst.executeUpdate();
	            pst.close();
	            
	            
	            String sql = "SELECT p.*, (SELECT COUNT(*) FROM comment_tbl c WHERE c.post_id = p.post_id) AS comment_count " +
	                         "FROM post_tbl p WHERE post_id = ?";
	            
	            pst = con.prepareStatement(sql);
	            pst.setInt(1, postId);
	            
	            rs = pst.executeQuery();
	            
	            if (rs.next()) {
	                post = new CommunityDTO();
	                post.setPost_id(rs.getInt("post_id"));
	                post.setUser_id(rs.getString("user_id"));
	                post.setPost_title(rs.getString("title"));
	                post.setPost_content(rs.getString("content"));
	                post.setLike_number(rs.getInt("likes"));
	                post.setViews_number(rs.getInt("views"));
	                post.setCreated_at(rs.getTimestamp("created_at"));
	                post.setComment_count(rs.getInt("comment_count"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            close(rs, pst, con);
	        }
	        
	        return post;
	    }
	  // 글 수정
	  public boolean updatePost(CommunityDTO post) {
	        Connection con = null;
	        PreparedStatement pst = null;
	        boolean success = false;
	        
	        try {
	            con = dbCon();
	            
	            String sql = "UPDATE post_tbl SET title = ?, content = ? WHERE post_id = ? AND user_id = ?";
	            
	            pst = con.prepareStatement(sql);
	            pst.setString(1, post.getPost_title());
	            pst.setString(2, post.getPost_content());
	            pst.setInt(3, post.getPost_id());
	            pst.setString(4, post.getUser_id());
	            
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
	  // 글 삭제
	  public boolean deletePost(int postId, String userId) {
	        Connection con = null;
	        PreparedStatement pst = null;
	        boolean success = false;
	        
	        try {
	            con = dbCon();
	            
	            
	            String deleteCommentsSql = "DELETE FROM comment_tbl WHERE post_id = ?";
	            pst = con.prepareStatement(deleteCommentsSql);
	            pst.setInt(1, postId);
	            pst.executeUpdate();
	            pst.close();
	            
	            String sql = "DELETE FROM post_tbl WHERE post_id = ? AND user_id = ?";
	            
	            pst = con.prepareStatement(sql);
	            pst.setInt(1, postId);
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
	  
	  //좋아요 수 증가
	  public boolean increaseLike(int postId) {
	        Connection con = null;
	        PreparedStatement pst = null;
	        boolean success = false;
	        
	        try {
	            con = dbCon();
	            
	            String sql = "UPDATE post_tbl SET likes = likes + 1 WHERE post_id = ?";
	            
	            pst = con.prepareStatement(sql);
	            pst.setInt(1, postId);
	            
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
	  // 글 검색
	  public List<CommunityDTO> searchPosts(String searchType, String keyword, int page, int pageSize) {
	        List<CommunityDTO> postList = new ArrayList<>();
	        Connection con = null;
	        PreparedStatement pst = null;
	        ResultSet rs = null;
	        
	        try {
	            con = dbCon();
	            
	            // sqlBuilder 사용하여 쿼리 동적으로 생성
	            StringBuilder sqlBuilder = new StringBuilder();
	            sqlBuilder.append("SELECT p.*, (SELECT COUNT(*) FROM comment_tbl c WHERE c.post_id = p.post_id) AS comment_count ");
	            sqlBuilder.append("FROM (SELECT post_id, user_id, title, content, likes, views, created_at, ");
	            sqlBuilder.append("ROW_NUMBER() OVER (ORDER BY post_id DESC) AS rn ");
	            sqlBuilder.append("FROM post_tbl WHERE ");
	            
	            // 검색 조건에 맞는 각 검색 타입 조정
	            switch (searchType) {
	                case "title":
	                    sqlBuilder.append("title LIKE ?) p ");
	                    break;
	                case "content":
	                    sqlBuilder.append("content LIKE ?) p ");
	                    break;
	                case "title_content":
	                    sqlBuilder.append("(title LIKE ? OR content LIKE ?)) p ");
	                    break;
	                case "user":
	                    sqlBuilder.append("user_id LIKE ?) p ");
	                    break;
	                default:
	                    sqlBuilder.append("(title LIKE ? OR content LIKE ?)) p ");
	                    break;
	            }
	            // sqlBuilder에 페이지네이션 추가
	            sqlBuilder.append("WHERE rn BETWEEN ? AND ?");
	            
	            pst = con.prepareStatement(sqlBuilder.toString());
	            
	            // 검색 조건에 맞는 매개변수 설정
	            int paramIndex = 1;
	            if (searchType.equals("title_content")) {
	                pst.setString(paramIndex++, "%" + keyword + "%");
	                pst.setString(paramIndex++, "%" + keyword + "%");
	            } else {
	                pst.setString(paramIndex++, "%" + keyword + "%");
	            }
	            
	            int start = (page - 1) * pageSize + 1;
	            int end = page * pageSize;
	            
	            pst.setInt(paramIndex++, start);
	            pst.setInt(paramIndex++, end);
	            
	            rs = pst.executeQuery();
	            
	            while (rs.next()) {
	                CommunityDTO post = new CommunityDTO();
	                post.setPost_id(rs.getInt("post_id"));
	                post.setUser_id(rs.getString("user_id"));
	                post.setPost_title(rs.getString("title"));
	                post.setPost_content(rs.getString("content"));
	                post.setLike_number(rs.getInt("likes"));
	                post.setViews_number(rs.getInt("views"));
	                post.setCreated_at(rs.getTimestamp("created_at"));
	                post.setComment_count(rs.getInt("comment_count"));
	                
	                postList.add(post);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            close(rs, pst, con);
	        }
	        
	        return postList;
	    }
	  // 검색한 글의 갯수 가져옴
	  public int getSearchPostCount(String searchType, String keyword) {
	        Connection con = null;
	        PreparedStatement pst = null;
	        ResultSet rs = null;
	        int count = 0;
	        
	        try {
	            con = dbCon();
	            
	            StringBuilder sqlBuilder = new StringBuilder();
	            sqlBuilder.append("SELECT COUNT(*) FROM post_tbl WHERE ");
	            
	            // 검색 조건에 따라 쿼리 조정
	            switch (searchType) {
	                case "title":
	                    sqlBuilder.append("title LIKE ?");
	                    break;
	                case "content":
	                    sqlBuilder.append("content LIKE ?");
	                    break;
	                case "title_content":
	                    sqlBuilder.append("(title LIKE ? OR content LIKE ?)");
	                    break;
	                case "user":
	                    sqlBuilder.append("user_id LIKE ?");
	                    break;
	                default:
	                    sqlBuilder.append("(title LIKE ? OR content LIKE ?)");
	                    break;
	            }
	            
	            pst = con.prepareStatement(sqlBuilder.toString());
	            
	            if (searchType.equals("title_content")) {
	                pst.setString(1, "%" + keyword + "%");
	                pst.setString(2, "%" + keyword + "%");
	            } else {
	                pst.setString(1, "%" + keyword + "%");
	            }
	            
	            rs = pst.executeQuery();
	            
	            if (rs.next()) {
	                count = rs.getInt(1);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            close(rs, pst, con);
	        }
	        
	        return count;
	    }
	  
	  //상위 5개 게시글을 가져오는 메소드
	  public List<CommunityDTO> getTopLikedPosts(int count) {
		    List<CommunityDTO> postList = new ArrayList<>();
		    String sql = "SELECT p.post_id, p.user_id, p.title as post_title, p.content as post_content, "
		               + "p.likes as like_number, p.views as views_number, p.created_at, "
		               + "(SELECT COUNT(*) FROM comment_tbl c WHERE c.post_id = p.post_id) as comment_count "
		               + "FROM post_tbl p "
		               + "ORDER BY p.likes DESC "
		               + "FETCH FIRST ? ROWS ONLY";
		    
		    try (Connection con = dbCon();
		         PreparedStatement pstmt = con.prepareStatement(sql)) {
		        
		        pstmt.setInt(1, count);
		        ResultSet rs = pstmt.executeQuery();
		        
		        while (rs.next()) {
		            CommunityDTO post = new CommunityDTO();
		            post.setPost_id(rs.getInt("post_id"));
		            post.setUser_id(rs.getString("user_id"));
		            post.setPost_title(rs.getString("post_title"));
		            post.setPost_content(rs.getString("post_content"));
		            post.setLike_number(rs.getInt("like_number"));
		            post.setViews_number(rs.getInt("views_number"));
		            post.setComment_count(rs.getInt("comment_count"));
		            postList.add(post);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return postList;
		}
	  
	  
	  //최신 게시글을 가져오는 메소드
	  public List<CommunityDTO> getRecentPosts(int count) {
		    List<CommunityDTO> postList = new ArrayList<>();
		    String sql = "SELECT p.post_id, p.user_id, p.title as post_title, p.content as post_content, "
		               + "p.likes as like_number, p.views as views_number, p.created_at, "
		               + "(SELECT COUNT(*) FROM comment_tbl c WHERE c.post_id = p.post_id) as comment_count "
		               + "FROM post_tbl p "
		               + "ORDER BY p.created_at DESC "
		               + "FETCH FIRST ? ROWS ONLY";
		    
		    try (Connection con = dbCon();
		         PreparedStatement pstmt = con.prepareStatement(sql)) {
		        
		        pstmt.setInt(1, count);
		        ResultSet rs = pstmt.executeQuery();
		        
		        while (rs.next()) {
		            CommunityDTO post = new CommunityDTO();
		            post.setPost_id(rs.getInt("post_id"));
		            post.setUser_id(rs.getString("user_id"));
		            post.setPost_title(rs.getString("post_title"));
		            post.setPost_content(rs.getString("post_content"));
		            post.setLike_number(rs.getInt("like_number"));
		            post.setViews_number(rs.getInt("views_number"));
		            post.setComment_count(rs.getInt("comment_count"));
		            postList.add(post);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		    return postList;
		}
	  
	  
	  
	  public static void main(String[] args) {
		
	}
	  
	  

}
