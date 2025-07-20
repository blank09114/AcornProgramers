package Rank;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rank")
public class RankServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int currentPage = 1;
	    String page = req.getParameter("page");
	    if (page != null && !page.equals("")) {
	        currentPage = Integer.parseInt(page);
	    }

	    RankService service = new RankService();

	    // 전체 레코드 수 구하기
	    int totalRecords = service.getTotalRankCount();
	    PageHandler ph = new PageHandler(10, 5, totalRecords, currentPage); // 페이지당 10개, 그룹당 5페이지

	    ArrayList<Rank> list = service.getRankPage(currentPage);

	    req.setAttribute("list", list);
	    req.setAttribute("ph", ph);
	    req.setAttribute("currentPage", currentPage);

	    req.getRequestDispatcher("WEB-INF/views/Rank/ranking.jsp").forward(req, resp);
	}
}