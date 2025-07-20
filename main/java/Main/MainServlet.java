package Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Community.CommunityDAO;
import Community.CommunityDTO;
import Rank.Rank;
import Rank.RankService;
import User.User;

@WebServlet("/index")
public class MainServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RankService service = new RankService();
	    ArrayList<Rank> list = service.getRankList();
	    req.setAttribute("list", list);

	    // 유저 정보 가져오기
        User user = (User) req.getSession().getAttribute("user");
        Rank rankInfo = null;

        if (user != null) {
            rankInfo = service.getOneRankList(user.getUser_id());
        }
        req.setAttribute("rankInfo", rankInfo);
        
        
        CommunityDAO communityDAO = new CommunityDAO();
        
        // 커뮤니티 인기 게시글 가져오기
        List<CommunityDTO> topPosts = communityDAO.getTopLikedPosts(5);
        req.setAttribute("topPosts", topPosts);
        
        // 최신 게시글 가져오기 (최신순)
        List<CommunityDTO> recentPosts = communityDAO.getRecentPosts(5);
        req.setAttribute("recentPosts", recentPosts);
        
        // 모든 데이터를 설정한 후 한 번만 forward
        req.getRequestDispatcher("WEB-INF/views/Main/index.jsp").forward(req, resp);
        
	}
	
}
