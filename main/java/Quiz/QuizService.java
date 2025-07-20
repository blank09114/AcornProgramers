package Quiz;

import java.util.ArrayList;

public class QuizService {
	
	QuizDAO dao = new QuizDAO();
	
	//퀴즈 전체를 가져와 목록 형식으로 보여주기 service 코드
	public ArrayList<Quiz> getQuiz() {
		return dao.quizList();
	}
	
	//퀴즈 풀이 페이지에서 보여줄 각 퀴즈의 상세 데이터 service 코드
	public Quiz getOneQuiz(String quiz_id) {
		Quiz quiz = dao.selectOne(quiz_id);
		return quiz;
	}
	
	//퀴즈를 맞힌 후 해당 유저의 점수(score)를 올리는 service 코드
	public void updateScore(String user_mail) {
		dao.updateUserScore(user_mail);
	}
	
	//퀴즈를 맞힌 후 해당 퀴즈를 맞힌 유저의 인원 수를 올리는 service 코드
	public void updateSolved(String quiz_id) {
		dao.updateQuizSolved(quiz_id);
	}
}
