package User;

public class User {
	String user_id; //유저 아이디
	String user_pw; //유저 비밀번호
	String user_mail; //유저 메일
	String user_tell; //유저 전화번호
	int user_grade; //유저 등급
	int user_point = user_grade*10;//유저 포인트
	
	//생성자
	public User(String user_id, String user_pw, String user_mail, String user_tell, int user_grade, int user_point) {
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_mail = user_mail;
		this.user_tell = user_tell;
		this.user_grade = user_grade;
		this.user_point = user_point;
	}
	public User(String user_mail) {
		this.user_mail = user_mail;
	}

	
	
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_pw=" + user_pw + ", user_mail=" + user_mail + ", user_tell="
				+ user_tell + ", user_grade=" + user_grade + ", user_point=" + user_point + "]";
	}
	//getter
	public String getUser_id() {
		return user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public String getUser_mail() {
		return user_mail;
	}
	public String getUser_tell() {
		return user_tell;
	}
	
	public int getUser_grade() {
		return user_grade;
	}
	
	public int getUser_point() {
		return user_point;
	}
	
	public void setUser_grade(int user_grade) {
		this.user_grade = user_grade;
	}
	
	public void setUser_point(int user_point) {
	    this.user_point = user_point;
	}

	
	
	
	
	
}