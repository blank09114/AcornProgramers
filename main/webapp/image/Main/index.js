// 상단 슬라이드 변경
let topCnt = 1;


function topCtr()
{
    const top1 = document.querySelector(".topBannerSlide1");
    const top2 = document.querySelector(".topBannerSlide2");
	const top3 = document.querySelector(".topBannerSlide3");

    if (topCnt === 1)
    {
        top1.classList.remove("now");
        top2.classList.add("now");
        topCnt = 2;
    }
	else 	if (topCnt === 2)
	    {
	        top2.classList.remove("now");
	        top3.classList.add("now");
	        topCnt = 3;
	    }
    else
    {
        top1.classList.add("now");
        top3.classList.remove("now");
        topCnt = 1;
    }
}

function bottomCtr()
{
    const top1 = document.querySelector(".topBannerSlide1");
    const top2 = document.querySelector(".topBannerSlide2");
	const top3 = document.querySelector(".topBannerSlide3");

    if (topCnt === 1)
    {
        top1.classList.remove("now");
        top3.classList.add("now");
        topCnt = 3;
    }
	else 	if (topCnt === 3)
	    {
	        top3.classList.remove("now");
	        top2.classList.add("now");
	        topCnt = 2;
	    }
    else
    {
        top1.classList.add("now");
        top2.classList.remove("now");
        topCnt = 1;
    }
}

// 커뮤니티 슬라이드 변경
let comCnt = 1;

function comCtr()
{
    const com1 = document.querySelector(".communitySlide1");
    const com2 = document.querySelector(".communitySlide2");
	

    if (comCnt === 1)
    {
        com1.classList.remove("now");
        com2.classList.add("now");
        comCnt = 2;
    }
    else
    {
        com1.classList.add("now");
        com2.classList.remove("now");
        comCnt = 1;
    }
}

// 광고 버튼 클릭
const messages =
[
    "왜 클릭?",
    "중고차 없습니다",
    "이걸 또 누르네",
    "할 일 없냐",
    "그만 좀 눌러라",
    "문제나 풀어라",
    "다음 광고 기다리는 중...",
    "팝업창 열릴 뻔",
    "실제로 쪽찌는 못 보냅니다",
    "클릭 횟수 수집 중… 거짓말이야",
    "이쯤에서 진지하게 공부나 해볼까?",
    "이 광고는 실존하지 않습니다",
    "우리 광고 넣는 법 몰라요",
    "이 광고는 딥러닝으로 생성된 허구입니다",
    "누르면 당신의 지갑에서 10원이 빠져나갑니다",
    "아 뭐 넣지",
    "슬슬",
    "아이디어",
    "고갈"
];

let index = 0;
let cnt = 0;

function ad()
{
    alert(messages[index]);

    index = (index + 1) % messages.length;
    cnt++;

    console.log("어떤 할 일 없는 유저가 이 버튼을 " + cnt + "번이나 눌렀습니다.");
}