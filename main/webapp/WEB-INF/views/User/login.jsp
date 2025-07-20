<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link href="css/User/userForm.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<!-- Google 로그인 API 추가 -->
<script src="https://accounts.google.com/gsi/client" async defer></script>
<!-- 카카오 로그인 SDK 추가 -->
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script>
// 카카오 SDK 초기화 - 발급받은 앱 키로 변경 필요
Kakao.init('e6f2109adcaa73674544504669b2b1ef');

function login(e) {
    e.preventDefault();
    
    let user_mail = document.querySelector(".user_mail");
    let user_pw = document.querySelector(".user_pw");
    
    $.ajax({
        type: "post",
        url: "/testprj/login",
        data: {
            user_mail: user_mail.value,
            user_pw: user_pw.value
        },
        success: function(data) {
            if (data.success) {
                alert("로그인에 성공하셨습니다.");
                location.href = "/testprj/index";
            } else {
                alert("이메일과 비밀번호가 일치하지 않습니다.");
                user_mail.value = "";
                user_pw.value = "";
            }
        },
        error: function(err) {
            alert("서버 오류가 발생했습니다.");
            console.log(err);
        }
    });
}

// Google 로그인 처리 함수
function processGoogleLogin(response) {
    // 구글에서 준 토큰 받아오기 -> response.credential 구글에서 지정한 이름 그대로 써야함
    const id_token = response.credential;
    
    // 서버로 ID 토큰 전송
    $.ajax({
        type: "post",
        url: "/testprj/googleLogin",
        data: {
            id_token: id_token
        },
        success: function(data) {
            if (data.success) {
                alert("구글 로그인에 성공하셨습니다.");
                location.href = "<%=request.getContextPath()%>/index";
            } else {
                alert("구글 로그인 처리 중 오류가 발생했습니다: " + data.message);
            }
        },
        error: function(err) {
            alert("서버 오류가 발생했습니다.");
            console.log(err);
        }
    });
}

// 카카오 로그인 처리 함수
function kakaoLogin() {
    Kakao.Auth.login({
        success: function(authObj) {
            $.ajax({
                type: "post",
                url: "/testprj/kakaoLogin",
                data: {
                    access_token: authObj.access_token
                },
                success: function(data) {
                    if (data.success) {
                        alert("카카오 로그인에 성공하셨습니다.");
                        location.href = "/testprj/index";
                    } else {
                        alert("카카오 로그인 처리 중 오류가 발생했습니다: " + (data.message || "알 수 없는 오류"));
                    }
                },
                error: function(err) {
                    alert("서버 오류가 발생했습니다.");
                    console.log(err);
                }
            });
        },
        fail: function(err) {
            alert("카카오 로그인에 실패했습니다: " + JSON.stringify(err));
        }
    });
}
</script>
</head>
<body>
    <jsp:include page="../Main/header.jsp"></jsp:include>
    <div class="userFormContainer">
        <form class="userForm" onsubmit="login(event)">
            <h2>로그인</h2>
            <div class="inputBox">
                <label for="user_mail">이메일</label>
                <div class="userInput">
                    <img src="image/User/email.png" alt="이메일 아이콘" />
                    <input class="user_mail" type="email" name="user_mail" placeholder="이메일을 입력하세요." required>
                </div>
            </div>
            <div class="inputBox">
                <label for="user_pw">비밀번호</label>
                <div class="userInput">
                    <img src="image/User/padlock.png" alt="이메일 아이콘" />
                    <input class="user_pw" type="password" name="user_pw" placeholder="비밀번호를 입력하세요." required>
                </div>
            </div>
            <div class=submitBtn>
                <button type="submit">로그인</button>
            </div>
            <hr>
            <div class="iconBtn">
                <div class="social">
                    <!-- Google 로그인 버튼 -->
                    <div id="g_id_onload"
                         data-client_id="1032929545361-gbt6r0e9cg9kfrmjc86t9i8vv4g7oli0.apps.googleusercontent.com"
                         data-callback="processGoogleLogin"
                         data-auto_prompt="false">
                    </div>
                    <div class="g_id_signin"
                         data-type="icon"
                         data-size="large"
                         data-theme="outline"
                         data-text="sign_in_with"
                         data-shape="circle">
                    </div>
                    <p>구글</p>
                </div>
                <div class="social">
                    <img src="image/User/kakao.png" alt="kakao" onclick="kakaoLogin()" />
                    <p>카카오</p>
                </div>
            </div>
            <div class="link">
                 <a href="/testprj/join" style="margin-right: 15px;">회원가입</a>
                 <a href="<%=request.getContextPath()%>/index">메인화면</a>
            </div>
        </form>
    </div>
    <jsp:include page="../Main/footer.jsp"></jsp:include>
</body>
</html>