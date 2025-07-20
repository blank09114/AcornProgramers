package User;

public class UserService {
	UserDAO dao = new UserDAO();
	LoginDAO loginDao = new LoginDAO();
	
	//회원가입
	public void joinUser(User user) {
		dao.join(user);
	}
	
	//아이디, 이메일 중복 확인
	public boolean checkDuplicate(User user) {
		return dao.isUserExist(user.getUser_id(), user.getUser_mail());
	}
	
	public boolean loginUser(User user) {
	    return loginDao.login(user);
	}
	
	public User getUserInfo(String user_mail) {
	    return dao.userInfo(user_mail);
	}
	
	// 회원탈퇴
	public boolean deleteUser(String user_mail) {
	    return dao.deleteUser(user_mail);
	}
}
