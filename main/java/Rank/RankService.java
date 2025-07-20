package Rank;

import java.util.ArrayList;

public class RankService {
	RankDAO dao = new RankDAO();
	
	public ArrayList<Rank> getRankList(){
		ArrayList<Rank> list = dao.getRanking();
		return list;
	}
	
	public ArrayList<Rank> getRankPage(int currentPage){
		ArrayList<Rank> list = dao.rankPage(currentPage);
		return list;
	}
	
	public int getTotalRankCount() {
	    return dao.getTotalRankCount();
	}
	
	public Rank getOneRankList(String user_id) {
		Rank rank = dao.getOneRanking(user_id);
		return rank;
	}
}
