package Rank;

public class Rank {
	int ranking; //랭킹 순위
	String user_id; //유저 아이디
	String user_score; //유저 점수
	String user_day; //유저 가입날짜
	
	//생성자
	public Rank(int ranking, String user_id, String user_score, String user_day) {
		this.ranking = ranking;
		this.user_id = user_id;
		this.user_score = user_score;
		this.user_day = user_day;
	}

	//getter 함수
	public int getRanking() {
		return ranking;
	}
	public String getUser_id() {
		return user_id;
	}
	public String getUser_score() {
		return user_score;
	}
	public String getUser_day() {
		return user_day;
	}

	@Override
	public String toString() {
		return "Rank [ranking=" + ranking + ", user_id=" + user_id + ", user_score=" + user_score + ", user_day=" + user_day
				+ "]";
	}
}