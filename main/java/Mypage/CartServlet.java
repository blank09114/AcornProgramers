package Mypage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/cart/*")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.equals("/delete")) {
            // 삭제
            deleteCartItem(req, resp);
            return;
        }

        // 기존의 장바구니 추가 로직
        //DB에서 상폼정보 가져오기
        String productName = req.getParameter("productName");
        String productPoint = req.getParameter("productPoint");
        String imgSrc = req.getParameter("imgSrc");

        // 세션에서 장바구니 정보 가져오기
        HttpSession session = req.getSession();
        // 장바구니가 없으면 새로 생성
        List<Map<String, String>> cartList = (List<Map<String, String>>) session.getAttribute("cartList");
        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        // 상품 정보가 유효한지 확인
        // 상품 정보를 맵에 저장
        if (productName != null && productPoint != null) {
            Map<String, String> product = new HashMap<>();
            product.put("name", productName);
            product.put("point", productPoint);
            product.put("imgSrc", imgSrc);
            cartList.add(product);
        }

        // 장바구니 정보를 세션에 저장
        session.setAttribute("cartList", cartList);
        req.getRequestDispatcher("WEB-INF/views/Mypage/cart.jsp").forward(req, resp);
    }

    // 장바구니 삭제 처리
    private void deleteCartItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	// 삭제할 상품의 인덱스 가져오기
        String indexStr = req.getParameter("index");
        // 인덱스가 null이 아니면 삭제 처리
        if (indexStr != null) {
            try {
            	
                int index = Integer.parseInt(indexStr);
                //세션에서 장바구니 정보 가져오기
                HttpSession session = req.getSession();
                List<Map<String, String>> cartList = (List<Map<String, String>>) session.getAttribute("cartList");
                // 장바구니가 null이 아니고 인덱스가 유효한지 확인 후 삭제 처리
                if (cartList != null && index >= 0 && index < cartList.size()) {
                    cartList.remove(index);
                    session.setAttribute("cartList", cartList);
                }
                
                
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Invalid index\"}");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Missing index parameter\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/Mypage/cart.jsp").forward(req, resp);
    }
}