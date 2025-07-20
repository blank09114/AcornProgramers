<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>배송지 정보</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link href="css/Mypage/address.css" rel="stylesheet">
</head>

<body>
<div class="shipping-container">
  <div class="form-header">
    배송지 정보
  </div>
  
  <form action="/testprj/order" method="post">
    <table>
      <tr>
        <th class="required">배송지명</th>
        <td>
          <div class="input-icon">
            <i class="fas fa-tag"></i>
            <input type="text" name="shipping-name" placeholder="배송지를 입력해주세요 ex)회사, 집, 학교" required>
          </div>
        </td>
      </tr>
      <tr>
        <th class="required">수령인</th>
        <td>
          <div class="input-icon">
            <i class="fas fa-user"></i>
            <input type="text" name="recipient" placeholder="이름" required>
          </div>
        </td>
      </tr>
      <tr class="address-row">
        <th class="required">배송지</th>
        <td>
          <div class="input-icon">
            <i class="fas fa-map-marker-alt"></i>
            <input type="text" name="address" placeholder="주소" required>
          </div>
          <div class="input-icon">
            <i class="fas fa-envelope"></i>
            <input type="text" name="postal-code" placeholder="우편번호">
          </div>
          <div class="input-icon">
            <i class="fas fa-home"></i>
            <input type="text" name="address-detail" placeholder="상세주소 입력">
          </div>
        </td>
      </tr>
      <tr>
        <th class="required">연락처1</th>
        <td>
          <div class="phone-group">
            <select name="phone1-prefix" required>
              <option value="">선택하세요</option>
              <option value="010">010</option>
              <option value="011">011</option>
            </select>
            <span class="phone-divider">-</span>
            <input type="text" name="phone1-mid" maxlength="4" required>
            <span class="phone-divider">-</span>
            <input type="text" name="phone1-last" maxlength="4" required>
          </div>
        </td>
      </tr>
      <tr>
        <th>연락처2</th>
        <td>
          <div class="phone-group">
            <select name="phone2-prefix">
              <option value="">선택하세요</option>
              <option value="010">010</option>
              <option value="011">011</option>
            </select>
            <span class="phone-divider">-</span>
            <input type="text" name="phone2-mid" maxlength="4">
            <span class="phone-divider">-</span>
            <input type="text" name="phone2-last" maxlength="4">
          </div>
        </td>
      </tr>
      <tr>
        <td colspan="2" class="button-row">
          <button type="submit" class="order-button">
            <i class="fas fa-check"></i> 주문하기
          </button>
          <button type="button" class="cancel-button">
            <i class="fas fa-times"></i> 취소
          </button>
        </td>
      </tr>
    </table>
  </form>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {

  // 주문하기 (submit 버튼)
  const form = document.querySelector('form');
  form.addEventListener('submit', function(e) {
    // 주문 확인 후 제출 진행
    const confirmed = confirm("주문을 완료하시겠습니까?");
    if (!confirmed) {
      e.preventDefault();
    } else {
      alert("주문이 완료되었습니다.");
    }
  });

  // 취소 버튼
  const cancelButton = document.querySelector('.cancel-button');
  cancelButton.addEventListener('click', function() {
    if (confirm('입력을 취소하시겠습니까?')) {
      window.history.back();
    }
  });

});
</script>

</body>
</html>