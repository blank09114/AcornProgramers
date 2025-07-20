package Quiz;

public class Quiz {
	String quiz_id; //레벨
	String quiz_title; //문제 타이틀
	String quiz_answer; //정답
	int quiz_solution; //정답을 맞친 유저 수
	
	//퀴즈 생성자
	public Quiz() {

	}
	public Quiz(String quiz_id, String quiz_title, String quiz_answer, int quiz_solution) {
		this.quiz_id = quiz_id;
		this.quiz_title = quiz_title;
		this.quiz_answer = quiz_answer;
		this.quiz_solution = quiz_solution;
	}

	//getter함수
	public String getQuiz_id() {
		return quiz_id;
	}
	public String getQuiz_title() {
		return quiz_title;
	}
	public String getQuiz_answer() {
		return quiz_answer;
	}
	public int getQuiz_solution() {
		return quiz_solution;
	}

	@Override
	public String toString() {
		return "Quiz [quiz_id=" + quiz_id + ", quiz_title=" + quiz_title + ", quiz_answer=" + quiz_answer
				+ ", quiz_solution=" + quiz_solution + "]";
	}
}