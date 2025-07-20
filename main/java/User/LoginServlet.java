package User;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")  
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/User/login.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        String user_mail = req.getParameter("user_mail");
        String user_pw = req.getParameter("user_pw");
        
        User user = new User(null, user_pw, user_mail, null, 1, 10);

        UserService service = new UserService();
        boolean loginSuccess = service.loginUser(user);
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        
        if (loginSuccess) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            
            out.print("{\"success\": true}");
        } else {
    
            out.print("{\"success\": false}");
        }
        out.flush();
    }
}
