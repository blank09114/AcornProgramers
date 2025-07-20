package Community;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/community")
public class CommunityServlet extends HttpServlet {
    private CommunityService service = new CommunityService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 페이지 번호 (기본값 1)
        int page = 1;
        if (req.getParameter("page") != null) {
            try {
                page = Integer.parseInt(req.getParameter("page"));
            } catch (NumberFormatException e) {
                // 숫자 형식이 아니면 기본값 1 사용
            }
        }
        
       
        String searchType = req.getParameter("searchType");
        String keyword = req.getParameter("keyword");
        
        // 페이지당 게시글 수 10으로 정함
        int pageSize = 10;
        
        Map<String, Object> result;
        
        // 검색어가 있으면 검색 결과, 없으면 전체 목록
        if (searchType != null && keyword != null && !keyword.trim().isEmpty()) {
            result = service.searchPosts(searchType, keyword, page, pageSize);
        } else {
            result = service.getPostList(page, pageSize);
        }
        
        // 결과를 request에 저장
        req.setAttribute("result", result);
        
        
        req.getRequestDispatcher("WEB-INF/views/Community/community.jsp").forward(req, resp);
    }
}