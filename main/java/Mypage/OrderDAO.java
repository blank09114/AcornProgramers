package Mypage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {
    // 사용자 ID별로 주문 내역 저장 (임시 메모리 저장소)
    private static Map<String, List<String>> orderHistoryMap = new HashMap<>();

    // 주문 추가 메서드
    public static void addOrder(String userId, String orderInfo) {
        orderHistoryMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(orderInfo);
    }

    // 주문 내역 조회 메서드
    public static List<String> getOrderHistoryByUserId(String userId) {
        return orderHistoryMap.getOrDefault(userId, new ArrayList<>());
    }
}
