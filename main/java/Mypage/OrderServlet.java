package Mypage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import User.User;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 배송지 form 데이터 수신
        String shippingName = request.getParameter("shipping-name");
        String recipient = request.getParameter("recipient");
        String address = request.getParameter("address");
        String postalCode = request.getParameter("postal-code");
        String addressDetail = request.getParameter("address-detail");

        String phone1 = request.getParameter("phone1-prefix") + "-" +
                        request.getParameter("phone1-mid") + "-" +
                        request.getParameter("phone1-last");

        String phone2 = "";
        if (!request.getParameter("phone2-prefix").isEmpty()) {
            phone2 = request.getParameter("phone2-prefix") + "-" +
                     request.getParameter("phone2-mid") + "-" +
                     request.getParameter("phone2-last");
        }

        // 세션에서 사용자 ID 및 장바구니 정보 가져오기
        User user = (User) request.getSession().getAttribute("user");
        List<Map<String, String>> cartList = (List<Map<String, String>>) request.getSession().getAttribute("cartList");

        if (user != null) {
            String userId = user.getUser_id();

            // 주문 정보 문자열 구성
            StringBuilder orderInfo = new StringBuilder();
            orderInfo.append("배송지명: ").append(shippingName)
                     .append(" | 수령인: ").append(recipient)
                     .append(" | 주소: ").append(address).append(" ").append(addressDetail).append(" (").append(postalCode).append(")")
                     .append(" | 연락처1: ").append(phone1);

            if (!phone2.isEmpty()) {
                orderInfo.append(" | 연락처2: ").append(phone2);
            }

            // 장바구니 상품 목록 포함
            if (cartList != null && !cartList.isEmpty()) {
                orderInfo.append(" | 상품 목록: ");
                for (Map<String, String> product : cartList) {
                    String name = product.get("name");
                    String point = product.get("point");
                    orderInfo.append("[").append(name).append(" - ").append(point).append("P] ");
                }
            }

            // DAO에 저장
            OrderDAO.addOrder(userId, orderInfo.toString());
            request.getSession().removeAttribute("cartList");
        }

        // 완료 페이지로 이동
        request.getRequestDispatcher("WEB-INF/views/Mypage/orderComplete.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 주문 내역 조회용 페이지 이동
        request.getRequestDispatcher("WEB-INF/views/Mypage/order.jsp").forward(request, response);
    }
}
