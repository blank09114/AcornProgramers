package User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
	
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:testdb";
	String user="scott";
	String password="tiger";

//    String driver="oracle.jdbc.driver.OracleDriver";
//    String url="jdbc:oracle:thin:@13.125.27.64:1521:xe";
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
    
    public boolean login(User user) {
        Connection con = dbCon();
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean loginSuccess = false;
        
        if (con != null) {
            System.out.println("DB ok");
        } else {
            System.out.println("DB failed");
            return false;
        }
        
        String sql = "select * from user_tbl where user_mail=? and user_pw=?";
        
        try {
            pst = con.prepareStatement(sql);

            pst.setString(1, user.getUser_mail());
            pst.setString(2, user.getUser_pw());

            rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("Login success");
                System.out.println("user_id: " + rs.getString("user_id"));
                System.out.println("user_pw: " + rs.getString("user_pw"));
                System.out.println("user_mail: " + rs.getString("user_mail"));
                System.out.println("user_tell: " + rs.getString("user_tell"));
                System.out.println("user_score: " + rs.getInt("user_score")); // Changed from user_grade to user_score
                
                user.user_id = rs.getString("user_id");
                user.user_tell = rs.getString("user_tell");
                user.user_grade = rs.getInt("user_score"); // Map user_score from DB to user_grade in User object
                
                loginSuccess = true;
            } else {
                System.out.println("Login failed");
                loginSuccess = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            loginSuccess = false;
        } finally {
            close(pst, rs, con);
        }
        
        return loginSuccess;
    }
    
    public void close(AutoCloseable ...a) {
        for(AutoCloseable item: a) {
            try {
                item.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}